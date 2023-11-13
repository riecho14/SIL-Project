package com.scrum.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.scrum.movieapp.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var rvFilm: RecyclerView
    private lateinit var list: ArrayList<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvFilm = binding.rvFilm
        rvFilm.layoutManager = LinearLayoutManager(this)
        rvFilm.setHasFixedSize(true)

        list = arrayListOf()
        getFilmData()

        binding.newFilm.setOnClickListener {
            val intent = Intent(this@AdminActivity, NewFilmActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFilmData() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://film-370da-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbRef = database.getReference("Film")

        Log.d("Database nya", dbRef.database.toString())

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(filmSnapshot in snapshot.children){
                        val film = filmSnapshot.getValue(Film::class.java)

                        Log.d("FirebaseData", "Film name: ${film?.title}, Description: ${film?.description}")

                        list.add(film!!)
                    }
                    rvFilm.adapter = ListFilmAdapter(list)
                }
                else{
                    Toast.makeText(this@AdminActivity, "Data is Null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("null_error","data")
                Toast.makeText(this@AdminActivity, "Database Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}