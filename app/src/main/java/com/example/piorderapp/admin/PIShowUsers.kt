package com.example.piorderapp.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.adapters.UserListAdapter
import com.example.piorderapp.database.DBHelper
import com.example.piorderapp.databinding.ActivityPishowUsersBinding
import com.example.piorderapp.databinding.ActivityPiuserBinding
import com.example.piorderapp.models.PIUserModel

class PIShowUsers : AppCompatActivity() {
    private lateinit var binding: ActivityPishowUsersBinding

    lateinit var usersRecyclerView: RecyclerView
    var adapter: UserListAdapter ?=null
    var dbHandler: DBHelper ?=null
    var userList: List<PIUserModel> = ArrayList<PIUserModel>()
    var linearLayoutManager: LinearLayoutManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_pishow_users)
        supportActionBar?.hide()
        usersRecyclerView = binding.usersRV
        dbHandler = DBHelper(this)
        showUsers()
    }

    fun showUsers(){
        userList = dbHandler!!.getUsers()
        adapter = UserListAdapter(userList,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        usersRecyclerView.layoutManager = linearLayoutManager
        usersRecyclerView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

}