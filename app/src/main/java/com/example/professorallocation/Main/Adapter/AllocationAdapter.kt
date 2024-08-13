package com.example.professorallocation.Main.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.Main.Models.Allocation
import com.example.professorallocation.R
import com.example.professorallocation.Main.Models.Professor

class AllocationAdapter(
    private val allocations: List<Allocation>,
    private val onViewClick: (Allocation) -> Unit
) : RecyclerView.Adapter<AllocationAdapter.AllocationViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllocationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listdefault_view, parent, false)
        return AllocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AllocationViewHolder, position: Int) {
        val allocation = allocations[position]
        holder.allocationName.text = allocation.professor.name + '/' + allocation.course.name +'(' + allocation.startHour + ')'
        holder.btnView.setOnClickListener { onViewClick(allocation) }
    }

    override fun getItemCount(): Int = allocations.size

    class AllocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val allocationName: TextView = itemView.findViewById(R.id.ItemName)
        val btnView: Button = itemView.findViewById(R.id.btnView)
    }
}
