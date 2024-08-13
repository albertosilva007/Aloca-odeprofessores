package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.professorallocation.Main.Models.Department
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.R

class CreateDepartmentActivity : AppCompatActivity() {

    private lateinit var repository: DepartmentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_department)

        val departmentService = DepartmentService.create()
        repository = DepartmentRepository(departmentService)

        val btnCreateDeparment = findViewById<ImageButton>(R.id.btnSaveDepartment)
        val edtDepartmentName = findViewById<EditText>(R.id.editNewDepartmento)

        btnCreateDeparment.setOnClickListener {
            val departmentName = edtDepartmentName.text.toString()
            if (departmentName.isNotEmpty()) {
                val novoDepartment = Department(
                    id = 0,
                    name = departmentName
                )

                repository.criarDepartamento(
                    Department = novoDepartment,
                    onCall = {

                       Toast.makeText(this, "Departmento criado com sucesso!", Toast.LENGTH_SHORT).show()

                        val menuDepartment = findViewById<Button>(R.id.btnDepartament)
                        menuDepartment.setOnClickListener {
                            val intent = Intent(this , DepartmentActivity :: class.java)
                            startActivity(intent)
                        }

                    },
                    onError = {
                        Toast.makeText(this, "Erro ao criar departmento!!", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "O nome do departmento não pode estar vazio!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
