package com.example.professorallocation.Main.Repository

import android.util.Log
import android.widget.Toast
import com.example.professorallocation.Main.Models.Professor
import com.example.professorallocation.Main.Service.ProfessorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfessorRepository(val servicoProfessor: ProfessorService) {

    fun criarProfessor(professor: Professor, onCall: () -> Unit, onError: (String) -> Unit) {
        servicoProfessor.postProfessor(professor).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    // Log para sucesso
                    Log.d("ProfessorRepository", "Professor criado com sucesso. Código de resposta: ${response.code()}")
                    onCall()
                } else {
                    // Log para erro de resposta
                    Log.e("ProfessorRepository", "Erro ao criar professor. Código de resposta: ${response.code()}, Mensagem: ${response.message()}")
                    onError("Erro ao criar professor: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                // Log para erro de falha
                Log.e("ProfessorRepository", "Falha na requisição ao criar professor", t)
                onError("Falha na requisição: ${t.message ?: "Desconhecido"}")
            }
        })
    }

    fun buscarProfessor(onCall: (professor: List<Professor>?) -> Unit, onError: () -> Unit) {
        servicoProfessor.getProfessor().enqueue(object : Callback<List<Professor>> {
            override fun onResponse(call: Call<List<Professor>>, response: Response<List<Professor>>) {
                if (response.isSuccessful) {
                    val professor = response.body()
                    Log.d("ProfessorRepository", "Success: ${response.body()}")
                    onCall(professor)
                } else {
                    Log.e("ProfessorRepository", "Response error: ${response.errorBody()?.string()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<List<Professor>>, t: Throwable) {
                Log.e("ProfessorRepository", "Failure: ${t.message}")
                onError()
            }
        })
    }



    fun buscarProfessorPorId(
        idProfessor: Int,
        onCall: (professor: Professor?) -> Unit,
        onError: (mensagem: String) -> Unit){

        servicoProfessor.getProfessorPorId(idProfessor).enqueue(object : Callback<Professor> {
            override fun onResponse(p0: Call<Professor>, response: Response<Professor>) {
                if (response.isSuccessful) {
                    val professor = response.body()
                    onCall(professor)
                } else {
                    if (response.code() == 404)
                        onError("Dado não exite na base")
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Professor>, p1: Throwable) {
                val mensagem = p1.message
                if (mensagem != null)
                    onError(mensagem)
            }

        })

    }


    fun alterarProfessor(
        idProfessor: Int,
        professor: Professor,
        onCall: (professor: Professor?) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servicoProfessor.putProfessor(idProfessor, professor).enqueue(object : Callback<Professor> {
            override fun onResponse(p0: Call<Professor>, p1: Response<Professor>) {
                onCall(p1.body())
            }

            override fun onFailure(p0: Call<Professor>, p1: Throwable) {
                onError(p1.message ?: "")
            }
        })
    }

    fun apagarProfessor(
        idProfessor: Int,
        onCall: (code: Int ) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servicoProfessor.deleteProfessor(idProfessor).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                onCall(p1.code())
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError(p1.message ?: " Erro")
            }
        })
    }

}