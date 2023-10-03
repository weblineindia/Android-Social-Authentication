package com.example.authlib

import android.content.Context
import android.content.Intent
import com.example.authlib.facebook.Facebook
import com.example.authlib.facebook.FacebookAuthenticationCallback
import com.example.authlib.facebook.FacebookLogOutCallback
import com.example.authlib.google.Google
import com.example.authlib.google.GoogleAuthentication
import com.example.authlib.google.GoogleLogOutCallback
import com.example.authlib.instagram.Instagram
import com.example.authlib.instagram.InstagramAuthentication
import com.example.authlib.linkedin.Linkedin
import com.example.authlib.linkedin.LinkedinAuthentication
import com.example.authlib.twitter.Twitter
import com.example.authlib.twitter.TwitterAuthentication
import com.example.authlib.twitter.TwitterLogOutCallback
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth

class MyAuthentication(private val context: Context) {

    private var facebook: Facebook? = null
    private var instagram: Instagram? = null
    private var google: Google? = null
    private var twitter: Twitter? = null
    private var linkedin: Linkedin? = null


    /**
     * Google initialization method
     * googleClientId : String
     */
    fun initGoogle(googleClientId: String) {
        if (google == null) {
            google = Google(context, googleClientId)
        }
    }

    /**
     * Facebook initialization method
     */
    fun initFacebook() {
        if (facebook == null) {
            facebook = Facebook(context)
        }
    }

    /**
     * Twitter initialization method
     */
    fun initTwitter() {
        if (twitter == null) {
            twitter = Twitter(context)
        }
    }

    /**
     * Instagram initialization method
     * clientId : String
     * clientSecret : String
     * redirectUrl : String
     */
    fun initInstagram(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
    ) {
        if (instagram == null) {
            instagram = Instagram(context, clientId, clientSecret, redirectUrl)
        }
    }

    /**
     * Linkedin initialization method
     */
    fun initLinkedin() {
        if (linkedin == null) {
            linkedin = Linkedin(context)
        }
    }

    /**
     * Facebook Login wrapper method
     * callback : FacebookAuthenticationCallback
     */
    fun facebookSignIn(callback: FacebookAuthenticationCallback) {
        facebook?.signInWithFacebook(callback)
    }

    /**
     * Facebook Button Login wrapper method
     * button : LoginButton
     * callback : FacebookAuthenticationCallback
     */
    fun facebookButtonSignIn(button: LoginButton, callback: FacebookAuthenticationCallback) {
        facebook?.signInWithFacebookButton(button, callback)
    }

    /**
     * Facebook logout wrapper method
     * callback : FacebookLogOutCallback
     */
    fun facebookLogout(callback: FacebookLogOutCallback) {
        facebook?.facebookLogOut(callback)
    }

    /**
     * Facebook onActivityResult wrapper method
     * requestCode : requestCode
     * resultCode : ResultCode
     * data : Intent?
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebook?.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Google Login wrapper method
     * callback : GoogleAuthenticationCallback
     */
    fun googleLogin(callback: GoogleAuthentication) {
        google?.signIn(callback)
    }

    /**
     * Google SignOut wrapper method
     * callback : object of GoogleSignOutCallback
     */
    fun googleLogOut(callback: GoogleLogOutCallback) {
        google?.googleLogOut(callback)
    }

    /**
     * Twitter Login wrapper method
     * firebaseAuth : FirebaseAuth
     * callback : TwitterAuthentication
     */
    fun twitterLogin(firebaseAuth: FirebaseAuth, callback: TwitterAuthentication) {
        twitter?.signInWithTwitter(firebaseAuth, callback)
    }


    /**
     * Twitter Logout wrapper method
     * firebaseAuth : FirebaseAuth
     * callback : TwitterAuthentication
     */
    fun twitterLogOut(firebaseAuth: FirebaseAuth, callback: TwitterLogOutCallback) {
        twitter?.twitterLogOut(firebaseAuth, callback)
    }


    /**
     * Instagram Signin wrapper method
     * firebaseAuth : FirebaseAuth
     * callback : TwitterAuthentication
     */
    fun instagramSignIn(callback: InstagramAuthentication) {
        instagram?.instagramLogin(callback)
    }

    /**
     * Linkedin Signin wrapper method
     * clientId : String
     * clientSecret: String
     * redirectUrl: String
     * callback:LinkedinAuthentication
     */
    fun linkedinSignIn(
        clientId: String,
        clientSecret: String,
        redirectUrl: String,
        callback: LinkedinAuthentication
    ) {
        linkedin?.linkedinSignIn(clientId, clientSecret, redirectUrl, callback)
    }

}





