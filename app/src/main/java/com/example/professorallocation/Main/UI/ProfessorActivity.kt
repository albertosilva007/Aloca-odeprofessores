package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.Main.Adapter.ProfessorAdapter
import com.example.professorallocation.Main.Repository.ProfessorRepository
import com.example.professorallocation.Main.Service.ProfessorService
import com.example.professorallocation.R

class ProfessorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: ProfessorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_professor)

        recyclerView = findViewById(R.id.professorList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val professorService = ProfessorService.create()
        repository = ProfessorRepository(professorService)

        buscarProfessors()

        val newProfessor = findViewById<Button>(R.id.newProfessor)
        newProfessor.setOnClickListener {
             val intent = Intent(this , CreateProfessorActivity :: class.java)
             startActivity(intent)
        }


    }


    private fun buscarProfessors() {
        repository.buscarProfessor(
            onCall = { professors ->
                if (professors != null) {
                    val adapter = ProfessorAdapter(professors,
                        onViewClick = { professor ->

                            val intent = Intent(this, URDProfessor::class.java)
                                 intent.putExtra("Professor_id", professor.id)
                                 intent.putExtra("Professor_name", professor.name)
                                 intent.putExtra("Professor_CPF", professor.cpf)
                                 intent.putExtra("Professor_Departamento", professor.department?.id ?: -1)
                            startActivity(intent)
                        }


                    )
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "A lista de professors está vazia!", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            onError = {
                Log.e("ProfessorActivity", "Erro ao buscar professors!")

            }
        )
    }

}

