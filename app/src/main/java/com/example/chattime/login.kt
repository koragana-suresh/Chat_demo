package com.example.chattime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnsignup: Button

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnlogin=findViewById(R.id.btnlogin)
        btnsignup=findViewById(R.id.btnsignup)

        btnsignup.setOnClickListener{
            val intent=Intent(this,signup::class.java)
            startActivity(intent)
        }
       btnlogin.setOnClickListener{
           val email=edtEmail.text.toString()
           val password=edtPassword.text.toString()

           Login(email,password);
       }


    }

    private fun Login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent= Intent (this@login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@login,"user does not exist", Toast.LENGTH_SHORT).show()
                }
            }

    }

}