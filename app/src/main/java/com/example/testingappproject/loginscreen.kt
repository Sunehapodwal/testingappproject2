package com.example.testingappproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class logincreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loginscreen)
        auth = FirebaseAuth.getInstance()
        val emailEditText: EditText= findViewById(R.id.Email)
        val passwordEditText: EditText= findViewById(R.id.password)
        val donebtn: Button=findViewById(R.id.donebtn)
        donebtn.setOnClickListener{
          val email= emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener (this){ task->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, homescren::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
