package com.project.farmingapp.model

import com.project.farmingapp.model.data.WeatherRootList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL ="https://api.openweathermap.org/"
const val API_KEY ="d0ce8d03d45981c7b55253b30eb0e8d1"
interface weatherInterface {
    @GET("data/2.5/forecast?appid=$API_KEY")
    fun getWeather(@Query("lat")lat:String, @Query("lon")lon:String): Call<WeatherRootList>
}

object WeatherApi {
    val weatherInstances:weatherInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherInstances =retrofit.create(weatherInterface::class.java)
    }
}