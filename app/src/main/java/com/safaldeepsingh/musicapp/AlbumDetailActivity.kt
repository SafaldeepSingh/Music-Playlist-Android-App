package com.safaldeepsingh.musicapp

import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.safaldeepsingh.musicapp.db.AlbumTable
import com.safaldeepsingh.musicapp.entities.Album
import com.safaldeepsingh.musicapp.adapter.AlbumAdapter
import com.squareup.picasso.Picasso

class AlbumDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        val toggleFavButton: Button = findViewById(R.id.albumDetail_toggleFav)
        val albumTable: AlbumTable = AlbumTable(this)
        val coverImageView: ImageView = findViewById(R.id.albumDetail_coverImage)
        val myIntent = intent
        if(intent!= null && intent.hasExtra(AlbumAdapter.EXTRA_ALBUM)){
            val album: Album? = intent.getSerializableExtra(AlbumAdapter.EXTRA_ALBUM) as? Album
            if(album!= null){
                val coverImage: ImageView = findViewById(R.id.albumDetail_coverImage)
                val title: TextView = findViewById(R.id.albumDetail_title)
                val year: TextView = findViewById(R.id.albumDetail_year)
                val ID: TextView = findViewById(R.id.albumDetail_id)

                title.text = album.name
                year.text = "Year " + album.year
                ID.text = "ID " + album.ID
                if(album.coverImage!="")
                    Picasso.get().load(album.coverImage).into(coverImage)
                else
                    coverImage.setImageResource(R.drawable.placeholder)
                val ifAlbumExists = albumTable.ifExists(album)
                changeToggleButtonUI(toggleFavButton,ifAlbumExists)
                toggleFavButton.setOnClickListener {
                    if(toggleFavButton.text == "Add")
                    {
                        albumTable.insert(album)
                        changeToggleButtonUI(it as Button, true)
                    }else{
                        albumTable.delete(album)
                        changeToggleButtonUI(it as Button, false)
                    }

                }
            }
        }
    }

    private fun changeToggleButtonUI(toggleFavButton: Button, ifAlbumExists: Boolean) {
        if(ifAlbumExists){
            toggleFavButton.setText("Remove")
            toggleFavButton.setBackgroundColor(Color.RED)
        }else{
            toggleFavButton.setText("Add")
            toggleFavButton.setBackgroundColor(Color.GREEN)
        }
    }
}