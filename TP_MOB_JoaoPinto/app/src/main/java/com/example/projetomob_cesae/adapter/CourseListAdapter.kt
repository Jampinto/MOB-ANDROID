package com.example.projetomob_cesae.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomob_cesae.R
import com.example.projetomob_cesae.adapter.listener.CourseOnClickListener
import com.example.projetomob_cesae.adapter.viewholder.CourseViewHolder
import com.example.projetomob_cesae.model.CoursesModel

class CourseListAdapter(
    private val courseList: List<CoursesModel>,
    private val courseOnClickListener: CourseOnClickListener
) : RecyclerView.Adapter<CourseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contact, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courseList[position]
        holder.textName.text = course.name
        holder.textLocal.text = course.local
        holder.textDateBegin.text = course.dateBegin
        when (course.imageId) {
            -1 -> {
                holder.image.setImageResource(R.drawable.sw)
            }
            -2 -> {
                holder.image.setImageResource(R.drawable.cs)
            }
            -5 -> {
                holder.image.setImageResource(R.drawable.multimedia)
            }
            -4 -> {
                holder.image.setImageResource(R.drawable.excel)
            }
            -3 -> {
                holder.image.setImageResource(R.drawable.dataanalyst)
            }
            -6 -> {
                holder.image.setImageResource(R.drawable.bi)
            }
            else -> {
                holder.image.setImageResource(R.drawable.imagedefault)
            }
        }
        holder.itemView.setOnClickListener {
            courseOnClickListener.clickListener(course)
        }

    }

}