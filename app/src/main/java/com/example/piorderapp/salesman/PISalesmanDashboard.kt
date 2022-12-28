package com.example.piorderapp.salesman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R

class PISalesmanDashboard : AppCompatActivity() {

    private lateinit var binding: com.example.piorderapp.databinding.ActivityPisalesmanDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pisalesman_dashboard)
        supportActionBar?.hide()

        binding.placeOrderBTN.setOnClickListener{
            val intent: Intent = Intent(this,PICreateOrder::class.java)
            startActivity(intent)
        }
    }
}