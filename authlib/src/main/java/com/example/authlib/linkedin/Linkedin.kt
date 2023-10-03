package com.example.authlib.linkedin

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class Linkedin(private val context: Context) {

    private val authUrl = "https://www.linkedin.com/oauth/v2/authorization"
    private val tokenUrl = "https://www.linkedin.com/oauth/v2/accessToken"
    private val scope = "email,profile,openid"
    private var linkedinDialog: Dialog? = null


    fun linkedinSignIn(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        callback: LinkedinAuthentication
    ) {
        val state = "linkedin" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        val linkedinAuthURLFull =
            "$authUrl?response_type=code&client_id=$clientId&scope=$scope&state=$state&redirect_uri=$redirectUrl"
        setupLinkedinWebViewDialog(
            linkedinAuthURLFull,
            clientId,
            clientSecret,
            redirectUrl,
            callback
        )
    }

    private fun setupLinkedinWebViewDialog(
        url: String,
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        callback: LinkedinAuthentication,
    ) {
        linkedinDialog = Dialog(context)
        val webView = WebView(context)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = LinkedInWebViewClient(clientId, clientSecret, redirectUrl, callback)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        linkedinDialog?.setContentView(webView)
        linkedinDialog?.show()
    }

    private inner class LinkedInWebViewClient(
        private val clientId: String,
        private val clientSecret: String,
        private val redirectUrl: String,
        private val callback: LinkedinAuthentication,
    ) : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request?.url.toString().startsWith(redirectUrl)) {
                handleUrl(request?.url.toString(), clientId, clientSecret, redirectUrl, callback)
                if (request?.url.toString().contains("?code=")) {
                    linkedinDialog?.dismiss()
                }
                return true
            }
            return false
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith(redirectUrl)) {
                handleUrl(url, clientId, clientSecret, redirectUrl, callback)
                // Close the dialog after getting the authorization code
                if (url.contains("?code=")) {
                    linkedinDialog?.dismiss()
                }
                return true
            }
            return false
        }

        private fun handleUrl(
            url: String,
            clientId: String,
            clientSecret: String,
            redirectUrl: String,
            callback: LinkedinAuthentication,
        ) {
            val uri = Uri.parse(url)
            if (url.contains("code")) {
                val linkedinCode = uri.getQueryParameter("code") ?: ""
                linkedInRequestForAccessToken(
                    linkedinCode,
                    redirectUrl,
                    clientId,
                    clientSecret,
                    callback
                )
            } else if (url.contains("error")) {
                val error = uri.getQueryParameter("error") ?: ""
                (context as Activity).runOnUiThread { callback.onError() }
            }
        }
    }

    private fun linkedInRequestForAccessToken(
        linkedinCode: String,
        redirectUrl: String,
        clientId: String,
        clientSecret: String,
        callback: LinkedinAuthentication
    ) {
        val grantType = "authorization_code"
        val postParams =
            "grant_type=$grantType&code=$linkedinCode&redirect_uri=$redirectUrl&client_id=$clientId&client_secret=$clientSecret"

        GlobalScope.launch(Dispatchers.Default) {
            try {
                val client = OkHttpClient()
                val requestBody: RequestBody = FormBody.Builder()
                    .add("Content-Type", "application/x-www-form-urlencoded")
                    .add("Content-Length", postParams.length.toString())
                    .add("grant_type", grantType)
                    .add("code", linkedinCode)
                    .add("redirect_uri", redirectUrl)
                    .add("client_id", clientId)
                    .add("client_secret", clientSecret)
                    .build()

                val request = Request.Builder()
                    .url(tokenUrl)
                    .post(requestBody)
                    .build()

                val response: Response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val jsonObject = JSONObject(responseBody)
                        val accessToken = jsonObject.getString("access_token")
                        val expiresIn = jsonObject.getInt("expires_in")
                        (context as Activity).runOnUiThread { callback.onSuccess(accessToken) }
                    } else {
                        (context as Activity).runOnUiThread { callback.onError() }
                    }
                } else {
                    (context as Activity).runOnUiThread { callback.onError() }
                }
            } catch (e: Exception) {
                (context as Activity).runOnUiThread { callback.onException(e) }
            }
        }
    }
}