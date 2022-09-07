package com.solucioneshr.soft.jsonplaceholder.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.model.Contact
import com.solucioneshr.soft.jsonplaceholder.model.Post

class AdapterContact(private var listener: OnItemClickListener): RecyclerView.Adapter<AdapterContact.ContactViewModel>()  {
    private lateinit var listItem: ArrayList<Contact>

    interface OnItemClickListener{
        fun onItemClick(data: Contact)
    }

    class ContactViewModel(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.nameContactFragment)
        val numberPhone: TextView = itemView.findViewById(R.id.phoneContactFragment)
        val itemClick: View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewModel {
        return ContactViewModel(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_contact, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContactViewModel, position: Int) {
        holder.name.text = listItem[position].name
        holder.numberPhone.text = listItem[position].numberPhone
        holder.itemClick.setOnClickListener {
            listener.onItemClick(listItem[position])
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setListData(data: ArrayList<Contact>){
        listItem = data
    }
}