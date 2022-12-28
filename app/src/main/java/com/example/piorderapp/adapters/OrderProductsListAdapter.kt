package com.example.piorderapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.admin.PIEditProduct
import com.example.piorderapp.models.PIOrderOnelineModel
import com.example.piorderapp.models.PIProductModel

class OrderProductsListAdapter(orderProductList: List<PIOrderOnelineModel>, internal var context: Context)
    : RecyclerView.Adapter<OrderProductsListAdapter.OrderProductsViewHolder>(){

    internal var orderProductList: List<PIOrderOnelineModel> = ArrayList()

    init {
        this.orderProductList = orderProductList
    }

    inner class OrderProductsViewHolder(view: View): RecyclerView.ViewHolder(view){
        var quantity: TextView = view.findViewById(R.id.onelineProductQuantityTV)
        var productName : TextView = view.findViewById(R.id.onelineProductNameTV)
        var productUnitPrice : TextView = view.findViewById(R.id.onelineProductPriceTV)
        var productTotalPrice: TextView = view.findViewById(R.id.onelineProductTPTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductsViewHolder {
        //method to bind view
        val view = LayoutInflater.from(context).inflate(R.layout.onelineordercard_layout,parent,false)
        return OrderProductsViewHolder(view)
        //view is bind now
    }

    override fun onBindViewHolder(holder: OrderProductsViewHolder, position: Int){
        val oneline =orderProductList[position]
        holder.quantity.text = oneline.oo_quantity.toString() + "x"
        holder.productName.text = oneline.oo_product.toString()
        holder.productUnitPrice.text = oneline.oo_perUnitPrice.toString()
        holder.productTotalPrice.text = oneline.oo_totalPrice.toString()
    }

    override fun getItemCount(): Int {
        return orderProductList.size
    }
}