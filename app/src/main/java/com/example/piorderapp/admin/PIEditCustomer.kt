package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPieditCustomerBinding
import com.example.piorderapp.models.PICustomerModel

class PIEditCustomer : AppCompatActivity() {

    lateinit var binding: ActivityPieditCustomerBinding

    var dbHelper: DBHelper?= null
    lateinit var customer: PICustomerModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piedit_customer)

        supportActionBar?.hide()

        dbHelper = DBHelper(this)

        if(intent!=null){
            customer = dbHelper!!.getCustomerByID(intent.getIntExtra("ID",0))
            binding.editCustomerNameET.setText(customer.customerName)
            binding.editCustomerAddressET.setText(customer.customerAddress)
        }

        binding.editCustomerDataBTN.setOnClickListener{
            var success: Boolean = false
            val customer: PICustomerModel= PICustomerModel()

            customer.customerID = intent.getIntExtra("ID",0)
            customer.customerName = binding.editCustomerNameET.text.toString()
            customer.customerAddress = binding.editCustomerAddressET.text.toString()

            success = dbHelper?.updateCustomer(customer) as Boolean

            if(success){
                val intent: Intent = Intent(this,PIShowCustomers::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.deleteCustomerDataBTN.setOnClickListener{
            var success: Boolean = false
            val customer: PICustomerModel= PICustomerModel()

            customer.customerID = intent.getIntExtra("ID",0)
            success = dbHelper?.deleteCustomer(customer.customerID) as Boolean

            if(success){
                binding.editCustomerNameET.text.clear()
                binding.editCustomerAddressET.text.clear()

                Toast.makeText(applicationContext,"Deleted Successfully", Toast.LENGTH_LONG).show()

                val intent: Intent = Intent(this,PICustomers::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}