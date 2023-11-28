package com.scrum.movieapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.scrum.movieapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var list_genre: Spinner
    private lateinit var rvFilm: RecyclerView
    private lateinit var list: ArrayList<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFilm = findViewById(R.id.rv_film)
        rvFilm.layoutManager = LinearLayoutManager(this)
        rvFilm.setHasFixedSize(true)

        list_genre = binding.filter
        val dataGenre = arrayOf("All Genre", "Aksi", "Animasi", "Drama", "Fantasi", "Komedi", "Horror", "Romance")
        val adapterGenre = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dataGenre)
        list_genre.adapter = adapterGenre

        list = arrayListOf<Film>()
        getFilmData()

        binding.filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedGenre = dataGenre[p2]
                if(selectedGenre == "All Genre"){
                    showAllFilms()
                }else{
                    filterByGenre(selectedGenre)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun showAllFilms() {
        rvFilm.adapter = ListFilmAdapter(list)
    }

    private fun filterByGenre(genre: String) {
        val filteredList = ArrayList<Film>()

        for (film in list) {
            if (film.genre.equals(genre, ignoreCase = true)) {
                filteredList.add(film)
            }
        }

        if (filteredList.isNotEmpty()) {
            rvFilm.adapter = ListFilmAdapter(filteredList)
        } else {
            Toast.makeText(this, "Tidak ada film dengan genre $genre", Toast.LENGTH_SHORT).show()
            rvFilm.adapter = ListFilmAdapter(ArrayList())
        }
    }

    private fun getFilmData() {
        val database: FirebaseDatabase =
            FirebaseDatabase.getInstance("https://film-370da-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbRef = database.getReference("Film")

        Log.d("Database nya", dbRef.database.toString())

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (filmSnapshot in snapshot.children) {
                        val film = filmSnapshot.getValue(Film::class.java)

                        Log.d(
                            "FirebaseData",
                            "Film name: ${film?.title}, Description: ${film?.description}"
                        )

                        list.add(film!!)
                    }
                    rvFilm.adapter = ListFilmAdapter(list)
                } else {
                    Toast.makeText(this@MainActivity, "Data is Null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("null_error", "data")
                Toast.makeText(this@MainActivity, "Database Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}