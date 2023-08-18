package com.example.projetomob_cesae.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projetomob_cesae.R
import com.example.projetomob_cesae.database.DBHelper
import com.example.projetomob_cesae.databinding.ActivityCourseDetailBinding
import com.example.projetomob_cesae.model.CoursesModel

class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailBinding
    private lateinit var db: DBHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var imageId: Int? = -1
    private var courseModel = CoursesModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)

        if (id != null) {
            courseModel = db.getCourse(id)
            populate()
        } else {
            finish()
        }

        binding.buttonBack.setOnClickListener {
            setResult(0, i)
            finish()
        }

        binding.buttonEdit.setOnClickListener {
            binding.layoutEditDelete.visibility = View.VISIBLE
            binding.layoutEdit.visibility = View.GONE
            changeEditText(true)
        }

        binding.buttonCancel.setOnClickListener {
            binding.layoutEditDelete.visibility = View.GONE
            binding.layoutEdit.visibility = View.VISIBLE
            populate()
            changeEditText(false)
        }
        binding.buttonSave.setOnClickListener {
            val res = db.updateCourse(
                id = courseModel.id,
                name = binding.editName.text.toString(),
                local = binding.editLocal.text.toString(),
                dateBegin = binding.editDateBegin.text.toString(),
                dateEnd = binding.editDateEnd.text.toString(),
                price = binding.editPrice.text.toString(),
                duration = binding.editDuration.text.toString(),
                edition = binding.editEdition.text.toString(),
                imageId = courseModel.imageId
            )

            if (res > 0) {
                Toast.makeText(applicationContext, "Update OK", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Update Error", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }

        }
        binding.buttonDelete.setOnClickListener {
            val res = db.deleteCourse(courseModel.id)

            if (res > 0) {
                Toast.makeText(applicationContext, "Delete OK", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Delete Error", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }

        }
        /*
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                imageId = it.data?.extras?.getInt("id")
                binding.imageCourse.setImageDrawable(resources.getDrawable(id!!))
            } else {
                imageId = -1
                binding.imageCourse.setImageResource(R.drawable.imagedefault)
            }
        }

         */

    }

    private fun changeEditText(status: Boolean) {
        binding.editName.isEnabled = status
        binding.editLocal.isEnabled = status
        binding.editDateBegin.isEnabled = status
        binding.editDateEnd.isEnabled = status
        binding.editPrice.isEnabled = status
        binding.editDuration.isEnabled = status
        binding.editEdition.isEnabled = status
    }

    private fun populate() {

        binding.editName.setText(courseModel.name)
        binding.editLocal.setText(courseModel.local)
        binding.editDateBegin.setText(courseModel.dateBegin)
        binding.editDateEnd.setText(courseModel.dateEnd)
        binding.editPrice.setText(courseModel.price)
        binding.editDuration.setText(courseModel.duration)
        binding.editEdition.setText(courseModel.edition)
        binding.editEdition.setText(courseModel.edition)
        if (courseModel.imageId == -1) {
            binding.imageCourse.setImageResource(R.drawable.sw)
        } else if (courseModel.imageId == -2) {
            binding.imageCourse.setImageResource(R.drawable.cs)
        } else if (courseModel.imageId == -3) {
            binding.imageCourse.setImageResource(R.drawable.dataanalyst)
        } else if (courseModel.imageId == -4) {
            binding.imageCourse.setImageResource(R.drawable.excel)
        } else if (courseModel.imageId == -5) {
            binding.imageCourse.setImageResource(R.drawable.multimedia)
        } else if (courseModel.imageId == -6) {
            binding.imageCourse.setImageResource(R.drawable.bi)
        } else
            binding.imageCourse.setImageResource(R.drawable.imagedefault)

    }
}