package com.solucioneshr.soft.jsonplaceholder.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.*
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.databinding.ActivityContactBinding


class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val isNew = intent.extras?.getBoolean("isNew")

        if (isNew != null){
            if (isNew){
                supportActionBar?.setTitle(R.string.title_new_contact_activity)

                binding.layoutBtnEditContactActivity.visibility = View.GONE
                binding.layoutBtnNewContactActivity.visibility = View.VISIBLE

                binding.btnSaveContactActivity.setOnClickListener {
                    if (!binding.nameContactActivity.text.isNullOrEmpty()){
                        if (!binding.phoneContactActivity.text.isNullOrEmpty()){
                            val contactIntent = Intent(ContactsContract.Intents.Insert.ACTION)
                            contactIntent.type = ContactsContract.RawContacts.CONTENT_TYPE
                            contactIntent
                                .putExtra(ContactsContract.Intents.Insert.NAME, binding.nameContactActivity.text)
                                .putExtra(ContactsContract.Intents.Insert.PHONE, binding.phoneContactActivity.text)
                            startActivityForResult(contactIntent, 1)
                        } else{
                            AwesomeDialog.build(this@ContactActivity)
                                .title(getString(R.string.msg_no_phone_contact))
                                .position(AwesomeDialog.POSITIONS.CENTER)
                                .onPositive(getString(R.string.btn_dialog_permission))
                        }
                    } else{
                        AwesomeDialog.build(this@ContactActivity)
                            .title(getString(R.string.msg_no_name_contact))
                            .position(AwesomeDialog.POSITIONS.CENTER)
                            .onPositive(getString(R.string.btn_dialog_permission))
                    }
                }
            } else{
                supportActionBar?.setTitle(R.string.title_edit_contact_activity)
                val idContact = intent.extras?.getString("idContact")

                var lookupKey = ""

                val cursor: Cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(idContact),
                    null)!!
                if (cursor.moveToNext()) {
                    lookupKey =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))
                    val phoneNumber: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val displayName =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    binding.nameContactActivity.setText(displayName)
                    binding.phoneContactActivity.setText(phoneNumber)
                }
                cursor.close()

                binding.btnDelContactActivity.setOnClickListener {
                    try {
                        val uri: Uri =
                            Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                                lookupKey)
                        contentResolver.delete(uri, null, null)
                        finish()
                    } catch (e: Exception) {
                        AwesomeDialog.build(this@ContactActivity)
                            .title(e.toString())
                            .position(AwesomeDialog.POSITIONS.CENTER)
                            .onPositive(getString(R.string.btn_dialog_permission))
                    }
                }
                binding.btnUpdateContactActivity.setOnClickListener {
                    if (!binding.nameContactActivity.text.isNullOrEmpty()){
                        if (!binding.phoneContactActivity.text.isNullOrEmpty()){
                            val uri: Uri =
                                Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI,
                                    lookupKey)

                            val contactIntent = Intent(Intent.ACTION_EDIT).apply {
                                /*
                                 * Sets the contact URI to edit, and the data type that the
                                 * Intent must match
                                 */
                                setDataAndType(uri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
                            }

                            startActivityForResult(contactIntent, 2)
                        } else{
                            AwesomeDialog.build(this@ContactActivity)
                                .title(getString(R.string.msg_no_phone_contact))
                                .position(AwesomeDialog.POSITIONS.CENTER)
                                .onPositive(getString(R.string.btn_dialog_permission))
                        }
                    } else{
                        AwesomeDialog.build(this@ContactActivity)
                            .title(getString(R.string.msg_no_name_contact))
                            .position(AwesomeDialog.POSITIONS.CENTER)
                            .onPositive(getString(R.string.btn_dialog_permission))
                    }
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // in on activity result method.
        if (requestCode == 1) {
            // we are checking if the request code is 1
            if (resultCode == RESULT_OK) {
                AwesomeDialog.build(this@ContactActivity)
                    .title(getString(R.string.title_ok_add_contact))
                    .body(getString(R.string.msg_ok_add_contact))
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .onPositive(getString(R.string.btn_yes_add_contact))
                    .onNegative(getString(R.string.btn_no_add_contact), action = {
                        finish()
                    })
            }
            // else we are displaying a message as contact addition has cancelled.
            if (resultCode == RESULT_CANCELED) {
                AwesomeDialog.build(this@ContactActivity)
                    .title(getString(R.string.title_no_add_contact))
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .onPositive(getString(R.string.btn_dialog_permission))
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                finish()
            }
        }
    }
}