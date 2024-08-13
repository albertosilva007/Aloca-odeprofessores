package com.example.professorallocation.Main.Repository


import com.example.professorallocation.Main.Models.Allocation
import com.example.professorallocation.Main.Service.AllocationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllocationRepository(val servico: AllocationService) {

    fun criarAllocation(Allocation: Allocation, onCall: () -> Unit, onError: () -> Unit) {
        servico.postAllocation(Allocation).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError()
            }
        })
    }

    fun buscarAllocation(onCall: (allocation:List<Allocation>?) -> Unit, onError: () -> Unit) {
        servico.getAllocation().enqueue(object : Callback<List<Allocation>> {
            override fun onResponse(p0: Call<List<Allocation>>, response: Response<List<Allocation>>) {
                val allocation = response.body()
                onCall(allocation)
            }

            override fun onFailure(p0: Call<List<Allocation>>, p1: Throwable) {
                onError()
            }
        })
    }

    fun buscarAllocationId(
        idAllocation: Int,
        onCall: (allocation: Allocation?) -> Unit,
        onError: (mensagem: String) -> Unit){

        servico.getAllocationId(idAllocation).enqueue(object : Callback<Allocation> {
            override fun onResponse(p0: Call<Allocation>, response: Response<Allocation>) {
                if (response.isSuccessful) {
                    val allocation = response.body()
                    onCall(allocation)
                } else {
                    if (response.code() == 404)
                        onError("Dado não exite na base")
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Allocation>, p1: Throwable) {
                val mensagem = p1.message
                if (mensagem != null)
                    onError(mensagem)
            }
        })

    }

    fun alterarAllocation(
        idAllocation: Int,
        allocation: Allocation,
        onCall: (allocation: Allocation?) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servico.putAllocation(idAllocation, allocation).enqueue(object : Callback<Allocation> {
            override fun onResponse(p0: Call<Allocation>, p1: Response<Allocation>) {
                onCall(p1.body())
            }

            override fun onFailure(p0: Call<Allocation>, p1: Throwable) {
                onError(p1.message ?: "")
            }
        })
    }

    fun apagarAllocation(
        idAllocation: Int,
        onCall: (code: Int) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servico.deleteAllocation(idAllocation).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
              onCall(p1.code())
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError(p1.message ?: " Erro")
            }
        })
    }
}