package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.professorallocation.Main.Models.Department
import com.example.professorallocation.Main.Models.Professor
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Repository.ProfessorRepository
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.Main.Service.ProfessorService
import com.example.professorallocation.R

class URDProfessor : AppCompatActivity() {

    private lateinit var repository: ProfessorRepository
    private lateinit var repositoryDep: DepartmentRepository
    private lateinit var departmentPairs: List<Pair<String, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_urdprofessor)

        val professorService = ProfessorService.create()
        repository = ProfessorRepository(professorService)

        val departmentService = DepartmentService.create()
        repositoryDep = DepartmentRepository(departmentService)

        val ProfessorId   = intent.getIntExtra("Professor_id", -1)
        val ProfessorName = intent.getStringExtra("Professor_name") ?: ""
        val ProfessorCpf  = intent.getStringExtra("Professor_CPF") ?: ""
        val ProfessorDep  = intent.getIntExtra("Professor_Departamento", -1)



       // val btnUpdateProfessor = findViewById<ImageButton>(R.id.btnUpdateCourse)
        val btnDelete: Button = findViewById(R.id.btnDeleteProf)
        val btnUpdate: Button = findViewById(R.id.btnEditProf)
        val editIdProfessor: EditText = findViewById(R.id.idEditProfessor)
        editIdProfessor.setText(ProfessorId.toString())
        editIdProfessor.isEnabled = false
        val editNomeProfessor: EditText = findViewById(R.id.NomeEditProfessor)
        editNomeProfessor.setText(ProfessorName)
        val editCPFProfessor: EditText = findViewById(R.id.CPFEditProfessor)
        editCPFProfessor.setText(ProfessorCpf)
        val spinnerDepProfessor = findViewById<Spinner>(R.id.spinnerDepProfessor)

        // Buscar departamentos
        repositoryDep.buscarDepartamento(
            onCall = { departments ->
                if (departments != null) {
                    // Crie uma lista de pares (nome, id)
                    departmentPairs = departments.map { it.name to it.id }

                    // Extraia apenas os nomes dos departamentos para configurar o Spinner
                    val departmentNames: List<String> = departmentPairs.map { it.first }

                    // Configura o Adapter do Spinner
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departmentNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerDepProfessor.adapter = adapter

                    val professorDepartmentId = ProfessorDep
                    val position = departmentPairs.indexOfFirst { it.second == professorDepartmentId }

                    if (position >= 0) {
                        spinnerDepProfessor.setSelection(position)
                    } else {
                        spinnerDepProfessor.setSelection(0)
                    }
                } else {
                    // Tratamento caso a lista de departamentos seja nula
                    Toast.makeText(this, "Nenhum departamento encontrado", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                // Tratamento de erro
                Toast.makeText(this, "Erro ao buscar departamentos", Toast.LENGTH_SHORT).show()
            }
        )

        btnDelete.setOnClickListener  { professor ->
            repository.apagarProfessor(
                idProfessor = ProfessorId,
                onCall = { code ->
                    if(code == 204)
                        Toast.makeText(this, "Professor excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this, "Não foi possível excluir professor!", Toast.LENGTH_SHORT).show()

                },
                onError = { mensagem ->
                    Toast.makeText(this, "Erro ao excluir professor: $mensagem", Toast.LENGTH_SHORT).show()
                }
            )

            val intent = Intent(this, ProfessorActivity::class.java)
            startActivity(intent)

        }

        btnUpdate.setOnClickListener {

            val professorName = editNomeProfessor.text.toString()
            val professorCPF = editCPFProfessor.text.toString()
            val selectedDepartmentName = spinnerDepProfessor.selectedItem.toString()

            // Encontrar o ID do departamento correspondente ao nome selecionado
            val selectedDepartmentId = departmentPairs.find { it.first == selectedDepartmentName }?.second

            if (professorName.isNotEmpty() && selectedDepartmentId != null) {
                val novoProfessor = Professor(
                    id = ProfessorId,
                    name = professorName,
                    cpf = professorCPF,
                    departmentId = selectedDepartmentId,
                    department = null
                )

                repository.alterarProfessor(
                    novoProfessor.id,
                    professor = novoProfessor,
                    onCall = {
                        Toast.makeText(this, "Professor criado com sucesso!", Toast.LENGTH_SHORT).show()

                        val menuProfessor = findViewById<Button>(R.id.btnDepartament)
                        menuProfessor.setOnClickListener {
                            val intent = Intent(this, ProfessorActivity::class.java)
                            startActivity(intent)
                        }
                    },
                    onError = {
                        Toast.makeText(this, "Erro ao criar professor!", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "O nome do professor não pode estar vazio e o departamento deve ser selecionado!", Toast.LENGTH_SHORT).show()
            }

        }


    }
}