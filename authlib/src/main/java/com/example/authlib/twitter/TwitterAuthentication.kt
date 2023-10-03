package com.example.authlib.twitter

import com.google.firebase.auth.AuthResult
import java.lang.Exception

interface TwitterAuthentication {
    fun addOnSuccessListener(authResult: AuthResult)
    fun addOnFailureListener(exception: Exception)
}