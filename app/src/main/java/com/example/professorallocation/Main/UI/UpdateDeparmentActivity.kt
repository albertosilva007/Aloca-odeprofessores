package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.professorallocation.Main.Models.Department
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.R

class UpdateDeparmentActivity : AppCompatActivity() {

    private lateinit var repository: DepartmentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_update_department)

        val departmentService = DepartmentService.create()
        repository = DepartmentRepository(departmentService)

        val departmenteId = intent.getIntExtra("Department_id", -1)
        val departmentName = intent.getStringExtra("Department_name") ?: ""

        val btnUpdateDepartmentName = findViewById<ImageButton>(R.id.btnUpdateDeparment)
        val editTextDepartmentName: EditText = findViewById(R.id.editDepartmento)
        editTextDepartmentName.setText(departmentName)

        btnUpdateDepartmentName.setOnClickListener {
            val departmentName = editTextDepartmentName.text.toString()
            if (departmentName.isNotEmpty()) {
                val novoDepartment = Department(
                    id = departmenteId,
                    name = departmentName
                )

                repository.alterarDepartamento(
                    idDepartment = departmenteId,
                    department = novoDepartment,
                    onCall = {

                        Toast.makeText(this, "Departmento alterado com sucesso!", Toast.LENGTH_SHORT).show()

                        val menuDepartment = findViewById<Button>(R.id.btnDepartament)
                        menuDepartment.setOnClickListener {
                            val intent = Intent(this , DepartmentActivity :: class.java)
                            startActivity(intent)
                        }

                    },
                    onError = {
                        Toast.makeText(this, "Erro ao alterar Departmento!!", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "O nome do Departmento não pode estar vazio!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}