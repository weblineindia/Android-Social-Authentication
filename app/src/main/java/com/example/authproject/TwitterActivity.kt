package com.example.authproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.authlib.MyAuthentication
import com.example.authlib.twitter.TwitterLogOutCallback
import com.google.firebase.auth.FirebaseAuth

class TwitterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var twitterButton: Button
    private lateinit var myAuth: MyAuthentication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter)

        firebaseAuth = FirebaseAuth.getInstance()
        twitterButton = findViewById(R.id.twitter_button_logout)
        myAuth = MyAuthentication(this)
        myAuth.initTwitter()


        twitterButton.setOnClickListener {
            myAuth.twitterLogOut(firebaseAuth, object : TwitterLogOutCallback {
                override fun onSuccess() {
                    startActivity(Intent(this@TwitterActivity, MainActivity::class.java))
                    finish()
                }

                override fun onFailure() {
                    Toast.makeText(this@TwitterActivity, "Log out fail", Toast.LENGTH_SHORT).show()
                }

            })

        }

    }
}