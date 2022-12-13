package com.example.sharednotes.clase

import com.google.gson.annotations.SerializedName

data class Quote(val author: String,
            val id: Int,

            @SerializedName("quote")
            val text: String
            )
{

}