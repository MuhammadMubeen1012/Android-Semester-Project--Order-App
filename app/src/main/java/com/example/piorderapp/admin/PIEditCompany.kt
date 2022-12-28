package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPieditCompanyBinding
import com.example.piorderapp.databinding.ActivityPieditUsersBinding
import com.example.piorderapp.models.PICompanyModel
import com.example.piorderapp.models.PIUserModel

class PIEditCompany : AppCompatActivity() {

    lateinit var binding: ActivityPieditCompanyBinding

    lateinit var editCompanyBTN : Button
    lateinit var deleteCompanyBTN : Button
    lateinit var companyNameET: EditText
    var dbHelper: DBHelper?= null
    lateinit var company: PICompanyModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piedit_company)

        editCompanyBTN = findViewById(R.id.editCompanyDataBTN)
        deleteCompanyBTN = findViewById(R.id.deleteCompanyDataBTN)
        companyNameET = findViewById(R.id.editCompanyNameET)

        dbHelper = DBHelper(this)

        if(intent!=null){
            company = dbHelper!!.getCompanyByID(intent.getIntExtra("ID",0))

            companyNameET.setText(company.companyName)
        }

        binding.editCompanyDataBTN.setOnClickListener{
            var success: Boolean = false
            val company: PICompanyModel= PICompanyModel()

            company.ID = intent.getIntExtra("ID",0)
            company.companyName = companyNameET.text.toString()

            success = dbHelper?.updateCompany(company) as Boolean

            if(success){
                val intent: Intent = Intent(this,PIShowCompanies::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.deleteCompanyDataBTN.setOnClickListener{
            var success: Boolean = false
            val company: PICompanyModel= PICompanyModel()

            company.ID = intent.getIntExtra("ID",0)
            success = dbHelper?.deleteCompany(company.ID) as Boolean

            if(success){
                companyNameET.text.clear()
                Toast.makeText(applicationContext,"Deleted Successfully", Toast.LENGTH_LONG).show()

                val intent: Intent = Intent(this,PICompanies::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}