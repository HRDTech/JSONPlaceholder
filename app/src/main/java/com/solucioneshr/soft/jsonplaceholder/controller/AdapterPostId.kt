package com.solucioneshr.soft.jsonplaceholder.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.model.Post
import com.solucioneshr.soft.jsonplaceholder.model.PostId

class AdapterPostId(): RecyclerView.Adapter<AdapterPostId.PostIdViewModel>() {
    private lateinit var listItem: ArrayList<PostId>

    class PostIdViewModel(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.namePlaceholderActivity)
        val email: TextView = itemView.findViewById(R.id.emailPlaceholderActivity)
        val body: TextView = itemView.findViewById(R.id.bodyPlaceholderActivity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostIdViewModel {
        return PostIdViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_postid, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostIdViewModel, position: Int) {
        holder.name.text = listItem[position].name
        holder.email.text = listItem[position].email
        holder.body.text = listItem[position].body
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setListData(data: ArrayList<PostId>){
        listItem = data
    }
}