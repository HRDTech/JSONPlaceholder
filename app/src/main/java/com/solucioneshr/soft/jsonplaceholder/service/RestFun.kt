package com.solucioneshr.soft.jsonplaceholder.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.solucioneshr.soft.jsonplaceholder.model.Post
import com.solucioneshr.soft.jsonplaceholder.model.PostId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestFun {
    private var petitionsRest: PetitionsRest? = null

    init {
        petitionsRest = ClientRest.getApi().create(PetitionsRest::class.java)
    }

    /*
        Función Get Post
     */
    fun getPost(): LiveData<ArrayList<Post>>{
        val data = MutableLiveData<ArrayList<Post>>()

        petitionsRest?.getPosts()?.enqueue(object : Callback<ArrayList<Post>>{
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>,
            ) {
                val res = response.body()

                if (response.code() == 200 && res != null){
                    data.value = res!!
                }
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                Log.e("Apk_Placeholder", t.message.toString())
            }

        })

        return data
    }

    /*
        Función Get idPost
     */
    fun getIdPost(id: Int): LiveData<ArrayList<PostId>>{
        val data = MutableLiveData<ArrayList<PostId>>()

        petitionsRest?.getPostsId(id)?.enqueue(object : Callback<ArrayList<PostId>>{
            override fun onResponse(call: Call<ArrayList<PostId>>, response: Response<ArrayList<PostId>>) {
                val res = response.body()

                if (response.code() == 200 && res != null){
                    data.value = res!!
                }
            }

            override fun onFailure(call: Call<ArrayList<PostId>>, t: Throwable) {
                Log.e("Apk_Placeholder", t.message.toString())
            }

        })

        return data
    }
}