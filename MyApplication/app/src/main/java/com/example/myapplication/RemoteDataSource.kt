package com.example.myapplication

import com.example.myapplication.models.cat.Cat
import com.example.myapplication.models.duck.Duck
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val catApi: CatApi,
    private val duckApi: DuckApi
) {
    suspend  fun getCat(queries: Map<String, String>): Response<Cat> {
        return catApi.getCat(queries)
    }

    suspend  fun getDuck(queries: Map<String, String>): Response<Duck> {
        return duckApi.getDuck(queries)
    }
}