package com.example.authlib.facebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class Facebook(private val context: Context) {

    private val loginManager by lazy { LoginManager.getInstance() }
    private val callbackManager by lazy { CallbackManager.Factory.create() }


    fun signInWithFacebook(callback: FacebookAuthenticationCallback) {
        loginManager.logInWithReadPermissions((context as Activity), listOf("email"))
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                callback.onSuccess(result)
            }

            override fun onCancel() {
                callback.onCancel()
            }

            override fun onError(error: FacebookException?) {
                callback.onError(error)
            }
        })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


    fun signInWithFacebookButton(facebookButton: LoginButton, callback: FacebookAuthenticationCallback) {
        facebookButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                callback.onSuccess(result)
            }

            override fun onCancel() {
                callback.onCancel()
            }

            override fun onError(error: FacebookException?) {
                callback.onError(error)
            }
        })
    }

    fun facebookLogOut(callback: FacebookLogOutCallback) {
        loginManager.logOut()
        val loggedIn = isFacebookLoggedIn()
        if (loggedIn) {
            callback.onFailure()
        } else {
            callback.onSuccess()
        }
    }

    private fun isFacebookLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return (accessToken != null) && !accessToken.isExpired
    }

}