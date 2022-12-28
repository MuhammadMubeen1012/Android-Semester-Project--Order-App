package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.adapters.CompanyListAdapter
import com.example.piorderapp.adapters.UserListAdapter
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPishowCompaniesBinding
import com.example.piorderapp.databinding.ActivityPishowUsersBinding
import com.example.piorderapp.models.PICompanyModel
import com.example.piorderapp.models.PIUserModel

class PIShowCompanies : AppCompatActivity() {

    private lateinit var binding: ActivityPishowCompaniesBinding
    lateinit var companiesRecyclerView: RecyclerView
    var adapter: CompanyListAdapter?=null
    var dbHandler: DBHelper?=null
    var companyList: List<PICompanyModel> = ArrayList<PICompanyModel>()
    var linearLayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pishow_companies)
        supportActionBar?.hide()

        companiesRecyclerView = binding.companiesRV
        dbHandler = DBHelper(this)
        showCompanies()
    }

    fun showCompanies(){
        companyList = dbHandler!!.getCompanies()
        adapter = CompanyListAdapter(companyList,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        companiesRecyclerView.layoutManager = linearLayoutManager
        companiesRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
}