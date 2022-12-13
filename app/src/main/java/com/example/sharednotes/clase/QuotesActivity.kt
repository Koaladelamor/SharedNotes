package com.example.sharednotes.clase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sharednotes.databinding.ActivityQuotesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextQuoteButton.setOnClickListener {
            val outside = Retrofit.Builder()
                .baseUrl("http://quotes.stormconsultancy.co.uk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val call = outside.create(ApiQuotes::class.java)
            call.getRandomQuote().enqueue(object : Callback<Quote>{

                override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                    val quote = response.body() ?: return Toast.makeText(this@QuotesActivity, "Something went wrong :(", Toast.LENGTH_SHORT).show()
                    binding.quoteTextView.text = quote.text
                    binding.authorTextView.text = quote.author
                }

                override fun onFailure(call: Call<Quote>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }



}