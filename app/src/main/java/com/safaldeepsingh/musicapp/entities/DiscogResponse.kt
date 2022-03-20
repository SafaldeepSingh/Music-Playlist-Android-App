package com.safaldeepsingh.musicapp.entities

import com.google.gson.annotations.SerializedName

class DiscogResponse(
    @SerializedName("results") val albums: List<Album>
) {
}