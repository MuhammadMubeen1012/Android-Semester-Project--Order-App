package com.example.piorderapp.adapters

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.admin.PIEditUsers
import com.example.piorderapp.models.PIUserModel
import kotlinx.coroutines.NonDisposableHandle.parent

class UserListAdapter(userList: List<PIUserModel> , internal var context: Context)
    : RecyclerView.Adapter<UserListAdapter.UserViewHolder>(){

    internal var userList: List<PIUserModel> = ArrayList()

    init {
        this.userList =userList
    }

    inner class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        var id: TextView = view.findViewById(R.id.idTV)
        var userName : TextView = view.findViewById(R.id.unTV)
        var password : TextView = view.findViewById(R.id.pswTV)
        var userRole: TextView = view.findViewById(R.id.roleTV)
        var editBTN: ImageView = view.findViewById(R.id.editUserBTN)
        var deleteBTN: ImageView = view.findViewById(R.id.deleteUserBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        //method to bind view
        val view = LayoutInflater.from(context).inflate(R.layout.usercard_layout,parent,false)
        return UserViewHolder(view)
        //view is bind now
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int){
        val users =userList[position]
        holder.id.text = users.ID.toString()
        holder.userName.text =users.username
        holder.password.text = users.password
        holder.userRole.text =  "" + users.userRoleID

        holder.editBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditUsers::class.java)
            intent.putExtra("ID" , users.ID)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.deleteBTN.setOnClickListener{
            val intent: Intent = Intent(context,PIEditUsers::class.java)
            intent.putExtra("ID" , users.ID)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}