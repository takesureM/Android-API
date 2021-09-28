package com.example.myapplication.models.duck


import com.google.gson.annotations.SerializedName

data class Duck(
    @SerializedName("results")
    val results: List<Result>,

)