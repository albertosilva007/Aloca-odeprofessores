package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.professorallocation.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

      val menuCourse = findViewById<Button>(R.id.btnCourse)
      menuCourse.setOnClickListener {
          val intent = Intent(this , CourseActivity :: class.java)
          startActivity(intent)

      }

      val menuProfessor = findViewById<Button>(R.id.btnProfessor)
        menuProfessor.setOnClickListener {
            val intent = Intent(this , ProfessorActivity :: class.java)
            startActivity(intent)

      }

      val menuDepartament = findViewById<Button>(R.id.btnDepartament)
        menuDepartament.setOnClickListener {
            val intent = Intent(this , DepartmentActivity :: class.java)
            startActivity(intent)

      }

        val menuAllocation = findViewById<Button>(R.id.btnAllocation)
        menuAllocation.setOnClickListener {
            val intent = Intent(this , AllocationActivity :: class.java)
            startActivity(intent)

      }

    }
}