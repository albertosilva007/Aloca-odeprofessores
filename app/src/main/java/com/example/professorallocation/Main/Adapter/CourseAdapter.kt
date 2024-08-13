package com.example.professorallocation.Main.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.professorallocation.R
import com.example.professorallocation.Main.Models.Course

class CourseAdapter(
    private val courses: List<Course>,
    private val onEditClick: (Course) -> Unit,
    private val onDeleteClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.listdefault, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.courseName.text = course.name
        holder.btnEdit.setOnClickListener { onEditClick(course) }
        holder.btnDelete.setOnClickListener { onDeleteClick(course) }
    }

    override fun getItemCount(): Int = courses.size

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseName: TextView = itemView.findViewById(R.id.ItemName)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditProf)
        val btnDelete: Button = itemView.findViewById(R.id.btnNewItem)
    }
}
