package com.safaldeepsingh.musicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.safaldeepsingh.musicapp.entities.DiscogResponse
import com.safaldeepsingh.musicapp.network.DiscogApi
import com.safaldeepsingh.musicapp.adapter.AlbumAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val albumAdapter = AlbumAdapter(context = this)
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchEditText: EditText = findViewById(R.id.main_searchString)
        val searchButton: Button = findViewById(R.id.main_search)
        val openFavourites: Button = findViewById(R.id.main_openFavourites)
        recyclerView = findViewById(R.id.main_searchResults)

        recyclerView.adapter = albumAdapter
        recyclerView.setHasFixedSize(true) //Performance: If RecyclerView height and width doesn't change

        searchButton.setOnClickListener {
            val searchString: String = searchEditText.text.toString()
            searchDiscogs(searchString)
        }
        openFavourites.setOnClickListener {
            startActivity(Intent(this, FavouritesListActivity::class.java))
        }
    }
    private fun searchDiscogs(searchString: String) {
        val callAlbums = DiscogApi.RETROFIT_SERVICE.getAlbums(search = searchString)
        callAlbums.enqueue(object: Callback<DiscogResponse>
        {
            override fun onResponse(
                call: Call<DiscogResponse>,
                response: Response<DiscogResponse>
            ) {
                var discogResponse = response.body()
                val noResultsMessage: TextView = findViewById(R.id.main_noResultsMessage)
                if(discogResponse != null){
                    albumAdapter.setData(discogResponse.albums)
                    if(discogResponse.albums.size>0){
                        noResultsMessage.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                    else{
                        noResultsMessage.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Log.e("main","success")
                }else{
                    Log.e("main","failed")
                }
            }

            override fun onFailure(call: Call<DiscogResponse>, t: Throwable) {
                Log.e("main failure","failed")
                println(t)
            }
        })

    }
}