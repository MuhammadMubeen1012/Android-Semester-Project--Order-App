package com.example.piorderapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.admin.PIAdminDashboard
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiloginScreenBinding
import com.example.piorderapp.operator.PIOperatorDashboard
import com.example.piorderapp.salesman.PISalesmanDashboard

class PILoginScreen : AppCompatActivity() {

    private lateinit var binding: ActivityPiloginScreenBinding
    var dbHandler: DBHelper?=null
    private val sharedPrefFile = "loginSalesman"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pilogin_screen)
        //hiding action bar while running splash screen
        supportActionBar?.hide()

        dbHandler = DBHelper(this)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        binding.loginBTN.setOnClickListener{
            val role = dbHandler!!.getRole(binding.usernameET.text.toString(),binding.passwordET.text.toString())
            if(role == 0){
                Log.d("Admin: ","Me Admin")
                val intent: Intent = Intent(this, PIAdminDashboard::class.java)
                startActivity(intent)
                finish()
            } else if (role == 2){
                Log.d("Operator: ","Me Operator")
                val intent: Intent = Intent(this, PIOperatorDashboard::class.java)
                startActivity(intent)
                finish()
            } else if(role == 1){
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("salesman_name",binding.usernameET.text.toString())
                editor.apply()
                editor.commit()
                Log.d("Salesman: ","Me Salesman")
                val intent: Intent = Intent(this, PISalesmanDashboard::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("Login Status: ","Incorrect details")
            }
        }
    }

}