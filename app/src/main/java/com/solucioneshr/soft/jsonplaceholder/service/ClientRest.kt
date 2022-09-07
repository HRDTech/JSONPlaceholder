package com.solucioneshr.soft.jsonplaceholder.service

import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.solucioneshr.soft.jsonplaceholder.util.ConfigApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ClientRest {
    companion object{
        private var retrofit: Retrofit?=null

        fun getApi(): Retrofit{
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ConfigApp.BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!
        }

    }
}