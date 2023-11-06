package com.scrum.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.scrum.movieapp.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}