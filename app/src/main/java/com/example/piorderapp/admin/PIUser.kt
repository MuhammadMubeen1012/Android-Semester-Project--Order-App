package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.databinding.ActivityPiuserBinding

class PIUser : AppCompatActivity() {
    private lateinit var binding: ActivityPiuserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piuser)

        supportActionBar?.hide()

        binding.addUserCV.setOnClickListener{
            val intent: Intent = Intent(this,PIAddUserForm::class.java)
            startActivity(intent)
        }

        binding.getUserCV.setOnClickListener{
            val intent: Intent = Intent(this,PIShowUsers::class.java)
            startActivity(intent)
        }
    }
}