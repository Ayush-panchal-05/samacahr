package com.ayush.samachar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getnews()
    }

    private fun getnews() {
        val news:Call<Newss> =Newsservice.newsinstance.getheadlines("in",1)
        news.enqueue(object : Callback<Newss>{
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(call: Call<Newss>, response: Response<Newss>) {
               val news:Newss? =response.body()
                if(news!=null)
                {
                    Log.d("pqpqpq",news.toString())
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<Newss>, t: Throwable) {
                Log.d("pqpqpq","Error in this code ",t)
            }
        })
    }
}