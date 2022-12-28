package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.databinding.ActivityPicompaniesBinding
import com.example.piorderapp.databinding.ActivityPicustomersBinding

class PICustomers : AppCompatActivity() {

    private lateinit var binding: ActivityPicustomersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_picustomers)

        supportActionBar?.hide()

        binding.addCustomerCV.setOnClickListener{
            val intent: Intent = Intent(this,PIAddCustomerForm::class.java)
            startActivity(intent)
        }

        binding.getCustomersCV.setOnClickListener{
            val intent: Intent = Intent(this,PIShowCustomers::class.java)
            startActivity(intent)
        }

    }
}