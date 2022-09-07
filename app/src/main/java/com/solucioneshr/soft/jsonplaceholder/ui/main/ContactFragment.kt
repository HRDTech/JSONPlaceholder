package com.solucioneshr.soft.jsonplaceholder.ui.main

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.solucioneshr.soft.jsonplaceholder.controller.AdapterContact
import com.solucioneshr.soft.jsonplaceholder.databinding.FragmentContactBinding
import com.solucioneshr.soft.jsonplaceholder.model.Contact
import com.solucioneshr.soft.jsonplaceholder.ui.activity.ContactActivity


class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private lateinit var listContacts: ArrayList<Contact>
    private lateinit var adapter: AdapterContact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        val root = binding.root

        val listener = object : AdapterContact.OnItemClickListener{
            override fun onItemClick(data: Contact) {
                val intent = Intent(requireContext(), ContactActivity::class.java)
                intent.putExtra("isNew", false)
                intent.putExtra("idContact", data.id)
                startActivity(intent)
            }

        }

        listContacts = ArrayList()
        adapter = AdapterContact(listener)
        binding.progressContactFragment.visibility = View.GONE
        binding.recyclerContactFragment.visibility = View.VISIBLE
        binding.recyclerContactFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerContactFragment.setHasFixedSize(true)
        getContacts()

        binding.fabContactFragment.visibility = View.VISIBLE
        binding.fabContactFragment.setOnClickListener {
            val intent = Intent(requireContext(), ContactActivity::class.java)
            intent.putExtra("isNew", true)
            intent.putExtra("idContact", "0")
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("Range")
    private fun getContacts() {
        var contactId = ""
        var displayName = ""

        val resolver: ContentResolver = requireContext().contentResolver
        val cursor: Cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null)!!

        if (cursor.count > 0) {

            while (cursor.moveToNext()) {
                val hasPhoneNumber: Int =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt()
                if (hasPhoneNumber > 0) {
                    contactId =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    displayName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phoneCursor: Cursor = requireActivity().contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null)!!
                    if (phoneCursor.moveToNext()) {
                        val phoneNumber: String =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        listContacts.add(Contact(contactId, displayName, phoneNumber))
                        adapter.setListData(listContacts)
                        binding.recyclerContactFragment.adapter = adapter
                    }
                    phoneCursor.close()
                }
            }
        } else{
            binding.emptyContactFragment.visibility = View.VISIBLE
        }
        cursor.close()
    }

    override fun onResume() {
        listContacts.clear()
        getContacts()
        super.onResume()
    }
}