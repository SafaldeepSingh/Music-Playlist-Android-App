package com.safaldeepsingh.musicapp.db

import android.provider.BaseColumns

object MusicDbContract {
    object AlbumTable: BaseColumns{
        const val TABLE_NAME = "album"
        const val ALBUM_NAME = "name"
        const val COVER_IMAGE = "cover_image"
        const val YEAR = "year"
        const val ID = "id"
        const val GENRES = "genres"
    }
}