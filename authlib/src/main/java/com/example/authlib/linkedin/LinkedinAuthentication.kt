package com.example.authlib.linkedin

interface LinkedinAuthentication {
    fun onSuccess(accessToken:String)
    fun onError()
    fun onException(e:Exception)
}