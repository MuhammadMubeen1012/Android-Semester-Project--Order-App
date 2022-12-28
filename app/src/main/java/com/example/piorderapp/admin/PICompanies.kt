package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.databinding.ActivityPicompaniesBinding
import com.example.piorderapp.databinding.ActivityPigroupBinding

class PICompanies : AppCompatActivity() {

    private lateinit var binding: ActivityPicompaniesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_picompanies)
        supportActionBar?.hide()

        binding.addCompanyCV.setOnClickListener{
            val intent: Intent = Intent(this,PIAddCompanyForm::class.java)
            startActivity(intent)
        }

        binding.getComapniesCV.setOnClickListener{
            val intent: Intent = Intent(this,PIShowCompanies::class.java)
            startActivity(intent)
        }
    }
}