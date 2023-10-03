package com.example.authlib.instagram

import android.app.Activity
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class Instagram(
    private val context: Context,
    private val clientId: String,
    private val clientSecret: String,
    private val redirectUrl: String,
) {


    private val instagramDialog by lazy {
        InstagramDialog(
            context,
            instagramCodeReceiver,
            redirectUrl,
            clientId
        )
    }
    private lateinit var client: OkHttpClient

    private val instagramCodeReceiver = object : InstagramCodeReceiver {
        lateinit var instagramAuthentication: InstagramAuthentication;
        override fun onCodeReceived(code: String) {
            getAccessToken(code, instagramAuthentication)
        }
    }

    fun instagramLogin(callback: InstagramAuthentication) {
        instagramDialog.setCancelable(true)
        instagramDialog.show()
        instagramCodeReceiver.instagramAuthentication = callback;
    }

    private fun getAccessToken(code: String, callback: InstagramAuthentication) {
        client = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("client_id", clientId)
            .add("client_secret", clientSecret)
            .add("grant_type", "authorization_code")
            .add("redirect_uri", redirectUrl)
            .add("code", code)
            .build()

        val request = Request.Builder()
            .url("https:api.instagram.com/oauth/access_token")
            .post(formBody)
            .build()

        try {

            GlobalScope.launch(Dispatchers.IO) {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val json = JSONObject(responseBody)

                    val accessToken = json.getString("access_token")
                    val userId = json.getLong("user_id")

                    (context as Activity).runOnUiThread {
                        callback.onSuccess(accessToken, userId)
                    }
                } else {
                    (context as Activity).runOnUiThread {
                        callback.onError(response.message)
                    }
                }
            }
        } catch (e: IOException) {
            (context as Activity).runOnUiThread {
                callback.onException(e)
                e.printStackTrace()
            }
        }
    }
}