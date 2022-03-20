package com.safaldeepsingh.musicapp.network

import com.safaldeepsingh.musicapp.entities.DiscogResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Only Accessible within the file.
private const val BASE_URL = "https://api.discogs.com/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

//The implementation of the interface is done by Retrofit.
//Defines how Retrofit talks to the web server using HTTP requests.
interface DiscogApiService {

//    @GET("users/{user}/repos")
//    fun getRepositories(@Path("user") user: String) : Call<List<Repository>>

    @GET("database/search")
    fun getAlbums(
        @Query("key") key: String = "hybciPmmpRxvHZxzocYQ",
        @Query("secret") secret: String = "oCrCIqLZtAdOXocIIWkaKOVOFUqPCYzW",
        @Query("artist") search: String,
        @Query("country") country: String = "canada",
    )
    : Call<DiscogResponse>

}

//call     GithubApi.retrofitService()
object DiscogApi {
    val RETROFIT_SERVICE: DiscogApiService by lazy { //Lazy means will not create until call to object.
        retrofit.create(DiscogApiService::class.java)   //This is expensive. Thus the singleton.
        //This implements the Interface class. The body of each method.
    }
}













