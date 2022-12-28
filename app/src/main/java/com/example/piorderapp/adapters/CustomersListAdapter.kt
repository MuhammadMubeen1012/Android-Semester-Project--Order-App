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
import com.example.piorderapp.admin.PIEditCompany
import com.example.piorderapp.admin.PIEditCustomer
import com.example.piorderapp.models.PICompanyModel
import com.example.piorderapp.models.PICustomerModel

class CustomersListAdapter(customerList: List<PICustomerModel>, internal var context: Context)
    : RecyclerView.Adapter<CustomersListAdapter.CustomersViewHolder>() {

    internal var customerList: List<PICustomerModel> = ArrayList()

    init {
        this.customerList = customerList
    }

    inner class CustomersViewHolder(view: View): RecyclerView.ViewHolder(view){
        var id: TextView = view.findViewById(R.id.customerIDTV)
        var customerName : TextView = view.findViewById(R.id.customerNameTV)
        var customerAddress: TextView = view.findViewById(R.id.customerAddressTV)
        var editBTN: ImageView = view.findViewById(R.id.editCustomerBTN)
        var deleteBTN: ImageView = view.findViewById(R.id.deleteCustomerBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomersViewHolder {
        //method to bind view
        val view = LayoutInflater.from(context).inflate(R.layout.customercard_layout,parent,false)
        return CustomersViewHolder(view)
        //view is bind now
    }

    override fun onBindViewHolder(holder: CustomersViewHolder, position: Int) {
        val customer =customerList[position]
        holder.id.text = customer.customerID.toString()
        holder.customerName.text =customer.customerName
        holder.customerAddress.text = customer.customerAddress

        holder.editBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditCustomer::class.java)
            intent.putExtra("ID" , customer.customerID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.deleteBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditCustomer::class.java)
            intent.putExtra("ID" , customer.customerID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return customerList.size
    }
}