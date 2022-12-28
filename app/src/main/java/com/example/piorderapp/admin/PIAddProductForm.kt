package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddProductFormBinding
import com.example.piorderapp.databinding.ActivityPiaddUserFormBinding
import com.example.piorderapp.models.PIProductModel
import com.example.piorderapp.models.PIUserModel

class PIAddProductForm : AppCompatActivity() {

    private lateinit var binding: ActivityPiaddProductFormBinding
    var dbhandler: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piadd_product_form)

        supportActionBar?.hide()
        dbhandler = DBHelper(this)

        getCompanySpinnerItems()

        binding.saveDataBTN.setOnClickListener{
            var saved: Boolean = false
            var product: PIProductModel = PIProductModel()

            product.productName = binding.addProductNameET.text.toString()
            product.productPacking = binding.addProductPckET.text.toString()
            product.productPrice = binding.addProductPriceET.text.toString().toFloat()

            product.productCompanyID = dbhandler!!.getCompanyIDbyName(binding.productCompanySpinner.selectedItem.toString())

            saved = dbhandler?.addProduct(product) as Boolean

            if(saved){
                binding.addProductNameET.text.clear()
                binding.addProductPckET.text.clear()
                binding.addProductPriceET.text.clear()
                Toast.makeText(this,"Saved Successfully" , Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Error: Not Saved" , Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getCompanySpinnerItems(){
        var companyNames: List<String> = dbhandler!!.getCompanyNames()

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,companyNames)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.productCompanySpinner.adapter = adapter
    }
}