package com.example.authproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authlib.google.GoogleLogOutCallback
import com.example.authlib.MyAuthentication
//import com.example.authlib.MyGoogleLogin
import com.google.android.gms.tasks.Task

class GoogleActivity : AppCompatActivity() {


    private lateinit var btnGoogleLogOut: Button
    private lateinit var myAuthentication: MyAuthentication


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google)

        btnGoogleLogOut = findViewById(R.id.google_button_logout)
        myAuthentication = MyAuthentication(this)
        myAuthentication.initGoogle(getString(R.string.google_client_id))

        btnGoogleLogOut.setOnClickListener {

            myAuthentication.googleLogOut(
                object : GoogleLogOutCallback {
                    override fun onSignOut(task: Task<Void>) {
                        startActivity(
                            Intent(this@GoogleActivity, MainActivity::class.java).addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            )
                        )
                        toast("Google logged out")
                        finish()
                    }

                })
        }


    }


    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}