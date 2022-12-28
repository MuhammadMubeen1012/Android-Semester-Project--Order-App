package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.databinding.ActivityPiproductsBinding
import com.example.piorderapp.databinding.ActivityPiuserBinding

class PIProducts : AppCompatActivity() {
    private lateinit var binding: ActivityPiproductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piproducts)

        supportActionBar?.hide()

        binding.addProductCV.setOnClickListener{
            val intent: Intent = Intent(this,PIAddProductForm::class.java)
            startActivity(intent)
        }

        binding.getProductsCV.setOnClickListener{
            val intent: Intent = Intent(this,PIShowProducts::class.java)
            startActivity(intent)
        }
    }
}