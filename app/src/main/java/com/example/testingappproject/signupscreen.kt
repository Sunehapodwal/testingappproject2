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
            lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivitySignupscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val Uername = findViewById<EditText>(R.id.etuser)

        val phone = findViewById<EditText>(R.id.etphone)
        val donebutton = findViewById<Button>(R.id.btndone)
        val userNameStr = Uername.text.toString()
//        val emailStr = emailt.text.toString().trim()
//        val passwordStr = password.text.toString().trim()
        val phoneStr = phone.text.toString()

        donebutton.setOnClickListener {

//            Log.d("auth"," email:$emailt password:$password")
//
//            if (userNameStr.isEmpty() || emailt.isEmpty() || password.isEmpty() || phoneStr.isEmpty()) {
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                 return@setOnClickListener
//            }
            auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(
                binding.etemail.text.toString(),
                binding.etpass.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        val user = User(Username = userNameStr, phone = phoneStr)
                        uid?.let {
                            db.collection("Users").document(it).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "registration successful",
                                        Toast.LENGTH_SHORT
                                    )
                                    startActivity(Intent(this, homescren::class.java))
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                         "Failed to save user:${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(this, "Error:${task.exception?.message}", Toast.LENGTH_SHORT)
                    }
                }
        }
    }
}