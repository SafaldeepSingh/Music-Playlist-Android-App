package com.safaldeepsingh.musicapp.db

import android.content.ContentValues
import android.content.Context
import com.safaldeepsingh.musicapp.entities.Album

class AlbumTable(context: Context) {
    private var dbHelper= MusicDbHelper(context)
    fun insert(album: Album): Long{
        val values = ContentValues().apply {
            put(MusicDbContract.AlbumTable.ALBUM_NAME, album.name)
            put(MusicDbContract.AlbumTable.ID, album.ID)
            put(MusicDbContract.AlbumTable.YEAR, album.year)
            put(MusicDbContract.AlbumTable.COVER_IMAGE, album.coverImage)
            put(MusicDbContract.AlbumTable.GENRES, album.genres.joinToString(","))
        }
        val writeToDb = dbHelper.writableDatabase
        val newRowId = writeToDb.insert(MusicDbContract.AlbumTable.TABLE_NAME, null, values)
        return newRowId
    }

    fun getAll(): List<Album>{
        val albums:MutableList<Album> = mutableListOf()
        val readFromDb = dbHelper.readableDatabase
        val projection = arrayOf(
            MusicDbContract.AlbumTable.ALBUM_NAME,
            MusicDbContract.AlbumTable.ID,
            MusicDbContract.AlbumTable.YEAR,
            MusicDbContract.AlbumTable.COVER_IMAGE,
            MusicDbContract.AlbumTable.GENRES
        )
//        val selection = "${MusicDbContract.AlbumTable.ALBUM_NAME} = ?"
//        val selectionArgs = arrayOf("INXS- I Send A Message")

        val orderBy = "${MusicDbContract.AlbumTable.ALBUM_NAME} DESC"

        val cursor = readFromDb.query(
            MusicDbContract.AlbumTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            orderBy
        )
        //cursor starts at -1
        with(cursor){
            while(moveToNext()){
//                Log.e("Reading DB",
//                    getString(getColumnIndexOrThrow(MusicDbContract.AlbumTable.ALBUM_NAME)))
                val album = Album(
                    getString(getColumnIndexOrThrow(MusicDbContract.AlbumTable.ALBUM_NAME)),
                    getString(getColumnIndexOrThrow(MusicDbContract.AlbumTable.GENRES)).split(",").toList(),
                    getString(getColumnIndexOrThrow(MusicDbContract.AlbumTable.COVER_IMAGE)),
                    getString(getColumnIndexOrThrow(MusicDbContract.AlbumTable.YEAR)),
                    getString(getColumnIndexOrThrow(MusicDbContract.AlbumTable.ID)).toInt()
                )
                albums.add(album)
            }
        }
        cursor.close()
        return albums
    }
    fun ifExists(album: Album): Boolean{
        val albums:MutableList<Album> = mutableListOf()
        val readFromDb = dbHelper.readableDatabase
        val projection = arrayOf(
            MusicDbContract.AlbumTable.ALBUM_NAME,
            MusicDbContract.AlbumTable.ID,
            MusicDbContract.AlbumTable.YEAR,
            MusicDbContract.AlbumTable.COVER_IMAGE
        )
        val selection = "${MusicDbContract.AlbumTable.ID} = ?"
        val selectionArgs = arrayOf(album.ID.toString())

        val orderBy = "${MusicDbContract.AlbumTable.ALBUM_NAME} DESC"

        val cursor = readFromDb.query(
            MusicDbContract.AlbumTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            orderBy
        )
        //cursor starts at -1
        val ifExists = cursor.moveToNext()
        cursor.close()
        return ifExists
    }
    fun update(album: Album): Boolean{
        val values = ContentValues().apply {
            put(MusicDbContract.AlbumTable.ALBUM_NAME, album.name)
            put(MusicDbContract.AlbumTable.ID, album.ID)
            put(MusicDbContract.AlbumTable.YEAR, album.year)
            put(MusicDbContract.AlbumTable.COVER_IMAGE, album.coverImage)
        }
        val whereClause = "${MusicDbContract.AlbumTable.ID} = ?"
        val whereClauseArgs = arrayOf(album.ID.toString())

        val dbWrite = dbHelper.writableDatabase

        val rowsUpdated = dbWrite.update(
            MusicDbContract.AlbumTable.TABLE_NAME,
            values,
            whereClause,
            whereClauseArgs,
        )
        return rowsUpdated == 1
    }
    fun delete(album: Album): Boolean{
        val whereClause = "${MusicDbContract.AlbumTable.ID} = ?"
        val whereClauseArgs = arrayOf(album.ID.toString())

        val dbWrite = dbHelper.writableDatabase

        val deletedRows = dbWrite.delete(
            MusicDbContract.AlbumTable.TABLE_NAME,
            whereClause,
            whereClauseArgs
        )
        return deletedRows >= 1
    }
    fun deleteAll(): Boolean{
//        val whereClause = "${MusicDbContract.AlbumTable.ID} = ?"
//        val whereClauseArgs = arrayOf(album.ID.toString())

        val dbWrite = dbHelper.writableDatabase

        val deletedRows = dbWrite.delete(
            MusicDbContract.AlbumTable.TABLE_NAME,
            null,
            null
        )
        return deletedRows >= 1
    }
}