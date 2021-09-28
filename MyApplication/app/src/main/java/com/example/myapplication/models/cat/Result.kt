package com.example.myapplication.models.cat

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("webpurl")
    val webpurl: String,
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)