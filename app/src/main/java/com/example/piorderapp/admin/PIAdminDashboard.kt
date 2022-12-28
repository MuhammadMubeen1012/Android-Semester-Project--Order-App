package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.databinding.ActivityPiadminDashboardBinding

class PIAdminDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityPiadminDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piadmin_dashboard)

        supportActionBar?.hide()

        binding.userCV.setOnClickListener{
            val intent: Intent = Intent(this,PIUser::class.java)
            startActivity(intent)
        }

        binding.groupCV.setOnClickListener{
            val intent: Intent = Intent(this,PIGroup::class.java)
            startActivity(intent)
        }

        binding.companyCV.setOnClickListener{
            val intent: Intent = Intent(this,PICompanies::class.java)
            startActivity(intent)
        }

        binding.productCV.setOnClickListener{
            val intent: Intent = Intent(this,PIProducts::class.java)
            startActivity(intent)
        }

        binding.customerCV.setOnClickListener{
            val intent: Intent = Intent(this,PICustomers::class.java)
            startActivity(intent)
        }

        binding.orderCV.setOnClickListener{
            val intent: Intent = Intent(this,PIOrders::class.java)
            startActivity(intent)
        }

    }
}