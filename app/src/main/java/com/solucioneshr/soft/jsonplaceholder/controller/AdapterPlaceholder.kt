package com.solucioneshr.soft.jsonplaceholder.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.model.Post

class AdapterPlaceholder(private var listener: OnItemClickListener): RecyclerView.Adapter<AdapterPlaceholder.PlaceholderViewModel>() {
    private lateinit var listItem: ArrayList<Post>

    interface OnItemClickListener{
        fun onItemClick(data: Post)
    }

    class PlaceholderViewModel(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.titleItemPlaceholderFragment)
        val body: TextView  = itemView.findViewById(R.id.bodyItemPlaceholderFragment)
        val itemClick: View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceholderViewModel {
        return PlaceholderViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_placeholder, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PlaceholderViewModel, position: Int) {
        holder.title.text = listItem[position].title
        holder.body.text = listItem[position].body
        holder.itemClick.setOnClickListener {
            listener.onItemClick(listItem[position])
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setListData(data: ArrayList<Post>){
        listItem = data
    }
}