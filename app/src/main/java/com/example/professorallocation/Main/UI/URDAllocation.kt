package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.professorallocation.Main.Models.Department
import com.example.professorallocation.Main.Models.Professor
import com.example.professorallocation.Main.Repository.AllocationRepository
import com.example.professorallocation.Main.Repository.CourseRepository
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Repository.ProfessorRepository
import com.example.professorallocation.Main.Service.AllocationService
import com.example.professorallocation.Main.Service.CourseService
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.Main.Service.ProfessorService
import com.example.professorallocation.R

class URDAllocation : AppCompatActivity() {

    private lateinit var repository: AllocationRepository
    private lateinit var repositoryCurso: CourseRepository
    private lateinit var repositoryProfessor: ProfessorRepository
    private lateinit var coursesPairs: List<Pair<String, Int>>
    private lateinit var professorPairs: List<Pair<String, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_urdallocation)

        val courseService = CourseService.create()
        repositoryCurso = CourseRepository(courseService)

        val AllocationService = AllocationService.create()
        repository = AllocationRepository(AllocationService)

        val ProfessorService = ProfessorService.create()
        repositoryProfessor = ProfessorRepository(ProfessorService)


        val AllocationId   = intent.getIntExtra("Allocation_id", -1)
        val AllocationProf = intent.getIntExtra("Allocation_Id_Prof", -1)
        val AllocationCurso  = intent.getIntExtra("Allocation_Id_Curso", -1)
        val AllocationDia  = intent.getStringExtra("Allocation_Dia") ?: ""
        val AllocationHoraIni  = intent.getStringExtra("Allocation_Hora_Ini") ?: ""
        val AllocationHoraFim  = intent.getStringExtra("Allocation_Hora_Fim") ?: ""





        val btnDelete: Button = findViewById(R.id.btnDeleteAllocation)
        val btnUpdate: Button = findViewById(R.id.btnEditAllocation)
        val editIdAllocation: EditText = findViewById(R.id.idEditAllocation)
        editIdAllocation.setText(AllocationId.toString())
        editIdAllocation.isEnabled = false

        val editHoraIni: EditText = findViewById(R.id.editTimeStart)
        editHoraIni.setText(AllocationHoraIni)
        val editHoraFim: EditText = findViewById(R.id.editTimeEnd)
        editHoraFim.setText(AllocationHoraFim)

        //editCPFProfessor.setText(ProfessorCpf)
        val spinnerAllocationProf = findViewById<Spinner>(R.id.spinnerAllocationProf)
        val spinnerAllocationCurso = findViewById<Spinner>(R.id.spinnerAllocationCurso)
        val spinnerAllocationDia = findViewById<Spinner>(R.id.spinnerDia)

        repositoryCurso.buscarCursos(
            onCall = { cursos ->
                if (cursos != null) {
                    coursesPairs = cursos.map { it.name to it.id }

                    val coursesNames: List<String> = coursesPairs.map { it.first }

                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerAllocationCurso.adapter = adapter

                    val allocationCourseId = AllocationCurso
                    val position = coursesPairs.indexOfFirst { it.second == allocationCourseId }

                    if (position >= 0) {
                        spinnerAllocationCurso.setSelection(position)
                    } else {
                        spinnerAllocationCurso.setSelection(0)
                    }
                } else {
                    Toast.makeText(this, "Nenhum curso encontrado", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                // Tratamento de erro
                Toast.makeText(this, "Erro ao buscar curso", Toast.LENGTH_SHORT).show()
            }
        )


        repositoryProfessor.buscarProfessor(
            onCall = { professors ->
                if (professors != null) {
                    professorPairs = professors.map { it.name to it.id }

                    val ProfessorsNames: List<String> = professorPairs.map { it.first }

                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ProfessorsNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerAllocationProf.adapter = adapter

                    val allocationProfessorId = AllocationProf
                    val position = professorPairs.indexOfFirst { it.second == allocationProfessorId }

                    if (position >= 0) {
                        spinnerAllocationProf.setSelection(position)
                    } else {
                        spinnerAllocationProf.setSelection(0)
                    }
                } else {
                    Toast.makeText(this, "Nenhum professor encontrado", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                // Tratamento de erro
                Toast.makeText(this, "Erro ao buscar professor", Toast.LENGTH_SHORT).show()
            }
        )


        val diasDaSemana = listOf(
            "SEGUNDA_FEIRA",
            "TERCA_FEIRA",
            "QUARTA_FEIRA",
            "QUINTA_FEIRA",
            "SEXTA_FEIRA",
            "SABADO",
            "DOMINGO"
        )

        val spinner: Spinner = findViewById(R.id.spinnerDia)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, diasDaSemana)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val diaSelecionado = AllocationDia

        val position = diasDaSemana.indexOf(diaSelecionado)
        if (position >= 0) {
            spinnerAllocationDia.setSelection(position)
        } else {
            spinnerAllocationDia.setSelection(0)
        }



        btnDelete.setOnClickListener  { professor ->
            repository.apagarAllocation(
                idAllocation = AllocationId,
                onCall = { code ->
                    if(code == 204)
                        Toast.makeText(this, "Allocation excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this, "Não foi possível excluir allocation!", Toast.LENGTH_SHORT).show()

                },
                onError = { mensagem ->
                    Toast.makeText(this, "Erro ao excluir allocation: $mensagem", Toast.LENGTH_SHORT).show()
                }
            )

            val intent = Intent(this, AllocationActivity::class.java)
            startActivity(intent)

        }

      /*  btnUpdate.setOnClickListener {

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

        } */


    }
}