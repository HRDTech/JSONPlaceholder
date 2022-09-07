package com.solucioneshr.soft.jsonplaceholder.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.controller.AdapterPostId
import com.solucioneshr.soft.jsonplaceholder.databinding.ActivityDetailPlacehostBinding
import com.solucioneshr.soft.jsonplaceholder.databinding.ActivityMainBinding
import com.solucioneshr.soft.jsonplaceholder.service.RestViewModel

class DetailPlaceholderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPlacehostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPlacehostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle(R.string.title_detail_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val idPost = intent.extras?.getInt("idPost")

        val progress = binding.progressPlaceholderActivity

        val adapter = AdapterPostId()
        binding.recyclerPlaceholderActivity.layoutManager = LinearLayoutManager(this)
        binding.recyclerPlaceholderActivity.setHasFixedSize(true)

        if (idPost != null){
            val serviceVM = ViewModelProvider(this)[RestViewModel::class.java]
            serviceVM.getIdPost(idPost)
            serviceVM.getIdPostLiveData?.observe(this, Observer {
                if (it.size > 0){
                    progress.visibility = View.GONE
                    binding.recyclerPlaceholderActivity.visibility = View.VISIBLE
                    adapter.setListData(it)
                    binding.recyclerPlaceholderActivity.adapter = adapter
                }
            })
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
}