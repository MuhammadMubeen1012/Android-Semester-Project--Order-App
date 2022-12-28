package com.example.piorderapp.operator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.piorderapp.R

class PIOperatorDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pioperator_dashboard)

        supportActionBar?.hide()
    }
}