package com.example.professorallocation.Main.Service

import com.example.professorallocation.Main.Models.Course
import com.example.professorallocation.Main.Repository.RetrofitConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CourseService {

    @GET("courses")
    fun getCursos() : Call<List<Course>>

    @POST("courses")
    fun postCursos(@Body curso: Course) : Call<Any>

    @GET("courses/{id}")
    fun getCursoPorId(@Path("id") cursoId: Int) : Call<Course>

    @PUT("courses/{id}")
    fun putCurso(@Path("id") cursoId: Int, @Body curso: Course) : Call<Course>

    @DELETE("courses/{id}")
    fun deleteCurso(@Path("id") cursoId: Int) : Call<Any>

    companion object {
        fun create(): CourseService {
            val retrofit = RetrofitConfig.retrofit
            return retrofit.create(CourseService::class.java)
        }
    }

}