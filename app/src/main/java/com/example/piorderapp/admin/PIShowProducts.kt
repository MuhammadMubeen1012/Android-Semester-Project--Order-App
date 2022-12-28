package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.adapters.ProductListAdapter
import com.example.piorderapp.adapters.UserListAdapter
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPishowProductsBinding
import com.example.piorderapp.databinding.ActivityPishowUsersBinding
import com.example.piorderapp.models.PIProductModel
import com.example.piorderapp.models.PIUserModel

class PIShowProducts : AppCompatActivity() {

    private lateinit var binding: ActivityPishowProductsBinding

    lateinit var productsRecyclerView: RecyclerView
    var adapter: ProductListAdapter?=null
    var dbHandler: DBHelper?=null
    var productList: List<PIProductModel> = ArrayList<PIProductModel>()
    var linearLayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pishow_products)
        supportActionBar?.hide()
        dbHandler = DBHelper(this)
        productsRecyclerView = binding.productsRV
        showProducts()
    }

    fun showProducts(){
        productList = dbHandler!!.getProducts()
        adapter = ProductListAdapter(productList,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        productsRecyclerView.layoutManager = linearLayoutManager
        productsRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
}