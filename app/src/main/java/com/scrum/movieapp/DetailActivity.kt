package com.scrum.movieapp

import android.os.Bundle
import android.widget.Toast
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
            binding.buyFilm.text = film.title
            binding.genreFilm.text = film.genre
            binding.pemainFilm.text = film.actors
            binding.deskripsiFilm.text = film.description
            binding.hargaFilm.text = film.price

            Glide.with(this).load(film.image).into(binding.imageView2)
        }

        binding.hargaFilm.setOnClickListener {
            Toast.makeText(this, "Pembayaran berhasil", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}