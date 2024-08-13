package com.example.professorallocation.Main.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.Main.Models.Professor

class ProfessorAdapter(
    private val professors: List<Professor>,
    private val onViewClick: (Professor) -> Unit
) : RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listdefault_view, parent, false)
        return ProfessorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfessorViewHolder, position: Int) {
        val professor = professors[position]
        holder.professorName.text = professor.name
        holder.btnView.setOnClickListener { onViewClick(professor) }
    }

    override fun getItemCount(): Int = professors.size

    class ProfessorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val professorName: TextView = itemView.findViewById(R.id.ItemName)
        val btnView: Button = itemView.findViewById(R.id.btnView)
    }
}
