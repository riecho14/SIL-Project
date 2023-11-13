package com.scrum.movieapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.scrum.movieapp.databinding.ActivityNewFilmBinding
import java.io.ByteArrayOutputStream

class NewFilmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewFilmBinding
    private lateinit var list_genre: Spinner
    private lateinit var dbRef: DatabaseReference
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list_genre = binding.edAddGenre
        val dataGenre = arrayOf("Aksi", "Animasi", "Drama", "Fantasi", "Komedi", "Horror", "Romance")
        val adapterGenre = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dataGenre)
        list_genre.adapter = adapterGenre

        binding.galleryButton.setOnClickListener { startGallery() }

        binding.addButton.setOnClickListener {
            addFilm()
        }

    }

    private fun startGallery(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            binding.previewImageView.setImageURI(imageUri)
        }
    }
    private fun addFilm() {
        if(imageUri != null){
            val imageRef = storageReference.child("images/${System.currentTimeMillis()}.jpg")
            val bitmap = (binding.previewImageView.drawable).toBitmap()
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()

                    val title = binding.edAddTitle.text.toString()
                    val genre = binding.edAddGenre.selectedItem.toString()
                    val actors = binding.edAddActors.text.toString()
                    val description = binding.edAddDescription.text.toString()

                    val film = Film(description, imageUrl, title, genre, actors)

                    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://film-370da-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    dbRef = database.reference.child("Film").push()

                    dbRef.setValue(film)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Film added successfully", Toast.LENGTH_SHORT).show()
                            finish()
                            startActivity(Intent(this@NewFilmActivity, AdminActivity::class.java))
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to add film", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }else{
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}