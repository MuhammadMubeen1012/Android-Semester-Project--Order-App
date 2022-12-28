package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.adapters.CompanyListAdapter
import com.example.piorderapp.adapters.CustomersListAdapter
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPishowCompaniesBinding
import com.example.piorderapp.databinding.ActivityPishowCustomersBinding
import com.example.piorderapp.models.PICompanyModel
import com.example.piorderapp.models.PICustomerModel

class PIShowCustomers : AppCompatActivity() {

    private lateinit var binding: ActivityPishowCustomersBinding
    lateinit var customersRecyclerView: RecyclerView
    var adapter: CustomersListAdapter?=null
    var dbHandler: DBHelper?=null
    var customerList: List<PICustomerModel> = ArrayList<PICustomerModel>()
    var linearLayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pishow_customers)

        supportActionBar?.hide()

        customersRecyclerView = binding.customersRV
        dbHandler = DBHelper(this)
        showCustomers()
    }

    fun showCustomers(){
        customerList = dbHandler!!.getCustomers()
        adapter = CustomersListAdapter(customerList,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        customersRecyclerView.layoutManager = linearLayoutManager
        customersRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
}