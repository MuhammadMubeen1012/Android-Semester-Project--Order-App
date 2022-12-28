package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.piorderapp.R
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPiaddGroupFormBinding
import com.example.piorderapp.models.PIGroupModel
import com.example.piorderapp.models.PIUserModel

class PIAddGroupForm : AppCompatActivity() {

    private lateinit var binding: ActivityPiaddGroupFormBinding
    var dbhandler: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piadd_group_form)

        supportActionBar?.hide()
        dbhandler = DBHelper(this)

        binding.saveDataBTN.setOnClickListener{
            var saved: Boolean = false
            var group: PIGroupModel = PIGroupModel()

            group.groupName = binding.groupNameET.text.toString()

            saved = dbhandler?.addGroup(group) as Boolean

            if(saved){
                binding.groupNameET.text.clear()
                Toast.makeText(this,"Saved Successfully" , Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"Error: Not Saved" , Toast.LENGTH_LONG).show()
            }
        }

    }
}