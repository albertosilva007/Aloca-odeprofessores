package com.example.professorallocation.Main.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.Main.Models.Department

class DepartamentAdapter(
    private val departments: List<Department>,
    private val onEditClick: (Department) -> Unit,
    private val onDeleteClick: (Department) -> Unit
) : RecyclerView.Adapter<DepartamentAdapter.DepartmentViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listdefault, parent, false)
        return DepartmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = departments[position]
        holder.departmentName.text = department.name
        holder.btnEdit.setOnClickListener { onEditClick(department) }
        holder.btnDelete.setOnClickListener { onDeleteClick(department) }
    }

    override fun getItemCount(): Int = departments.size

    class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val departmentName: TextView = itemView.findViewById(R.id.ItemName)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditProf)
        val btnDelete: Button = itemView.findViewById(R.id.btnNewItem)
    }
}
