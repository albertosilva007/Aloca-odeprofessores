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
import com.example.professorallocation.Main.Repository.AllocationRepository
import com.example.professorallocation.Main.Repository.CourseRepository
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Repository.ProfessorRepository
import com.example.professorallocation.Main.Service.AllocationService
import com.example.professorallocation.Main.Service.CourseService
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.Main.Service.ProfessorService
import com.example.professorallocation.R

class CreateAllocationActivity : AppCompatActivity() {

    private lateinit var repository: AllocationRepository
    private lateinit var repositoryProf: ProfessorRepository
    private lateinit var repositoryCourse: CourseRepository
    private lateinit var CoursePairs: List<Pair<String, Int>>
    private lateinit var ProfessorPairs: List<Pair<String, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_allocation)

        val allocationService = AllocationService.create()
        repository = AllocationRepository(allocationService)

        val professprService = ProfessorService.create()
        repositoryProf = ProfessorRepository(professprService)

        val courseService = CourseService.create()
        val repositoryCourse = CourseRepository(courseService)

        val btnCreateAllocation = findViewById<ImageButton>(R.id.btnSaveAllocation)
        val edtAllocationHoraIni = findViewById<EditText>(R.id.editAllocationHoraIni)
        val edtAllocationHoraFim = findViewById<EditText>(R.id.editAllocationHoraIni)
        val spnDiaSemana = findViewById<Spinner>(R.id.spinnerSemanaCreate)
        val spnCurso = findViewById<Spinner>(R.id.spinnerCourseCreate)
        val spnProfessor = findViewById<Spinner>(R.id.spnProfessorCreate)


        repositoryCourse.buscarCursos(
            onCall = { courses ->
                if (courses != null) {
                    // Crie uma lista de pares (nome, id)
                    CoursePairs = courses.map { it.name to it.id }

                    // Extraia apenas os nomes dos departamentos para configurar o Spinner
                    val coursesNames: List<String> = CoursePairs.map { it.first }

                    // Configura o Adapter do Spinner
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, coursesNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnCurso.adapter = adapter

                    // Definir a seleção inicial (Placeholder)
                    spnCurso.setSelection(0)
                } else {
                    // Tratamento caso a lista de departamentos seja nula
                    Toast.makeText(this, "Nenhum curso encontrado", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                // Tratamento de erro
                Toast.makeText(this, "Erro ao buscar curso", Toast.LENGTH_SHORT).show()
            }
        )


        repositoryProf.buscarProfessor(
            onCall = { professors ->
                if (professors != null) {
                    // Crie uma lista de pares (nome, id)
                    ProfessorPairs = professors.map { it.name to it.id }

                    val professorsNames: List<String> = ProfessorPairs.map { it.first }

                    // Configura o Adapter do Spinner
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, professorsNames)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spnProfessor.adapter = adapter

                    // Definir a seleção inicial (Placeholder)
                    spnProfessor.setSelection(0)
                } else {
                    // Tratamento caso a lista de departamentos seja nula
                    Toast.makeText(this, "Nenhum professor encontrado", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                // Tratamento de erro
                Toast.makeText(this, "Erro ao buscar professor", Toast.LENGTH_SHORT).show()
            }
        )

       /*

        btnCreateAllocation.setOnClickListener {
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
        } */
    }
}
