package com.example.professorallocation.Main.Repository


import com.example.professorallocation.Main.Models.Course
import com.example.professorallocation.Main.Service.CourseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseRepository(val servico: CourseService) {

    fun criarCurso(course: Course, onCall: () -> Unit, onError: () -> Unit) {
        servico.postCursos(course).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError()
            }
        })
    }

    fun buscarCursos(onCall: (course: List<Course>?) -> Unit, onError: () -> Unit) {
        servico.getCursos().enqueue(object : Callback<List<Course>> {
            override fun onResponse(p0: Call<List<Course>>, response: Response<List<Course>>) {
                val curso = response.body()
                onCall(curso)
            }

            override fun onFailure(p0: Call<List<Course>>, p1: Throwable) {
                onError()
            }
        })
    }

    fun buscarCursoPorId(
        idCurso: Int,
        onCall: (curso: Course?) -> Unit,
        onError: (mensagem: String) -> Unit){

        servico.getCursoPorId(idCurso).enqueue(object : Callback<Course> {
            override fun onResponse(p0: Call<Course>, response: Response<Course>) {
                if (response.isSuccessful) {
                    val curso = response.body()
                    onCall(curso)
                } else {
                    if (response.code() == 404)
                        onError("Dado não exite na base")
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Course>, p1: Throwable) {
                val mensagem = p1.message
                if (mensagem != null)
                    onError(mensagem)
            }
        })

    }

    fun alterarCurso(
        idCurso: Int,
        curso: Course,
        onCall: (curso: Course?) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servico.putCurso(idCurso, curso).enqueue(object : Callback<Course> {
            override fun onResponse(p0: Call<Course>, p1: Response<Course>) {
                onCall(p1.body())
            }

            override fun onFailure(p0: Call<Course>, p1: Throwable) {
                onError(p1.message ?: "")
            }
        })
    }

    fun apagarCurso(
        idCurso: Int,
        onCall: (code: Int) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servico.deleteCurso(idCurso).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
              onCall(p1.code())
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError(p1.message ?: " Erro")
            }
        })
    }
}