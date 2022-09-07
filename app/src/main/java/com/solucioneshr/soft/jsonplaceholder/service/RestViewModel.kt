package com.solucioneshr.soft.jsonplaceholder.service

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solucioneshr.soft.jsonplaceholder.model.Post
import com.solucioneshr.soft.jsonplaceholder.model.PostId

class RestViewModel(application: Application): AndroidViewModel(application) {
    private var restFun: RestFun? = null

    var getPostLiveData: LiveData<ArrayList<Post>>? = null
    var getIdPostLiveData: LiveData<ArrayList<PostId>>? = null

    init {
        restFun = RestFun()
        getPostLiveData = MutableLiveData()
        getIdPostLiveData = MutableLiveData()
    }

    fun getPost(){
        getPostLiveData = restFun?.getPost()
    }

    fun getIdPost(id: Int){
        getIdPostLiveData = restFun?.getIdPost(id)
    }
}