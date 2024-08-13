package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.Main.Adapter.DepartamentAdapter
import com.example.professorallocation.Main.Repository.DepartmentRepository
import com.example.professorallocation.Main.Service.DepartmentService
import com.example.professorallocation.R

class DepartmentActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: DepartmentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_department)

        recyclerView = findViewById(R.id.departmentList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val departmentService = DepartmentService.create()
        repository = DepartmentRepository(departmentService)

        buscarDepartamentos()

        val newDepartment = findViewById<Button>(R.id.newDepartment)
        newDepartment.setOnClickListener {
            val intent = Intent(this , CreateDepartmentActivity :: class.java)
            startActivity(intent)
        }




    }


    private fun buscarDepartamentos() {
        repository.buscarDepartamento(
            onCall = { departament ->
                if (departament != null) {
                    val adapter = DepartamentAdapter(departament,
                        onEditClick = { department ->

                            val intent = Intent(this, UpdateDeparmentActivity::class.java)
                            intent.putExtra("Department_id", department.id)
                            intent.putExtra("Department_name", department.name)
                            startActivity(intent)

                        },
                        onDeleteClick = { department ->


                            repository.apagarDepartamento(
                                idDeparamento = department.id,
                                onCall = { code ->
                                    if(code == 204)
                                        Toast.makeText(this, "Departamento excluído com sucesso!", Toast.LENGTH_SHORT).show()
                                    else
                                        Toast.makeText(this, "Não foi possível excluir Departamento!", Toast.LENGTH_SHORT).show()

                                },
                                onError = { mensagem ->
                                    Toast.makeText(this, "Erro ao excluir Departamento: $mensagem", Toast.LENGTH_SHORT).show()
                                }
                            )

                            buscarDepartamentos()
                        }

                    )
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "A lista de departamento está vazia!", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                Log.e("DepartmentActivity", "Erro ao buscar departamento!")

            }
        )
    }







}
