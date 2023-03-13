package com.ayush.samachar

import com.ayush.samachar.model.NEWS
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

const val  base_url ="https://newsapi.org/"
const val  api_key="08ccb7146e5d4a2aaa9042c910d3630e"
//https://newsapi.org/v2/top-headlines?apiKey=08ccb7146e5d4a2aaa9042c910d3630e&country=in&page=1

interface  NewsInterface {
    @GET("/v2/top-headlines?$api_key")
    fun getheadlines(@Query("country") country:String ,@Query("page")page:Int): Call<NEWS>
}

object news_service{
    val newsser :NewsInterface

    init {
        val retrofit= Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build()
        newsser=retrofit.create(NewsInterface::class.java)
    }

}