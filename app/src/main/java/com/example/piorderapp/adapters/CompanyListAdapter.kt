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
import com.example.piorderapp.admin.PIEditUsers
import com.example.piorderapp.models.PICompanyModel
import com.example.piorderapp.models.PIUserModel

class CompanyListAdapter(companyList: List<PICompanyModel>, internal var context: Context)
    : RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

    internal var companyList: List<PICompanyModel> = ArrayList()

    init {
        this.companyList = companyList
    }

    inner class CompanyViewHolder(view: View): RecyclerView.ViewHolder(view){
        var id: TextView = view.findViewById(R.id.companyidTV)
        var companyname : TextView = view.findViewById(R.id.companynameTV)
        var companyGroup: TextView = view.findViewById(R.id.companyGroupIDTV)
        var editBTN: ImageView = view.findViewById(R.id.editCompanyBTN)
        var deleteBTN: ImageView = view.findViewById(R.id.deleteCompanyBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        //method to bind view
        val view = LayoutInflater.from(context).inflate(R.layout.companycard_layout,parent,false)
        return CompanyViewHolder(view)
        //view is bind now
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company =companyList[position]
        holder.id.text = company.ID.toString()
        holder.companyname.text =company.companyName
        holder.companyGroup.text =  "" + company.companyGroupID

        holder.editBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditCompany::class.java)
            intent.putExtra("ID" , company.ID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.deleteBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditCompany::class.java)
            intent.putExtra("ID" , company.ID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return companyList.size
    }
}