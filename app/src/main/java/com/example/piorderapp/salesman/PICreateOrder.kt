package com.example.piorderapp.salesman

//import android.R

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPicreateOrderBinding
import java.text.SimpleDateFormat
import java.util.*


class PICreateOrder : AppCompatActivity() {

    private lateinit var binding: ActivityPicreateOrderBinding
    var dbhandler: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_picreate_order)

        supportActionBar?.hide()
        dbhandler = DBHelper(this)
        customerAutoComplete()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("loginSalesman",
            Context.MODE_PRIVATE)

        binding.orderBySalesmanET.setText(sharedPreferences.getString("salesman_name","Unknown"))
        binding.orderBySalesmanET.isEnabled = false

        val currentDate = SimpleDateFormat("dd/MM/yyyy")
        val todayDate = Date()
        val thisDate = currentDate.format(todayDate)

        binding.orderDateET.setText(thisDate)
        binding.orderDateET.isEnabled = false

        binding.createOrderBTN.setOnClickListener{
            val intent: Intent = Intent(this,PIAddOrderForm::class.java)

            intent.putExtra("customerName" , binding.orderFromCustomerAC.text.toString())
            intent.putExtra("salesmanName" , binding.orderBySalesmanET.text.toString())
            intent.putExtra("orderDate" , binding.orderDateET.text.toString())

            startActivity(intent)
        }

    }


    fun customerAutoComplete(){
        //customer auto complete
        //get Customers from DB
        val customers: List<String> = dbhandler!!.getCustomerNames()
        //Creating the instance of ArrayAdapter containing list of fruit names
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, customers)
        //Getting the instance of AutoCompleteTextView
        val customersACV = binding.orderFromCustomerAC as AutoCompleteTextView
        customersACV.threshold = 1
        //will start working from first character
        customersACV.setAdapter(adapter)
        //setting the adapter data into the AutoCompleteTextView
        binding.createOrderBTN.requestFocus()
    }
}