package com.scrum.movieapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.scrum.movieapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.txtAdmin.setOnClickListener {
            val intent = Intent(this@LoginActivity, AdminLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please Complete Data", Toast.LENGTH_SHORT).show()
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Log.d(TAG, "loginUser:Success")
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                Log.w(TAG, "loginUserWithEmail:failure", task.exception)
                Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        private const val TAG = "RegisterActivity"
    }
}