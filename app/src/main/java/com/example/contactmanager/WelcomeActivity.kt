package com.example.contactmanager

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.contactmanager.databinding.WelcomeActivityBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WelcomeActivity : ComponentActivity() {

    private lateinit var binding: WelcomeActivityBinding

    private lateinit var database: DatabaseReference

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = WelcomeActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)

        dialog = Dialog(this)
        dialog.setContentView(R.layout.alert_dialogbox)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.alert_dialogbox_bg))

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etContactNumber = findViewById<TextInputEditText>(R.id.etContactNumber)

        binding.btnAdd.setOnClickListener {
            //taking input from user
            val name = etName.text.toString()
            val contactNumber = etContactNumber.text.toString()

            val contacts = Contacts(name,contactNumber)

            database = FirebaseDatabase.getInstance().getReference("Contact")
            //stores input in db in node called 'Contact'
            database.child(name).setValue(contacts).addOnSuccessListener {
                etName.text?.clear()
                etContactNumber.text?.clear()
            }
                .addOnSuccessListener {
                    Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
                }
            dialog.show()

            //button to close dialog box
            val btnOkay = findViewById<Button>(R.id.btnOkay)
            btnOkay.setOnClickListener {
            dialog.dismiss()
            }
            
        }
    }
}




