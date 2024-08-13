package com.example.professorallocation.Main.Models

import java.sql.Time
import java.time.LocalTime


data class Allocation(
    val id: Int,
    val professor: Professor,
    val course: Course,
    val endHour : String,
    val startHour: String,
    val day : String
)



