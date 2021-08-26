package ru.agafonovilya.pokeapiapp.model.entity.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonList (
    @SerializedName("count")
    @Expose
    val count : Int,
    @SerializedName("results")
    @Expose
    val results : List<Results>
)