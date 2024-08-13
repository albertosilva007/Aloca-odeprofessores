package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.professorallocation.Main.Models.Course
import com.example.professorallocation.Main.Repository.CourseRepository
import com.example.professorallocation.Main.Service.CourseService
import com.example.professorallocation.R

class CreateCourseActivity : AppCompatActivity() {

    private lateinit var repository: CourseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_course)

        val courseService = CourseService.create()
        repository = CourseRepository(courseService)

        val btnCreateCourse = findViewById<ImageButton>(R.id.btnSaveCourse)
        val edtCourseName = findViewById<EditText>(R.id.editNewCourse)

        btnCreateCourse.setOnClickListener {
            val courseName = edtCourseName.text.toString()
            if (courseName.isNotEmpty()) {
                val novoCurso = Course(
                    id = 0,
                    name = courseName
                )

                repository.criarCurso(
                    course = novoCurso,
                    onCall = {

                       Toast.makeText(this, "Curso criado com sucesso!", Toast.LENGTH_SHORT).show()

                        val menuCourse = findViewById<Button>(R.id.btnCourse)
                        menuCourse.setOnClickListener {
                            val intent = Intent(this , CourseActivity :: class.java)
                            startActivity(intent)
                        }

                    },
                    onError = {
                        Toast.makeText(this, "Erro ao criar curso!!", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "O nome do curso não pode estar vazio!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
