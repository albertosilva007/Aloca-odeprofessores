package com.example.professorallocation.Main.Service

import com.example.professorallocation.Main.Models.Course
import com.example.professorallocation.Main.Models.Department
import com.example.professorallocation.Main.Repository.RetrofitConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DepartmentService {

    @GET("departaments")
    fun getDepartamentos() : Call<List<Department>>

    @POST("departaments")
    fun postDepartamentos(@Body curso: Department) : Call<Any>

    @GET("departaments/{id}")
    fun getDepartamentosPorId(@Path("id") cursoId: Int) : Call<Department>

    @PUT("departaments/{id}")
    fun putDepartamentos(@Path("id") departmentId: Int, @Body department: Department) : Call<Department>

    @DELETE("departaments/{id}")
    fun deleteDepartamentos(@Path("id") departmentId: Int) : Call<Any>

    companion object {
        fun create(): DepartmentService {
            val retrofit = RetrofitConfig.retrofit
            return retrofit.create(DepartmentService::class.java)
        }
    }

}