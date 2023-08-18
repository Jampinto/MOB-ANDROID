package com.example.projetomob_cesae.adapter.listener

import com.example.projetomob_cesae.model.CoursesModel

class CourseOnClickListener (val clickListener: (course:CoursesModel) -> Unit) {
    fun onClick(course: CoursesModel) = clickListener
}