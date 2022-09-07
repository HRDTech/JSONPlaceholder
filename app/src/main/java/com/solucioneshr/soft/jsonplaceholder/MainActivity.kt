package com.solucioneshr.soft.jsonplaceholder

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.position
import com.example.awesomedialog.title
import com.gun0912.tedpermission.coroutine.TedPermission
import com.solucioneshr.soft.jsonplaceholder.controller.AdapterPagerPlaceholder
import com.solucioneshr.soft.jsonplaceholder.controller.AdapterPagerSimplePlaceholder
import com.solucioneshr.soft.jsonplaceholder.databinding.ActivityMainBinding
import com.solucioneshr.soft.jsonplaceholder.model.Contact
import com.solucioneshr.soft.jsonplaceholder.util.EventPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabs = binding.tabs

        CoroutineScope(IO).launch {
            val permissionResult = TedPermission.create()
                .setPermissions(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS
                )
                .check()

            if (permissionResult.isGranted){
                EventBus.getDefault().postSticky(EventPermission(true))
            } else{
                AwesomeDialog.build(this@MainActivity)
                    .title(getString(R.string.msg_dialog_permission))
                    .position(AwesomeDialog.POSITIONS.CENTER)
                    .onPositive(getString(R.string.btn_dialog_permission))
                EventBus.getDefault().postSticky(EventPermission(false))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventPermission) {
        if(event.isGranted){
            val fragmentAdapter = AdapterPagerPlaceholder(supportFragmentManager, this@MainActivity)
            viewPager.adapter = fragmentAdapter
            tabs.setupWithViewPager(viewPager)
        } else{
            val fragmentAdapter = AdapterPagerSimplePlaceholder(supportFragmentManager, this@MainActivity)
            viewPager.adapter = fragmentAdapter
            tabs.setupWithViewPager(viewPager)
        }

        EventBus.getDefault().removeStickyEvent(event)
    }

}