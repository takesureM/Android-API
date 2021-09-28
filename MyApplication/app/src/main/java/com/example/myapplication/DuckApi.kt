package com.example.myapplication

import com.example.myapplication.models.duck.Duck
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DuckApi {

    @GET("/api/random")
    suspend  fun getDuck(
        @QueryMap queries: Map<String, String>
    ): Response<Duck>
}

