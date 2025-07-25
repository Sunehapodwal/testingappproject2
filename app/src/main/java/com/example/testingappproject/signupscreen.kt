package com.example.testingappproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testingappproject.databinding.ActivitySignupscreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore


class signupscreen : AppCompatActivity() {
    var btn: Button? = null
    lateinit var binding: ActivitySignupscreenBinding
    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,systemBars.right, systemBars.bottom)
            insets
        }


        binding.btndone.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            val user = binding.etuser.text.toString().trim()
            val email = binding.etemail.text.toString().trim()
            val password = binding.etpass.text.toString().trim()
            val phone = binding.etphone.text.toString().trim()
            Log.d("Signup","$user $email $password $phone")
            if (user.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty() || phone.isNotEmpty()) {

                auth.createUserWithEmailAndPassword(
                    binding.etemail.text.toString(),
                    binding.etpass.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "register Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, homescren::class.java))
                            finish()
                        } else { Toast.makeText(this, "Signup Unsucessful:${task.exception?.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }}



