package com.example.piorderapp.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPieditProductBinding
import com.example.piorderapp.databinding.ActivityPieditUsersBinding
import com.example.piorderapp.models.PIProductModel
import com.example.piorderapp.models.PIUserModel

class PIEditProduct : AppCompatActivity() {

    lateinit var binding: ActivityPieditProductBinding

    var dbHelper: DBHelper?= null
    lateinit var product: PIProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piedit_product)
        supportActionBar?.hide()

        dbHelper = DBHelper(this)

        if(intent!=null){
            product = dbHelper!!.getProductByID(intent.getIntExtra("ID",0))
            binding.editProductNameET.setText(product.productName)
            binding.editProductPackET.setText(product.productPacking)
            binding.editProductPriceET.setText(product.productPrice.toString())
        }

        binding.editDataBTN.setOnClickListener{
            var success: Boolean = false
            val product: PIProductModel= PIProductModel()

            product.productID = intent.getIntExtra("ID",0)
            product.productName = binding.editProductNameET.text.toString()
            product.productPacking = binding.editProductPackET.text.toString()
            product.productPrice = binding.editProductPriceET.text.toString().toFloat()

            success = dbHelper?.updateProduct(product) as Boolean

            if(success){
                val intent: Intent = Intent(this,PIShowProducts::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.deleteDataBTN.setOnClickListener{
            var success: Boolean = false
            val product: PIProductModel= PIProductModel()

            product.productID = intent.getIntExtra("ID",0)
            success = dbHelper?.deleteProduct(product.productID) as Boolean

            if(success){
                binding.editProductNameET.text.clear()
                binding.editProductPackET.text.clear()
                binding.editProductPriceET.text.clear()

                Toast.makeText(applicationContext,"Deleted Successfully", Toast.LENGTH_LONG).show()

                val intent: Intent = Intent(this,PIProducts::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}