package com.example.piorderapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.piorderapp.R
import com.example.piorderapp.admin.PIEditGroups
import com.example.piorderapp.admin.PIEditUsers
import com.example.piorderapp.models.PIGroupModel
import com.example.piorderapp.models.PIUserModel

class GroupListAdapter(groupList: List<PIGroupModel>, internal var context: Context)
    : RecyclerView.Adapter<GroupListAdapter.GroupViewHolder>(){

    internal var groupList: List<PIGroupModel> = ArrayList()

    init {
        this.groupList =groupList
    }

    inner class GroupViewHolder(view: View): RecyclerView.ViewHolder(view){
        var id: TextView = view.findViewById(R.id.groupidTV)
        var groupName : TextView = view.findViewById(R.id.groupnameTV)
        var editBTN: ImageView = view.findViewById(R.id.editGroupBTN)
        var deleteBTN: ImageView = view.findViewById(R.id.deleteGroupBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListAdapter.GroupViewHolder {
        //method to bind view
        val view = LayoutInflater.from(context).inflate(R.layout.groupcard_layout,parent,false)
        return GroupViewHolder(view)
        //view is bind now
    }

    override fun onBindViewHolder(holder: GroupListAdapter.GroupViewHolder, position: Int){
        val groups =groupList[position]
        holder.id.text = groups.ID.toString()
        holder.groupName.text =groups.groupName

        holder.editBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditGroups::class.java)
            intent.putExtra("ID" , groups.ID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        holder.deleteBTN.setOnClickListener{
            val intent: Intent = Intent(context, PIEditGroups::class.java)
            intent.putExtra("ID" , groups.ID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }
}