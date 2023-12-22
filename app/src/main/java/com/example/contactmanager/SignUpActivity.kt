package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.contactmanager.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : ComponentActivity() {

//    lateinit var binding: ActivityMainBinding

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activuty)

        //all variables and finding them by their ids from xml file
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            //taking all required inputs from users as data
            val email = etEmail.text.toString()
            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            //creating object called 'user' to call User data class
            val user = Users(email,name,username,password)

            databaseReference = FirebaseDatabase.getInstance().getReference("Users")

            //sets user object in setValue
            databaseReference.child(username).setValue(user).addOnSuccessListener {
                //it clear input fields after click on sign up button
                etEmail.text?.clear()
                etName.text?.clear()
                etUsername.text?.clear()
                etPassword.text?.clear()

                Toast.makeText(this,"You have signed up successfully",Toast.LENGTH_SHORT).show()
            }
                //if its fail to store data or problem at our side
                .addOnFailureListener{
                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                }

        }
        //for sign in text
        val tvSignInText = findViewById<TextView>(R.id.tvSignInText)
        tvSignInText.setOnClickListener {
            //redirect to sign in screen
            val singinIntent = Intent(this, SignInActivity::class.java)
            startActivity(singinIntent)
        }
    }
}

