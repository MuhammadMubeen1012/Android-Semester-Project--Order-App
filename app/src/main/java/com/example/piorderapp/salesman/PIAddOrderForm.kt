package com.example.piorderapp.salesman


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddOrderFormBinding
import com.example.piorderapp.models.PIOrderModel
import com.example.piorderapp.models.PIOrderOnelineModel


class PIAddOrderForm : AppCompatActivity() {

    private lateinit var binding: ActivityPiaddOrderFormBinding
    private var orderID = 1
    var dbhandler: DBHelper? = null
    lateinit var oneLine: PIOrderOnelineModel
    lateinit var order: PIOrderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_piadd_order_form)
        supportActionBar?.hide()
        var orderOnelineID = 1
        var oneLineList = ArrayList<PIOrderOnelineModel>()
        dbhandler = DBHelper(this)
        productsAutoComplete()

        //binding.orderID.text = orderID.toString()
        binding.customerName.text = "${intent.getStringExtra("customerName")}"
        binding.salesmanName.text = "${intent.getStringExtra("salesmanName")}"
        binding.orderDate.text = "${intent.getStringExtra("orderDate")}"

        binding.addToOrderBTN.setOnClickListener{
            if(binding.onelineProductAC.text.toString()!="" && binding.onelineProductQtyET.text.toString()!=""){
                val productProperties: List<String> = dbhandler!!.getProductDetails(binding.onelineProductAC.text.toString())
                oneLine = PIOrderOnelineModel()
//                oneLine.oo_ID = orderOnelineID++
                oneLine.oo_product = productProperties[0]
                oneLine.oo_product_pck = productProperties[1]
                oneLine.oo_perUnitPrice = productProperties[2].toFloat()
                oneLine.oo_quantity = binding.onelineProductQtyET.text.toString().toInt()
                oneLine.oo_totalPrice = oneLine.oo_perUnitPrice * oneLine.oo_quantity
                oneLine.oo_orderID = orderID
                val saved = dbhandler!!.addOrderOneline(oneLine)

                if(saved){
                    Toast.makeText(applicationContext,"Success: Add to order", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,"Error: Check data entry", Toast.LENGTH_LONG).show()
                }
                oneLineList.add(oneLine)

                binding.onelineProductAC.text.clear()
                binding.onelineProductQtyET.text.clear()
            } else {
                Toast.makeText(this,"Select Product or Enter QTY",Toast.LENGTH_LONG).show()
            }
        }

        binding.moveToOrderBTN.setOnClickListener{
            order = PIOrderModel()
            //order.orderID = orderID++
            order.customerName = intent.getStringExtra("customerName").toString()
            order.salesMan = intent.getStringExtra("salesmanName").toString()
            order.orderDate = intent.getStringExtra("orderDate").toString()
            //order.oneLineItems = oneLineList

            for (price in 0..(oneLineList.size-1)){
                order.totalPrice += oneLineList[price].oo_totalPrice
            }

            val saved = dbhandler!!.addOrder(order)

            if (saved){
                val intent: Intent = Intent(this,PIOrderedCheckout::class.java)
                intent.putExtra("ID" , orderID++)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun productsAutoComplete(){
        //customer auto complete
        //get Customers from DB
        val products: List<String> = dbhandler!!.getProductsNames()
        //Creating the instance of ArrayAdapter containing list of fruit names
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, products)
        //Getting the instance of AutoCompleteTextView
        val productsACV = binding.onelineProductAC as AutoCompleteTextView
        productsACV.threshold = 1
        //will start working from first character
        productsACV.setAdapter(adapter)
        //setting the adapter data into the AutoCompleteTextView
        binding.onelineProductQtyET.requestFocus()
    }
}