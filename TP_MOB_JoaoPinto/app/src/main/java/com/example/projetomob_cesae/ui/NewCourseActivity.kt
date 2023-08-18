package com.example.projetomob_cesae.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.projetomob_cesae.R
import com.example.projetomob_cesae.R.*
import com.example.projetomob_cesae.database.DBHelper
import com.example.projetomob_cesae.databinding.ActivityNewCourseBinding


class NewCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        binding.buttonSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val local = binding.editLocal.text.toString()
            val dateBegin = binding.editDateBegin.text.toString()
            val dateEnd = binding.editDateEnd.text.toString()
            val price = binding.editPrice.text.toString()
            val duration = binding.editDuration.text.toString()
            val edition = binding.editEdition.text.toString()
            val imageId = 1

            if (name.isNotEmpty() && local.isNotEmpty() && duration.isNotEmpty() && edition.isNotEmpty()) {
                val res = db.insertCourse(
                    name,
                    local,
                    dateBegin,
                    dateEnd,
                    price,
                    duration,
                    edition,
                    imageId
                )
                if (res > 0) {
                    Toast.makeText(applicationContext, "Insert OK", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Insert Error", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.buttonCancel.setOnClickListener {
            setResult(0, i)
            finish()
        }



    }
}