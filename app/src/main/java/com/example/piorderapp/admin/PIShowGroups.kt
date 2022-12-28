package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.adapters.GroupListAdapter
import com.example.piorderapp.adapters.UserListAdapter
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPishowGroupsBinding
import com.example.piorderapp.databinding.ActivityPishowUsersBinding
import com.example.piorderapp.models.PIGroupModel
import com.example.piorderapp.models.PIUserModel

class PIShowGroups : AppCompatActivity() {
    private lateinit var binding: ActivityPishowGroupsBinding

    lateinit var groupRecyclerView: RecyclerView
    var adapter: GroupListAdapter?=null
    var dbHandler: DBHelper?=null
    var groupList: List<PIGroupModel> = ArrayList<PIGroupModel>()
    var linearLayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pishow_groups)

        groupRecyclerView = binding.groupRV
        dbHandler = DBHelper(this)
        showGroups()
    }

    fun showGroups(){
        groupList = dbHandler!!.getGroups()
        adapter = GroupListAdapter(groupList,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        groupRecyclerView.layoutManager = linearLayoutManager
        groupRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
}