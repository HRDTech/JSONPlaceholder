package com.solucioneshr.soft.jsonplaceholder.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solucioneshr.soft.jsonplaceholder.R
import com.solucioneshr.soft.jsonplaceholder.controller.AdapterPlaceholder
import com.solucioneshr.soft.jsonplaceholder.databinding.FragmentMainBinding
import com.solucioneshr.soft.jsonplaceholder.model.Post
import com.solucioneshr.soft.jsonplaceholder.service.RestViewModel
import com.solucioneshr.soft.jsonplaceholder.ui.activity.DetailPlaceholderActivity

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val listener = object : AdapterPlaceholder.OnItemClickListener{
            override fun onItemClick(data: Post) {
                val intent = Intent(requireContext(), DetailPlaceholderActivity::class.java)
                intent.putExtra("idPost", data.id)
                startActivity(intent)
            }
        }

        val progression = binding.progressPlaceholderFragment

        val adapter = AdapterPlaceholder(listener)
        val recycler = binding.recyclerPlaceholderFragment
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)

        val serviceVM = ViewModelProvider(this)[RestViewModel::class.java]
        serviceVM.getPost()
        serviceVM.getPostLiveData?.observe(requireActivity() , Observer {
            if (it != null){
                progression.visibility = View.GONE
                recycler.visibility = View.VISIBLE
                adapter.setListData(it)
                recycler.adapter = adapter
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}