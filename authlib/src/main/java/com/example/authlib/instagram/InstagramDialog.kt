package com.example.authlib.instagram

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.authlib.R

class InstagramDialog(
    context: Context,
    private val instagramCodeReceiver: InstagramCodeReceiver,
    private val redirectUrl: String,
    clientId: String
) :
    Dialog(context) {

    private val requestUrl: String
    private val instagramRedirectUrl: String


    init {
        this.instagramRedirectUrl = redirectUrl
        this.requestUrl =
            "https://api.instagram.com/" +
                    "oauth/authorize?client_id=" +
                    clientId +
                    "&redirect_uri=$instagramRedirectUrl" +
                    "&scope=user_profile,user_media&response_type=code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_dialog)
        initializeWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeWebView() {
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(requestUrl)
        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url?.startsWith(instagramRedirectUrl) == true) {
                    getToken(url)
                    this@InstagramDialog.dismiss()
                    return true
                }
                return false
            }
        }
    }

    private fun getToken(url: String?) {
        if (url?.contains("code=") == true) {
            val uri = Uri.parse(url)
            var code = uri.toString()
            code = code?.substring(code.lastIndexOf("=") + 1).toString()
            instagramCodeReceiver.onCodeReceived(code)
            dismiss()
        } else if (url?.contains("?error") == true) {
            dismiss()
        }
    }
}