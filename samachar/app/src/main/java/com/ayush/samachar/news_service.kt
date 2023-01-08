package com.ayush.samachar

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

const val  base_url ="https://newsapi.org/"
const val  api_key="08ccb7146e5d4a2aaa9042c910d3630e"
interface Newsinterface {
    @GET("v2/top-headlines?apiKey=$api_key")
    fun getheadlines(@Query("country")country:String, @Query("page")page:Int):Call<Newss>

}//https://newsapi.org/v2/top-headlines?apiKey=08ccb7146e5d4a2aaa9042c910d3630e&country=in&page=1

object Newsservice{
    val newsinstance : Newsinterface
    init {
        val retrofit=Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build()
        newsinstance= retrofit.create(Newsinterface::class.java)
    }
}