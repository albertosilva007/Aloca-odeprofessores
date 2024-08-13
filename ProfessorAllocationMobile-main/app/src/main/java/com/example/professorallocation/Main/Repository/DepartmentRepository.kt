package com.example.professorallocation.Main.Repository


import com.example.professorallocation.Main.Models.Department
import com.example.professorallocation.Main.Service.DepartmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DepartmentRepository(val servico: DepartmentService) {

    fun criarDepartamento(Department: Department, onCall: () -> Unit, onError: () -> Unit) {
        servico.postDepartamentos(Department).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                onCall()
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError()
            }
        })
    }

    fun buscarDepartamento(onCall: (department:List<Department>?) -> Unit, onError: () -> Unit) {
        servico.getDepartamentos().enqueue(object : Callback<List<Department>> {
            override fun onResponse(p0: Call<List<Department>>, response: Response<List<Department>>) {
                val department = response.body()
                onCall(department)
            }

            override fun onFailure(p0: Call<List<Department>>, p1: Throwable) {
                onError()
            }
        })
    }

    fun buscarDepartamentoPorId(
        idDepartamento: Int,
        onCall: (departamento: Department?) -> Unit,
        onError: (mensagem: String) -> Unit){

        servico.getDepartamentosPorId(idDepartamento).enqueue(object : Callback<Department> {
            override fun onResponse(p0: Call<Department>, response: Response<Department>) {
                if (response.isSuccessful) {
                    val department = response.body()
                    onCall(department)
                } else {
                    if (response.code() == 404)
                        onError("Dado não exite na base")
                    else
                        onError(response.message())
                }
            }

            override fun onFailure(p0: Call<Department>, p1: Throwable) {
                val mensagem = p1.message
                if (mensagem != null)
                    onError(mensagem)
            }
        })

    }

    fun alterarDepartamento(
        idDepartment: Int,
        department: Department,
        onCall: (department: Department?) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servico.putDepartamentos(idDepartment, department).enqueue(object : Callback<Department> {
            override fun onResponse(p0: Call<Department>, p1: Response<Department>) {
                onCall(p1.body())
            }

            override fun onFailure(p0: Call<Department>, p1: Throwable) {
                onError(p1.message ?: "")
            }
        })
    }

    fun apagarDepartamento(
        idDeparamento: Int,
        onCall: (code: Int) -> Unit,
        onError: (mensagem: String) -> Unit
    ){
        servico.deleteDepartamentos(idDeparamento).enqueue(object : Callback<Any> {
            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
              onCall(p1.code())
            }

            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                onError(p1.message ?: " Erro")
            }
        })
    }
}