package com.example.testingappproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class logsignscreen : AppCompatActivity() {
    var btn:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logsignscreen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn=findViewById(R.id.LoginBTN)
        btn?.setOnClickListener {
            Toast.makeText(this, "on click message displayer", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, logincreen::class.java)
            startActivity(intent)
        }
        btn=findViewById(R.id.Sign)
        btn?.setOnClickListener {
            Toast.makeText(this,"on click message displayer",Toast.LENGTH_SHORT).show()
            var intent=Intent(this, signupscreen::class.java)
            startActivity(intent)
    }
}}
