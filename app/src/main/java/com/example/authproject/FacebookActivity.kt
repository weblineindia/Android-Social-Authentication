package com.example.authproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authlib.MyAuthentication
import com.example.authlib.facebook.FacebookLogOutCallback

class FacebookActivity : AppCompatActivity() {

    private lateinit var facebookLogOutButton: Button
    private lateinit var myAuthentication: MyAuthentication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)


        facebookLogOutButton = findViewById(R.id.facebook_button_logout)
        myAuthentication = MyAuthentication(this)
        myAuthentication.initFacebook()


        facebookLogOutButton.setOnClickListener {
            myAuthentication.facebookLogout(object : FacebookLogOutCallback {
                override fun onSuccess() {
                    startActivity(Intent(this@FacebookActivity, MainActivity::class.java))
                    finish()
                }

                override fun onFailure() {
                    Toast.makeText(this@FacebookActivity, "Log out fail", Toast.LENGTH_LONG).show()
                }

            })

        }
    }
}