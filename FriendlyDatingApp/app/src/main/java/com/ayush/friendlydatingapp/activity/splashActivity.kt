package com.ayush.friendlydatingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ayush.friendlydatingapp.MainActivity
import com.ayush.friendlydatingapp.R
import com.ayush.friendlydatingapp.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val user= FirebaseAuth.getInstance().currentUser
        Handler(Looper.getMainLooper()).postDelayed({
            if(user==null)
                startActivity(Intent(this,LoginActivity:: class.java))
            else
                startActivity(Intent(this,MainActivity:: class.java))
            finish()
        },2000)
    }
}