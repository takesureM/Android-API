package com.example.myapplication

import com.example.myapplication.models.cat.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CatApi {

    @GET("/catapi/")
    suspend  fun getCat(
        @QueryMap queries: Map<String, String>
    ): Response<Cat>

}