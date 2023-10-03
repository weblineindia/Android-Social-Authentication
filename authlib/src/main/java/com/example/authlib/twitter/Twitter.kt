package com.example.authlib.twitter

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

class Twitter(private val context: Context) {


    private var provider = OAuthProvider.newBuilder("twitter.com")
        .addCustomParameter(
            "lang", "en"
        )

    fun signInWithTwitter(firebaseAuth: FirebaseAuth, callback: TwitterAuthentication) {
        firebaseAuth.startActivityForSignInWithProvider(context as Activity, provider.build())
            .addOnSuccessListener { authResult ->
                callback.addOnSuccessListener(authResult)
            }
            .addOnFailureListener { e ->
                callback.addOnFailureListener(e)
            }
    }

    fun twitterLogOut(firebaseAuth: FirebaseAuth, callback: TwitterLogOutCallback) {
        firebaseAuth.signOut()
        val user = firebaseAuth.currentUser
        if (user == null) {
            callback.onSuccess()
        } else {
            callback.onFailure()
        }
    }


}