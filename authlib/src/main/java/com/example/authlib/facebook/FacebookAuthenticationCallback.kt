package com.example.authlib.facebook

import com.facebook.FacebookException
import com.facebook.login.LoginResult

interface FacebookAuthenticationCallback {
    fun onSuccess(result: LoginResult?)
    fun onCancel()
    fun onError(error: FacebookException?)
}