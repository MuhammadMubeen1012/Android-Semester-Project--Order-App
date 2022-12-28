package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.databinding.ActivityPigroupBinding
import com.example.piorderapp.databinding.ActivityPiuserBinding

class PIGroup : AppCompatActivity() {
    private lateinit var binding: ActivityPigroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pigroup)
        supportActionBar?.hide()

        binding.addGroupCV.setOnClickListener{
            val intent: Intent = Intent(this,PIAddGroupForm::class.java)
            startActivity(intent)
        }

        binding.getGroupsCV.setOnClickListener{
            val intent: Intent = Intent(this,PIShowGroups::class.java)
            startActivity(intent)
        }
    }
}