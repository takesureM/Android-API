package com.example.myapplication.models.duck

import com.google.gson.annotations.SerializedName

data class Result (
    @SerializedName("message")
    val message: String,
    @SerializedName("url")
    val url: String
)