package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.professorallocation.Main.Models.Professor
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Repository.ProfessorRepository
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.Main.Service.ProfessorService
import com.example.professorallocation.R

class CreateProfessorActivity : AppCompatActivity() {

    private lateinit var repository: ProfessorRepository
    private lateinit var departmentPairs: List<Pair<String, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_professor)

        val professorService = ProfessorService.create()
        repository = ProfessorRepository(professorService)

        val btnCreateProfessor = findViewById<ImageButton>(R.id.btnSaveAllocation)
        val edtProfessorName = findViewById<EditText>(R.id.editNewProfessor)
        val edtProfessorCPF = findViewById<EditText>(R.id.editCPF)
        val spnDepartamento = findViewById<Spinner>(R.id.spnProfessorCreate)

        val departmentService = DepartmentService.create()
        val repositoryDep = DepartmentRepository(departmentService)

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
                    spnDepartamento.adapter = adapter

                    // Definir a seleção inicial (Placeholder)
                    spnDepartamento.setSelection(0)
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

        btnCreateProfessor.setOnClickListener {
            val professorName = edtProfessorName.text.toString()
            val professorCPF = edtProfessorCPF.text.toString()
            val selectedDepartmentName = spnDepartamento.selectedItem.toString()

            // Encontrar o ID do departamento correspondente ao nome selecionado
            val selectedDepartmentId = departmentPairs.find { it.first == selectedDepartmentName }?.second

            if (professorName.isNotEmpty() && selectedDepartmentId != null) {
                val novoProfessor = Professor(
                    id = 0,
                    name = professorName,
                    cpf = professorCPF,
                    departmentId = selectedDepartmentId,
                    department = null
                )

                repository.criarProfessor(
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
