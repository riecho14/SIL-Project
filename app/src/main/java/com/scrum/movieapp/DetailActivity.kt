package com.scrum.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.scrum.movieapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val film: Film? = intent.getParcelableExtra("key_film")

        if (film != null) {
            binding.judulFilm.text = film.title
            binding.genreFilm.text = film.genre
            binding.pemainFilm.text = film.actors
            binding.deskripsiFilm.text = film.description

            Glide.with(this).load(film.image).into(binding.imageView2)
        }
    }
}