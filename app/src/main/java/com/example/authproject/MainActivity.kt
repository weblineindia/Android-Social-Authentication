package com.example.authproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authlib.MyAuthentication
import com.example.authlib.facebook.FacebookAuthenticationCallback
import com.example.authlib.google.GoogleAuthentication
import com.example.authlib.instagram.InstagramAuthentication
import com.example.authlib.linkedin.LinkedinAuthentication
import com.example.authlib.twitter.TwitterAuthentication
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var googleLoginButton: Button
    private lateinit var customFacebookButton: Button
    private lateinit var instagramLoginButton: Button
    private lateinit var facebookButton: LoginButton
    private lateinit var twitterLoginButton: Button
    private lateinit var myAuthentication: MyAuthentication
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var linkedinLoginButton: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        googleLoginButton = findViewById(R.id.google_button)
        customFacebookButton = findViewById<Button>(R.id.faebook_custom_button)
        facebookButton = findViewById(R.id.facebook_button)
        twitterLoginButton = findViewById(R.id.twitter_button)
        instagramLoginButton = findViewById(R.id.instagram_button)
        linkedinLoginButton = findViewById(R.id.linkedin_button)
        firebaseAuth = FirebaseAuth.getInstance()
        myAuthentication = MyAuthentication(this)
        myAuthentication.initGoogle(getString(R.string.google_client_id))
        myAuthentication.initFacebook()
        myAuthentication.initTwitter()
        myAuthentication.initInstagram(
            getString(R.string.instagram_client_id),
            getString(R.string.instagram_client_secret),
            getString(R.string.instagram_redirect_url),
        )
        myAuthentication.initLinkedin()

        linkedinLoginButton.setOnClickListener {
            myAuthentication.linkedinSignIn(
                getString(R.string.linkedin_client_id),
                getString(R.string.linkedin_client_secret),
                getString(R.string.linkedin_redirect_url),
                object : LinkedinAuthentication {
                    override fun onSuccess(accessToken: String) {
                        toast("linkedin accesstoken . : $accessToken")
                        Log.d("linkedin", "accesstoken $accessToken")
                    }

                    override fun onError() {
                        toast("linkedin error")
                        Log.d("linkedin", "error")
                    }

                    override fun onException(e: Exception) {
                        toast("linked ex. $e")
                        Log.d("linkedin", "exception $e")
                    }

                }
            )
        }


        googleLoginButton.setOnClickListener {
            myAuthentication.googleLogin(
                object : GoogleAuthentication {
                    override fun onResultSuccess(googleSignInAccount: GoogleSignInAccount) {
                        startActivity(Intent(this@MainActivity, GoogleActivity::class.java))
                        finish()
                    }

                    override fun onResultError(e: ApiException) {
                        toast("google error $e")
                    }

                    override fun onCancel() {
                        toast("google cancel")
                    }

                })
        }



        customFacebookButton.setOnClickListener {
            myAuthentication.facebookSignIn(object : FacebookAuthenticationCallback {
                override fun onSuccess(result: LoginResult?) {
                    if (result != null) {
                        toast("fb Custom Button success : $result")
                        Log.d("loginResult", "${result.accessToken}")
                        startActivity(
                            Intent(this@MainActivity, FacebookActivity::class.java).addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            )
                        )
                        finish()
                    }

                }

                override fun onCancel() {
                    toast("fb custom button cancel")
                }

                override fun onError(error: FacebookException?) {
                    toast("fb custom button success $error")
                }

            })
        }


        // facebook button code

        facebookButton.setOnClickListener {
            myAuthentication.facebookButtonSignIn(
                this.facebookButton,
                object : FacebookAuthenticationCallback {
                    override fun onSuccess(result: LoginResult?) {
                        startActivity(
                            Intent(this@MainActivity, FacebookActivity::class.java).addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            )
                        )
                        toast("facebook button success")
                        finish()
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException?) {
                        toast("facebook button error $error")
                    }

                })
        }


//        TWITTER LOGIN


        twitterLoginButton.setOnClickListener {
            myAuthentication.twitterLogin(firebaseAuth, object : TwitterAuthentication {
                override fun addOnSuccessListener(authResult: AuthResult) {
                    startActivity(
                        Intent(this@MainActivity, TwitterActivity::class.java).addFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    )
                    toast("twitter success $authResult")
                }

                override fun addOnFailureListener(exception: Exception) {
                    Log.d("twitter", "$exception")
                    toast("twitter exception $exception")
                }

            })
        }


//        Instagram login

        instagramLoginButton.setOnClickListener {
            myAuthentication.instagramSignIn(
                object : InstagramAuthentication {
                    override fun onSuccess(accessToken: String?, userId: Long?) {
                        if (accessToken != null && userId != null) {
                            Log.d("instagram", "$accessToken")
                            toast("instagram success $accessToken")
                        } else {
                            toast("instagram null")
                        }
                    }

                    override fun onError(message: String) {
                        toast("instagram error $message")
                    }

                    override fun onException(e: IOException) {
                        toast("instagram exception $e")
                    }

                })
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        myAuthentication.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}


