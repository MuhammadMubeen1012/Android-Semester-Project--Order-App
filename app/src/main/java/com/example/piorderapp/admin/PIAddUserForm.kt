package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddUserFormBinding
import com.example.piorderapp.models.PIUserModel

class PIAddUserForm : AppCompatActivity() {
    private lateinit var binding: ActivityPiaddUserFormBinding
    var dbhandler: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piadd_user_form)

        supportActionBar?.hide()
        dbhandler = DBHelper(this)

        binding.saveDataBTN.setOnClickListener{
            var saved: Boolean = false
            var user: PIUserModel = PIUserModel()

            user.username = binding.addUserNameET.text.toString()
            user.password= binding.addPasswordET.text.toString()

            if(binding.userRolesSpinner.selectedItem.toString().equals("Salesman")){
                user.userRoleID = 1
            } else if(binding.userRolesSpinner.selectedItem.toString().equals("Operator")) {
                user.userRoleID = 2
            }

            saved = dbhandler?.addUser(user) as Boolean

            if(saved){
                binding.addUserNameET.text.clear()
                binding.addPasswordET.text.clear()
                Toast.makeText(this,"Saved Successfully" , Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Error: Not Saved" , Toast.LENGTH_LONG).show()
            }
        }
    }
}