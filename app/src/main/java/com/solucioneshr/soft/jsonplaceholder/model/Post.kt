package com.solucioneshr.soft.jsonplaceholder.model

data class Post(val id: Int = 0,
                val title: String = "",
                val body: String = "",
                val userId: Int = 0)