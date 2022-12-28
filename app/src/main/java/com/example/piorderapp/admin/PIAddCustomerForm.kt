package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddCustomerFormBinding
import com.example.piorderapp.databinding.ActivityPiaddGroupFormBinding
import com.example.piorderapp.models.PICustomerModel
import com.example.piorderapp.models.PIGroupModel

class PIAddCustomerForm : AppCompatActivity() {

    private lateinit var binding: ActivityPiaddCustomerFormBinding
    var dbhandler: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piadd_customer_form)

        supportActionBar?.hide()
        dbhandler = DBHelper(this)

        binding.saveCustomerDataBTN.setOnClickListener{
            var saved: Boolean = false
            var customer: PICustomerModel = PICustomerModel()

            customer.customerName = binding.customerNameET.text.toString()
            customer.customerAddress = binding.customerAddressET.text.toString()


            saved = dbhandler?.addCustomer(customer) as Boolean

            if(saved){
                binding.customerNameET.text.clear()
                binding.customerAddressET.text.clear()

                Toast.makeText(this,"Saved Successfully" , Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Error: Not Saved" , Toast.LENGTH_LONG).show()
            }
        }

    }
}