package com.example.piorderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class PISplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pisplash_screen)

        //hiding action bar while running splash screen
        supportActionBar?.hide()

        //Splash Screen handling - make it remain for 3secs and move it to Login Activity
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            //intent to pass from splash screen to main screen
            val intent: Intent = Intent(this,PILoginScreen::class.java)
            startActivity(intent)
            //removing from stack after move to the login screen
            finish()
        } , 3000)
    }
}