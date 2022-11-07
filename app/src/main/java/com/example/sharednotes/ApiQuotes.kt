package com.example.sharednotes

import retrofit2.Call
import retrofit2.http.GET

interface ApiQuotes {

    @GET("random.json/")
    fun getRandomQuote(): Call<Quote>
}