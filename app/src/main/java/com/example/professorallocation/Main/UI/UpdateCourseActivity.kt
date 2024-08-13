package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
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

class UpdateCourseActivity : AppCompatActivity() {

    private lateinit var repository: CourseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_update_course)

        val courseService = CourseService.create()
        repository = CourseRepository(courseService)

        val courseId = intent.getIntExtra("Course_id", -1)
        val courseName = intent.getStringExtra("Course_name") ?: ""

        val btnUpdateCourse = findViewById<ImageButton>(R.id.btnUpdateCourse)
        val editTextCourseName: EditText = findViewById(R.id.editCourse)
        editTextCourseName.setText(courseName)

        btnUpdateCourse.setOnClickListener {
            val courseName = editTextCourseName.text.toString()
            if (courseName.isNotEmpty()) {
                val novoCurso = Course(
                    id = courseId,
                    name = courseName
                )

                repository.alterarCurso(
                    idCurso = courseId,
                    curso = novoCurso,
                    onCall = {

                        Toast.makeText(this, "Curso alterado com sucesso!", Toast.LENGTH_SHORT).show()

                        val menuCourse = findViewById<Button>(R.id.btnCourse)
                        menuCourse.setOnClickListener {
                            val intent = Intent(this , CourseActivity :: class.java)
                            startActivity(intent)
                        }

                    },
                    onError = {
                        Toast.makeText(this, "Erro ao alterar curso!!", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "O nome do curso não pode estar vazio!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}