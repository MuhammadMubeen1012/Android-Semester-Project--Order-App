package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddCompanyFormBinding
import com.example.piorderapp.databinding.ActivityPiaddGroupFormBinding
import com.example.piorderapp.models.PICompanyModel
import com.example.piorderapp.models.PIUserModel

class PIAddCompanyForm : AppCompatActivity() {

    private lateinit var binding: ActivityPiaddCompanyFormBinding
    var dbhandler: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piadd_company_form)

        supportActionBar?.hide()
        dbhandler = DBHelper(this)

        binding.saveCompanyDataBTN.setOnClickListener{
            var saved: Boolean = false
            var company: PICompanyModel = PICompanyModel()

            company.companyName = binding.addCompanyNameET.text.toString()

            if(binding.companyGroupSpinner.selectedItem.toString().equals("Pharma Inn")){
                company.companyGroupID = 1
            } else if(binding.companyGroupSpinner.selectedItem.toString().equals("Pharma Link")) {
                company.companyGroupID= 2
            }

            saved = dbhandler?.addCompany(company) as Boolean

            if(saved){
                binding.addCompanyNameET.text.clear()
                Toast.makeText(this,"Saved Successfully" , Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Error: Not Saved" , Toast.LENGTH_LONG).show()
            }
        }
    }
}