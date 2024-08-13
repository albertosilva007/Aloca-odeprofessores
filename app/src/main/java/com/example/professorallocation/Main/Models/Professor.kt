package com.example.professorallocation.Main.Models


data class Professor(
    val id: Int,
    val cpf: String,
    val departmentId: Int,
    val department: Department?,
    val name: String
)


//data class Department_prof(
//    val departmentId: Int
//)
