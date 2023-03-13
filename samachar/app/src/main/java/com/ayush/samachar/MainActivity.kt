package com.ayush.samachar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.ayush.samachar.databinding.ActivityMainBinding
import com.ayush.samachar.model.NEWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      binding.text.setOnClickListener {
          Log.d("click", "onCreate: ")
      }
        getnews()
    }

    private fun getnews() {
        val news= news_service.newsser.getheadlines("in",1)
        news.enqueue(object : Callback<NEWS?>{
            override fun onResponse(call: Call<NEWS?>, response: Response<NEWS?>) {
               val new = response.body()
               // val totalresult = response.body()!!.status
                //val totalresult = response.body()!!.totalResults
                //binding.text.text= totalresult.toString()
                    if(new!=null)
                    Log.d("sucess", "${new.status}")
            }

            override fun onFailure(call: Call<NEWS?>, t: Throwable) {
                Log.d("fail  ", "onFailure: ")
            }

        })
//        news.enqueue(object :Callback<NEWS>{
//            override fun onResponse(call: Call<NEWS>, response: Response<NEWS>) {
//                val news = response.body()
//                if(news!=null)
//                {
//                    Log.e("sucess","Get the data ")
//                }
//            }
//
//            override fun onFailure(call: Call<NEWS>, t: Throwable) {
//                Log.e("fail done ","Error on displaying data",t)
//            }
//        })
    }


}