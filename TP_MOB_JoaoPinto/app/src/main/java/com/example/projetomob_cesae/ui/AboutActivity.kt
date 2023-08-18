package com.example.projetomob_cesae.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetomob_cesae.R
import com.example.projetomob_cesae.databinding.ActivityAboutBinding
import com.example.projetomob_cesae.databinding.ActivityCourseDetailBinding
import com.example.projetomob_cesae.databinding.ActivityLoginBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonBacklogin.setOnClickListener {
            finish()
        }
    }
}