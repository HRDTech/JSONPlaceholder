package com.solucioneshr.soft.jsonplaceholder.service

import com.solucioneshr.soft.jsonplaceholder.model.Post
import com.solucioneshr.soft.jsonplaceholder.model.PostId
import com.solucioneshr.soft.jsonplaceholder.util.ConfigApp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PetitionsRest {

    /*
        Función de get para pots
     */
    @GET(ConfigApp.POSTURL)
    fun getPosts(): Call<ArrayList<Post>>

    /*
        Función de get para potsId
     */
    @GET(ConfigApp.IDPOSTURL)
    fun getPostsId(@Query("postId")id: Int): Call<ArrayList<PostId>>
}