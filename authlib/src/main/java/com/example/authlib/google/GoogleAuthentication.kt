package com.example.authlib.google

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException

interface GoogleAuthentication {
    fun onResultSuccess(googleSignInAccount: GoogleSignInAccount)
    fun onResultError(e:ApiException)
    fun onCancel()
}