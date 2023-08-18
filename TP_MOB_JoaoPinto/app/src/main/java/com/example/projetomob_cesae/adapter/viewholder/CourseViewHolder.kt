package com.example.projetomob_cesae.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetomob_cesae.R

class CourseViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image_course)
    val textName: TextView = view.findViewById(R.id.text_course_name)
    val textDateBegin: TextView = view.findViewById(R.id.text_date_begin)
    val textLocal: TextView = view.findViewById(R.id.text_course_local)
}