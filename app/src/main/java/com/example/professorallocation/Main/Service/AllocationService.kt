package com.example.professorallocation.Main.Service

import com.example.professorallocation.Main.Models.Allocation
import com.example.professorallocation.Main.Repository.RetrofitConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AllocationService {

    @GET("allocation")
    fun getAllocation() : Call<List<Allocation>>

    @POST("allocation")
    fun postAllocation(@Body curso: Allocation) : Call<Any>

    @GET("allocation/{id}")
    fun getAllocationId(@Path("id") allocationId: Int) : Call<Allocation>

    @PUT("allocation/{id}")
    fun putAllocation(@Path("id") allocationId: Int, @Body allocation: Allocation) : Call<Allocation>

    @DELETE("allocation/{id}")
    fun deleteAllocation(@Path("id") allocationId: Int) : Call<Any>

    companion object {
        fun create(): AllocationService {
            val retrofit = RetrofitConfig.retrofit
            return retrofit.create(AllocationService::class.java)
        }
    }

}