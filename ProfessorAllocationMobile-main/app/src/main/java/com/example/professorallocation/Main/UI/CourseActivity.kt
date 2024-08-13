package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.Main.Adapter.CourseAdapter
import com.example.professorallocation.Main.Repository.CourseRepository
import com.example.professorallocation.Main.Service.CourseService
import com.example.professorallocation.R

class CourseActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: CourseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course)

        recyclerView = findViewById(R.id.courseList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val courseService = CourseService.create()
        repository = CourseRepository(courseService)

        buscarCursos()

        val newCourse = findViewById<Button>(R.id.newCourse)
        newCourse.setOnClickListener {
            val intent = Intent(this , CreateCourseActivity :: class.java)
            startActivity(intent)
        }




    }


    private fun buscarCursos() {
        repository.buscarCursos(
            onCall = { cursos ->
                if (cursos != null) {
                    val adapter = CourseAdapter(cursos,
                        onEditClick = { course ->

                            val intent = Intent(this, UpdateCourseActivity::class.java)
                            intent.putExtra("Course_id", course.id)
                            intent.putExtra("Course_name", course.name)
                            startActivity(intent)

                        },
                        onDeleteClick = { course ->


                            repository.apagarCurso(
                                idCurso = course.id,
                                onCall = { code ->
                                  if(code == 204)
                                    Toast.makeText(this, "Curso excluído com sucesso!", Toast.LENGTH_SHORT).show()
                                  else
                                    Toast.makeText(this, "Não foi possível excluir curso!", Toast.LENGTH_SHORT).show()

                                },
                                onError = { mensagem ->
                                    Toast.makeText(this, "Erro ao excluir curso: $mensagem", Toast.LENGTH_SHORT).show()
                                }
                            )

                            buscarCursos()
                        }

                    )
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "A lista de cursos está vazia!", Toast.LENGTH_SHORT).show()
                }
            },
            onError = {
                Log.e("CourseActivity", "Erro ao buscar cursos!")

            }
        )
    }







}
