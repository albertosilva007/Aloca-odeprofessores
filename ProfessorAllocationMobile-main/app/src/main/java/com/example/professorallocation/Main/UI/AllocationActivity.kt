package com.example.professorallocation.Main.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.Main.Adapter.AllocationAdapter
import com.example.professorallocation.Main.Adapter.ProfessorAdapter
import com.example.professorallocation.Main.Repository.AllocationRepository
import com.example.professorallocation.Main.Repository.ProfessorRepository
import com.example.professorallocation.Main.Service.AllocationService
import com.example.professorallocation.Main.Service.ProfessorService
import com.example.professorallocation.R

class AllocationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: AllocationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_allocation)

        recyclerView = findViewById(R.id.allocationList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val allocationService = AllocationService.create()
        repository = AllocationRepository(allocationService)

        buscarAllocation()

        val newAllocation = findViewById<Button>(R.id.newAllocation)
        newAllocation.setOnClickListener {
            val intent = Intent(this , CreateAllocationActivity :: class.java)
            startActivity(intent)
        }


    }


    private fun buscarAllocation() {
        repository.buscarAllocation(
            onCall = { allocations ->
                if (allocations != null) {
                    val adapter = AllocationAdapter(allocations,
                         onViewClick = { allocation ->

                            val intent = Intent(this, URDAllocation::class.java)
                            intent.putExtra("Allocation_id"       , allocation.id)
                            intent.putExtra("Allocation_Id_Prof", allocation.professor.id ?: -1)
                            intent.putExtra("Allocation_Id_Curso", allocation.course.id ?: -1)
                            intent.putExtra("Allocation_Dia"      , allocation.day)
                            intent.putExtra("Allocation_Hora_Ini" , allocation.startHour)
                            intent.putExtra("Allocation_Hora_Fim" , allocation.endHour)
                            //intent.putExtra("Professor_Departamento", allocation.department?.id ?: -1)
                            startActivity(intent)
                        }


                    )
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "A lista de professors está vazia!", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            onError = {
                Log.e("ProfessorActivity", "Erro ao buscar professors!")

            }
        )
    }

}

