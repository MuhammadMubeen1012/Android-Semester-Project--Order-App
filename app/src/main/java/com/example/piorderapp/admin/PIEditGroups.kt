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
import com.example.piorderapp.databinding.ActivityPieditGroupsBinding
import com.example.piorderapp.databinding.ActivityPieditUsersBinding
import com.example.piorderapp.models.PIGroupModel
import com.example.piorderapp.models.PIUserModel

class PIEditGroups : AppCompatActivity() {

    lateinit var binding: ActivityPieditGroupsBinding
    lateinit var editGroupBTN : Button
    lateinit var deleteGroupBTN : Button
    lateinit var groupnameET: EditText
    var dbHelper: DBHelper?= null
    lateinit var group: PIGroupModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_piedit_groups)

        editGroupBTN = findViewById(R.id.editGroupDataBTN)
        deleteGroupBTN = findViewById(R.id.deleteGroupDataBTN)
        groupnameET = findViewById(R.id.editGroupNameET)

        dbHelper = DBHelper(this)

        if(intent!=null){
            group = dbHelper!!.getGroupByID(intent.getIntExtra("ID",0))

            groupnameET.setText(group.groupName)
        }

        binding.editGroupDataBTN.setOnClickListener{
            var success: Boolean = false
            val group: PIGroupModel= PIGroupModel()

            group.ID = intent.getIntExtra("ID",0)
            group.groupName = groupnameET.text.toString()

            success = dbHelper?.updateGroup(group) as Boolean

            if(success){
                val intent: Intent = Intent(this,PIShowGroups::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.deleteGroupDataBTN.setOnClickListener{
            var success: Boolean = false
            val group: PIGroupModel= PIGroupModel()

            group.ID = intent.getIntExtra("ID",0)
            success = dbHelper?.deleteGroup(group.ID) as Boolean

            if(success){
                groupnameET.text.clear()
                Toast.makeText(applicationContext,"Deleted Successfully", Toast.LENGTH_LONG).show()

                val intent: Intent = Intent(this,PIShowGroups::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}