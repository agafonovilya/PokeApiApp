package ru.agafonovilya.pokeapiapp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    @Expose
    val front_default : String
)
