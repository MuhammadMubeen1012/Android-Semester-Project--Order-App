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
import com.example.piorderapp.admin.PIEditUsers
import com.example.piorderapp.models.PIProductModel
import com.example.piorderapp.models.PIUserModel

class ProductListAdapter(productList: List<PIProductModel>, internal var context: Context)
    : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(){

    internal var productList: List<PIProductModel> = ArrayList()

    init {
        this.productList = productList
    }

    inner class ProductViewHolder(view: View): RecyclerView.ViewHolder(view){
        var id: TextView = view.findViewById(R.id.productIDTV)
        var productName : TextView = view.findViewById(R.id.productNameTV)
        var productPack : TextView = view.findViewById(R.id.productPackTV)
        var productPrice: TextView = view.findViewById(R.id.productPriceTV)
        var productCompany: TextView = view.findViewById(R.id.productCompanyTV)
        var editBTN: ImageView = view.findViewById(R.id.editProductBTN)
        var deleteBTN: ImageView = view.findViewById(R.id.deleteProductBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        //method to bind view
        val view = LayoutInflater.from(context).inflate(R.layout.productcard_layout,parent,false)
        return ProductViewHolder(view)
        //view is bind now
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int){
        val products =productList[position]
        holder.id.text = products.productID.toString()
        holder.productName.text = products.productName.toString()
        holder.productPack.text = products.productPacking.toString()
        holder.productPrice.text = products.productPrice.toString()
        holder.productCompany.text = products.productCompanyID.toString()

        holder.editBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditProduct::class.java)
            intent.putExtra("ID" , products.productID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.deleteBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditProduct::class.java)
            intent.putExtra("ID" , products.productID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}