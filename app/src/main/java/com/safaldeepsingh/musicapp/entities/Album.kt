package com.safaldeepsingh.musicapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Album(
    @SerializedName("title") val name: String,
    @SerializedName("genre") val genres: List<String>,
    @SerializedName("thumb") val coverImage: String,
    @SerializedName("year") val year: String,
    @SerializedName("id") val ID: Int
):Serializable {
}