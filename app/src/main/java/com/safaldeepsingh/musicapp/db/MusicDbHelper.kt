package com.safaldeepsingh.musicapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/*
*
*
*
* */

private const val SQL_CREATE_TABLE =
    "CREATE TABLE ${MusicDbContract.AlbumTable.TABLE_NAME} ("+
            "${MusicDbContract.AlbumTable.ALBUM_NAME} TEXT, "+
            "${MusicDbContract.AlbumTable.COVER_IMAGE} TEXT, "+
            "${MusicDbContract.AlbumTable.YEAR} INTEGER, "+
            "${MusicDbContract.AlbumTable.GENRES} TEXT, "+
            "${MusicDbContract.AlbumTable.ID} INTEGER  "+
            ")"
private const val DROP_TABLE = "DROP TABLE IF EXISTS ${MusicDbContract.AlbumTable.TABLE_NAME}"
class MusicDbHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        const val DATABASE_NAME = "music.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        super.onDowngrade(db, oldVersion, newVersion)
    }
}