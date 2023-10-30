package com.scrum.movieapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var rvFilm: RecyclerView
    private lateinit var list: ArrayList<Film>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFilm = findViewById(R.id.rv_film)
        rvFilm.layoutManager = LinearLayoutManager(this)
        rvFilm.setHasFixedSize(true)

        list = arrayListOf<Film>()
        getFilmData()
    }

    private fun getFilmData() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://film-370da-default-rtdb.asia-southeast1.firebasedatabase.app/")
        dbRef = database.getReference("Film")

        Log.d("Database nya", dbRef.database.toString())

        dbRef.addValueEventListener(object: ValueEventListener{
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
                    Toast.makeText(this@MainActivity, "Data is Null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("null_error","data")
                Toast.makeText(this@MainActivity, "Database Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}