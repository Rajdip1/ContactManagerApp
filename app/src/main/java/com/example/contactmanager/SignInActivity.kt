package com.example.contactmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.contactmanager.ui.theme.ContactManagerTheme
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : ComponentActivity() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)

        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)

        btnSignIn.setOnClickListener {
            //taking input from users
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            //checks input field empty or not
            if(username.isNotEmpty()){
                readData(username)
            }
            else{
                Toast.makeText(this,"Please enter your username", Toast.LENGTH_SHORT).show()
            }
        }
    }//onCreate fun over

    private fun readData(username: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).get().addOnSuccessListener {

            //check username exist or not in database
            if(it.exists()){
                val username = it.child("username").value
                val password = it.child("password").value

                val welcomeIntent = Intent(this,WelcomeActivity::class.java)
                startActivity(welcomeIntent)
            }
            else{
                Toast.makeText(this,"user not exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }

}

