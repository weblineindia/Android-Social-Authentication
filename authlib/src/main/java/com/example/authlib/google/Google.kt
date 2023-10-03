package com.example.authlib.google

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class Google(context: Context, serverClientId: String) {

    private var googleSignInClient: GoogleSignInClient? = null
    private val activityResultLauncher: ActivityResultLauncher<Intent>
    private var signInResultCallback: GoogleAuthentication? = null

    init {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId).requestEmail().build()

        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
        activityResultLauncher = (context as AppCompatActivity).registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val signedAccount: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(signedAccount)
                try {
                    val account = task.getResult(ApiException::class.java)
                    signInResultCallback?.onResultSuccess(account)
                } catch (e: ApiException) {
                    signInResultCallback?.onResultError(e)
                }
            } else {
                signInResultCallback?.onCancel()
            }
        }
    }

    fun signIn(callback: GoogleAuthentication) {
        signInResultCallback = callback
        val signInIntent = googleSignInClient?.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    fun googleLogOut(callback: GoogleLogOutCallback) {
        googleSignInClient?.signOut()?.addOnCompleteListener {
            callback.onSignOut(it)
        }
    }
}