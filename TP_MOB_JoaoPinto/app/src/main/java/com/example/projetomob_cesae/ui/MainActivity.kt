package com.example.projetomob_cesae.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetomob_cesae.R
import com.example.projetomob_cesae.adapter.CourseListAdapter
import com.example.projetomob_cesae.adapter.listener.CourseOnClickListener
import com.example.projetomob_cesae.database.DBHelper
import com.example.projetomob_cesae.databinding.ActivityMainBinding
import com.example.projetomob_cesae.model.CoursesModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var courseList: List<CoursesModel>

    //private lateinit var adapter: ArrayAdapter<CoursesModel>
    private lateinit var adapter: CourseListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbHelper: DBHelper
    private var ascDesc: Boolean = true

    //private var pos: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.recyclerViewCourses.layoutManager = LinearLayoutManager(applicationContext)

        loadList()

        binding.buttonLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }
        /*
        binding.listViewCourses.setOnItemClickListener { _, _, i, _ ->

            val intent = Intent(applicationContext, CourseDetailActivity::class.java)
            intent.putExtra("id", courseList[i].id)
            //startActivity(intent)
            // Toast.makeText(applicationContext, courseList[i].name, Toast.LENGTH_SHORT).show()
            result.launch(intent)
        }
         */

        binding.buttonAdd.setOnClickListener {
            result.launch(
                Intent(
                    android.content.Intent(
                        applicationContext,
                        NewCourseActivity::class.java
                    )
                )
            )
        }

        binding.buttonOrderDate.setOnClickListener {
            if(ascDesc){
                loadListDates()
            }else{
                courseList = courseList.reversed()
            }
            ascDesc = !ascDesc
            placeAdapter()
        }

        binding.buttonOrder.setOnClickListener {
            if (ascDesc) {
                loadList()
                binding.buttonOrder.setImageResource(R.drawable.baseline_arrow_upward_24)
            } else {
                binding.buttonOrder.setImageResource(R.drawable.baseline_arrow_downward_24)
            }
            ascDesc = !ascDesc
            courseList = courseList.reversed()
            placeAdapter()
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                loadList()
            } else if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun placeAdapter() {
        adapter = CourseListAdapter(courseList, CourseOnClickListener { course ->
            val intent = Intent(applicationContext, CourseDetailActivity::class.java)
            intent.putExtra("id", course.id)
            result.launch(intent)
        })
        binding.recyclerViewCourses.adapter = adapter
    }

    private fun loadList() {
        courseList =
            dbHelper.getAllCourse().sortedWith(compareBy { it.name })
        placeAdapter()

    }

    private fun loadListDates() {
        courseList = dbHelper.getAllCourseSortedByDate().sortedWith(compareBy { it.dateBegin })
        placeAdapter()
    }


}