package ru.agafonovilya.pokeapiapp.model.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("official-artwork")
    @Expose
    val officialArtwork : OfficialArtwork

)
