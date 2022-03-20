package com.safaldeepsingh.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.safaldeepsingh.musicapp.db.AlbumTable
import com.safaldeepsingh.musicapp.adapter.AlbumAdapter

class FavouritesListActivity : AppCompatActivity() {
    private val albumAdapter = AlbumAdapter(true, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites_list)

        val recyclerView: RecyclerView = findViewById(R.id.favouritesList_recyclerView)
        recyclerView.adapter = albumAdapter
        recyclerView.setHasFixedSize(true) //Performance: If RecyclerView height and width doesn't change

        val albumTable: AlbumTable = AlbumTable(this)
        albumAdapter.setData(albumTable.getAll())

    }
}