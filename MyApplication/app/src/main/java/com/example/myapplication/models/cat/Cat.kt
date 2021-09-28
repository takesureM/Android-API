package com.example.myapplication.models.cat


import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("results")
    val results: List<Result>,

)