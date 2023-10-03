# Social Authentication

Welcome to Social Authentication Library

![image](/media/image.png)

## Features

* Social Authentication library is simplifies social authentication implementation for Facebook,
  Google, Instagram Twitter and Linkedin authentication.
* Integrate social authentication seamlessly into your Android app
* To assist you in integrating social authentication into your Android app, the library offers detailed step-by-step guidance for each supported platform. Follow these steps to get started with each social authentication method.

## Getting started

To build and run the project, follow these steps:

1. Clone the repository
2. Open the project in android studio
3. Open app -> manifest -> AndroidManifest.xml file and add a uses-permission element.
    ```
    <uses-permission android:name="android.permission.INTERNET"/>
    ```
4. Google
   * Create a Firebase Project:
      * Go to the Firebase Console (https://console.firebase.google.com/).
      * Click "Add project" to create a new Firebase project.
   * Configure Your Android App:
      * In the Firebase project dashboard, click "Add app" and select the Android platform.
      * Register your app by providing the package name (com.example.myapp), app nickname, and app signing certificate SHA-1.
   * Enable Google Sign-In Method:
      * In the Firebase Console, navigate to "Authentication" and select "Sign-in method." Find "Google" and enable Google authentication and download the google-services.json configuration file.
   * Add the Firebase Configuration File:
      * Open your android studio project in Project view and place the google-services.json file in the app module of your Android project.
   * Open your app/src/main/res/values/strings.xml file. Add client_id in given string element.
     you will get your client id inside google-service.json file
    ```
    <string name="google_client_id">google_client_id</string>
    ```
   * Now initialize library and Google login by below line of code
    ```
    myAuthentication = MyAuthentication(context) // pass the context
    myAuthentication.initGoogle(getString(R.string.google_client_id))
    ```
   * Here code is given for implement Google sign in
    ```
    googleLoginButton.setOnClickListener {
        myAuthentication.googleLogin(
            object : GoogleAuthentication {
                override fun onResultSuccess(googleSignInAccount: GoogleSignInAccount) {
                           
                }
    
                override fun onResultError(e: ApiException) {
                           
                }
    
                override fun onCancel() {
                            
                }
    
            }
        )
    }
    ```
5. Facebook
   * Create a Facebook App:
      * Go to the Facebook developer page (https://developers.facebook.com/).
      * Click "My Apps" and then click on "Create App". Now select "other" and click next button then select app tpye "none" and click next and create app.
      * Click on Facebook Login and select Android.
      * Click next until "Tell Us about Your Android Project" and fill the detain in this.
      * Generate and place key-hash.
      * Click next until "Add the Facebook Login Button", if you want to use facebook button, copy and paste given xml code into your xml file. Otherwise you can use regular button.
      * Finish and go to dashboard.
   * Get App id and App secret
      *  Click on App Settings -> Basics
      * Here you will get App id and App secrete. Note down the App ID and App Secret.
   * Go to android studio
      * Open your /app/res/values/strings.xml file.
        Add string elements with the names facebook_app_id, fb_login_protocol_scheme and facebook_client_token, and set the values to your App ID and Client Token.
     ```
     <string name="facebook_app_id">app_id</string>
     <string name="fb_login_protocol_scheme">login_protocol_scheme</string>
     <string name="facebook_client_token">facebook_client_token</string>
     ```
   * Open app -> manifest -> AndroidManifest.xml file and add given lines.
     ```
     <application....
             
          <meta-data
             android:name="com.facebook.sdk.ApplicationId"
             android:value="@string/facebook_app_id" />
         <meta-data
             android:name="com.facebook.sdk.ClientToken"
             android:value="@string/facebook_client_token" />
         <activity
             android:name="com.facebook.FacebookActivity"
             android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
             android:label="@string/app_name" />
         <activity
             android:name="com.facebook.CustomTabActivity"
             android:exported="true">
             <intent-filter>
             <action android:name="android.intent.action.VIEW" />
             <category android:name="android.intent.category.DEFAULT" />
             <category android:name="android.intent.category.BROWSABLE" />
             <data android:scheme="@string/fb_login_protocol_scheme" />
             </intent-filter>
         </activity>    
     </application>
     ```
   * Now initialize library and Facebook login by below line of code
     ```
     myAuthentication = MyAuthentication(context) // pass the context
     myAuthentication.initFacebook()
     ```
   * Using Facebook Login Button
   * If you are Facebook Login button, use below code to sign in
     ```
     facebookButton.setOnClickListener {
         myAuthentication.facebookButtonSignIn(
             this.facebookButton,
             object : FacebookAuthenticationCallback {
                 override fun onSuccess(result: LoginResult?) {
                 
                 }
                 override fun onCancel() {
             
                 }
                 override fun onError(error: FacebookException?) {
                                     
                 }
             
             }
         )
     }
     
     ```
   * Using custom button
   * If you are using custom button, use below code to sign in
     ```
     customFacebookButton.setOnClickListener {
         myAuthentication.facebookSignIn(object : FacebookAuthenticationCallback {
             override fun onSuccess(result: LoginResult?) {
                           
             }
         
             override fun onCancel() {
                            
             }
         
             override fun onError(error: FacebookException?) {
                             
             }
         
         }
         )
     }
     ```

   * Implement below lines of code inside your activity and below onCreate() method.
      ```
      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
          myAuthentication.onActivityResult(requestCode, resultCode, data)
          super.onActivityResult(requestCode, resultCode, data)
      }
      ```
6. Instagram
   * Go to the Facebook developer page (https://developers.facebook.com/).
      * Click "My Apps" and then click on "Create App". Now select "other" and click next button then select app tpye "none" and click next and create app.
      * Click on "Instagram Basic Display" and then Click on "Create New App"
   * Get App Id and App secret.
      * You will get instagram App ID and Instagram App Secret inside Basic Dispaly sectino. Note down the App ID and App Secret.
      * Fill the fields Valid OAuth Redirect URIs, Deauthorize callback URL and Data Deletion Request URL. Note down redirect_URLs.
      * Click on "Add or Remove instagram tester" and you will get Instagram Tester section, click on "Add instagram testers" and add instagram account.
   * Verify instagram account as a tester.
      * Login into your Instagram Account from the web pannel and go to the Profile section.
      * In profile section go to setting and then go to Apps and Website option. Here go to Tester invitation tab and click on Accept button.
      * Come back on Basic Display and click on save changes.
   * Go to Android Studio
      * Open your /app/res/values/strings.xml file.
        Add string elements with the names instagram_client_id, instagram_client_secret and instagram_redirect_url and set the values to your App ID and App secret and Redirect URIs.
       ```
       <string name="instagram_client_id">instagram_client_id</string>
       <string name="instagram_client_secret">instagram_client_secret</string>
       <string name="instagram_redirect_url">instagram_redirect_url</string>
       ```
      * Initialize library and Instagram
       ```
       myAuthentication = MyAuthentication(this)
       myAuthentication.initInstagram(
           getString(R.string.instagram_client_id),
           getString(R.string.instagram_client_secret),
           getString(R.string.instagram_redirect_url),
       )
       ```
      * Use Instagram sign in
       ```
       instagramLoginButton.setOnClickListener {
           myAuthentication.instagramSignIn(
               object : InstagramAuthentication {
                   override fun onSuccess(accessToken: String?, userId: Long?) {
                       
                   }
                   override fun onError(message: String) {
                      
                   }
                   override fun onException(e: IOException) {
                      
                   }
               }
           )
       }
       ```
7. Twitter
   * Create developer account.
      * Go to the Twitter developer page (https://developer.twitter.com/) and create a developer account.
   * Get App Id and App secret access token and access token secret.
      * After account creation you will get Dashboard screen. now click on Projects & Apps -> Overview.
      * Click on Setting icon in PROJECT APP and you will get APP ID. Note down this APP ID. Then click on "Keys and token" tab.
      * Click "Generate" under consumer keys. Here you will get API Key and API Secret. Note down both of them.
      * Now generate bearer token and access token and access token secret. Note down all of them.
   * Enable Twitter Sign-In Method:
      * In the Firebase Console, navigate to "Authentication" and select "Sign-in method" Find "Twitter" and enable Twitter authentication by passing API key and API Secret. Here you will get callback URL which you have to note down.
   * Complete twitter developer setup.
      * Go to your twitter app and click on setup button under User authentication settings.
      * Select "Read and write and Direct message" option in app permissions. Select "Native app" in types of app.
      * Now in App info you need to pass callback url which you got from firebase. in website url you can pass "https://console.firebase.google.com/".
      * By clicking on save button you will get Client id and Client Secret. Note down both of them.
      * Keep safe and private all the IDs and Tokens.
   * Go to Android studio
      * Initialize library and Twitter
       ```
       myAuthentication = MyAuthentication(context) // pass the context
       myAuthentication.initTwitter()
       ```
      * Initalize FirebaseAuth
       ```
       firebaseAuth = FirebaseAuth.getInstance()
       ```
      * Use Twitter sign in
       ```
       twitterLoginButton.setOnClickListener {
           myAuthentication.twitterLogin(firebaseAuth, object : TwitterAuthentication {
               override fun addOnSuccessListener(authResult: AuthResult) {
       
               }
               override fun addOnFailureListener(exception: Exception) {
                  
               }
           })
       }
       ```
8. Linkedin
   * Create Linkedin developer account.
      * Go to the Twitter developer page (https://developer.linkedin.com/) and create a developer account and click on "MyApps".
      * Inside developer page homescreen click on "Create app" button.
      * Enter App name and you need to enter your linkedin company page. If you don't have companay page, click on this link (https://business.linkedin.com/marketing-solutions/linkedin-pages) and fill required information. It is required to have some connections in this page. at the end click on "Create app" button.
      * After this you will get Products tab. Click "request access" in Sign In with LinkedIn using OpenID Connect.
      * Come to Auth tab and get Client ID and Client Secret. Note down both of them.
   * Go to Android studio
      * Open your /app/res/values/strings.xml file.
        Add string elements with the names linkedin_client_id and linkedin_client_secret and set the values of your App ID and App secret.
       ```
       <string name="linkedin_client_id">linkedin client id</string>
       <string name="linkedin_client_secret">linkedin client secret</string>
       <string name="linkedin_redirect_url">https://www.linkedin.com/developers/tools/oauth/redirect</string>
       ```
      * Initialize library and Linkedin
       ```
       myAuthentication = MyAuthentication(context)
       myAuthentication.initLinkedin()
       ```
      * Use Linkedin sign in
       ```
        linkedinLoginButton.setOnClickListener {
           myAuthentication.linkedinSignIn(
               getString(R.string.linkedin_client_id),
               getString(R.string.linkedin_client_secret),
               getString(R.string.linkedin_redirect_url),
               object : LinkedinAuthentication {
                   override fun onSuccess(accessToken: String) {
                    
                   }
                   override fun onError() {
                  
                   }
                   override fun onException(e: Exception) {
                   
                   }
               }
           )
       }
       ```
9. Build and run the app on your device or emulator

