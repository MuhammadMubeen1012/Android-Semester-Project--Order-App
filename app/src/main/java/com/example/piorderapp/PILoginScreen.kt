package com.example.piorderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PILoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilogin_screen)

        //hiding action bar while running splash screen
        supportActionBar?.hide()

    }
}