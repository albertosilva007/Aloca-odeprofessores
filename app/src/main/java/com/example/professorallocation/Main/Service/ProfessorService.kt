package com.example.professorallocation.Main.Service

import com.example.professorallocation.Main.Models.Professor
import com.example.professorallocation.Main.Repository.RetrofitConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfessorService {

    @GET("professors")
    fun getProfessor() : Call<List<Professor>>

    @POST("professors")
    fun postProfessor(@Body professor: Professor) : Call<Any>

    @GET("professors/{id}")
    fun getProfessorPorId(@Path("id") professorId: Int) : Call<Professor>

    @PUT("professors/{id}")
    fun putProfessor(@Path("id") professorId: Int, @Body professor: Professor) : Call<Professor>

    @DELETE("professors/{id}")
    fun deleteProfessor(@Path("id") professorId: Int) : Call<Any>

    companion object {
        fun create(): ProfessorService {
            val retrofit = RetrofitConfig.retrofit
            return retrofit.create(ProfessorService::class.java)
        }
    }

}