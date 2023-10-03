package com.example.authlib.instagram

import java.io.IOException

interface InstagramAuthentication {
    fun onSuccess(accessToken:String?,userId:Long?)
    fun onError(message: String)
    fun onException(e: IOException)
}