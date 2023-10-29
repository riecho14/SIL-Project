package com.scrum.movieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.scrum.movieapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var rvFilm: RecyclerView
    private val list = ArrayList<Film>()
    private lateinit var adapter: ListFilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFilm = binding.rvFilm
        rvFilm.setHasFixedSize(true)

        rvFilm.layoutManager = LinearLayoutManager(this)
    }

    private fun showRecyclerList() {
        rvFilm.layoutManager = LinearLayoutManager(this)
        val listFilmAdapter = ListFilmAdapter(list)
        rvFilm.adapter = listFilmAdapter

    }


    private fun getListLaptops(): ArrayList<Film> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDesc = resources.getStringArray(R.array.data_desc)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listLaptop = ArrayList<Film>()
        for(i in dataName.indices){
            val laptop = Film(dataName[i],dataDesc[i],dataPhoto[i])
            listLaptop.add(laptop)
        }
        return listLaptop
    }

}