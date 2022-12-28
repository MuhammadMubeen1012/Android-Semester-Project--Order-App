package com.example.piorderapp.salesman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.adapters.OrderProductsListAdapter
import com.example.piorderapp.adapters.ProductListAdapter
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddOrderFormBinding
import com.example.piorderapp.databinding.ActivityPiorderedCheckoutBinding
import com.example.piorderapp.models.PIOrderOnelineModel
import com.example.piorderapp.models.PIProductModel

class PIOrderedCheckout : AppCompatActivity() {

    private lateinit var binding: ActivityPiorderedCheckoutBinding
    var dbhandler: DBHelper? = null
    lateinit var orderProductsRecyclerView: RecyclerView
    var adapter: OrderProductsListAdapter?=null
    var orderProductList: List<PIOrderOnelineModel> = ArrayList<PIOrderOnelineModel>()
    var linearLayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piordered_checkout)
        supportActionBar?.hide()

        dbhandler = DBHelper(this)
        orderProductsRecyclerView = binding.orderProductsRV
        val order = dbhandler?.getOrderByID(intent.getIntExtra("ID",0).toInt())

        showOrderProducts(order!!.orderID)

        if (order != null) {
            binding.orderID.setText(order.orderID.toString())
            binding.orderDate.setText(order.orderDate)
            binding.customerName.setText(order.customerName)
            binding.salesmanName.setText(order.salesMan)
            binding.orderPrice.setText(order.totalPrice.toString())
        }
    }

    fun showOrderProducts(id: Int){
        orderProductList = dbhandler!!.getOrderProductsByID(id)
        adapter = OrderProductsListAdapter(orderProductList,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        orderProductsRecyclerView.layoutManager = linearLayoutManager
        orderProductsRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
}