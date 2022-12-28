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
import com.example.piorderapp.databinding.ActivityPieditUsersBinding
import com.example.piorderapp.models.PIUserModel

class PIEditUsers : AppCompatActivity() {

    lateinit var binding: ActivityPieditUsersBinding

    lateinit var editTaskBTN : Button
    lateinit var deleteTaskBTN : Button
    lateinit var nameET: EditText
    lateinit var passwordET: EditText
    var dbHelper: DBHelper?= null
    lateinit var user: PIUserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piedit_users)

        supportActionBar?.hide()

        editTaskBTN = findViewById(R.id.editDataBTN)
        deleteTaskBTN = findViewById(R.id.deleteDataBTN)
        nameET = findViewById(R.id.editUserNameET)
        passwordET = findViewById(R.id.editPasswordET)

        dbHelper = DBHelper(this)

        if(intent!=null){
            user = dbHelper!!.getUserByID(intent.getIntExtra("ID",0))

            nameET.setText(user.username)
            passwordET.setText(user.password)
        }

        binding.editDataBTN.setOnClickListener{
            var success: Boolean = false
            val user: PIUserModel= PIUserModel()

            user.ID = intent.getIntExtra("ID",0)
            user.username = nameET.text.toString()
            user.password = passwordET.text.toString()

            success = dbHelper?.updateUser(user) as Boolean

            if(success){
                val intent: Intent = Intent(this,PIShowUsers::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.deleteDataBTN.setOnClickListener{
            var success: Boolean = false
            val user: PIUserModel= PIUserModel()

            user.ID = intent.getIntExtra("ID",0)
            success = dbHelper?.deleteUser(user.ID) as Boolean

            if(success){
                nameET.text.clear()
                passwordET.text.clear()
                Toast.makeText(applicationContext,"Deleted Successfully",Toast.LENGTH_LONG).show()

                val intent: Intent = Intent(this,PIShowUsers::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}