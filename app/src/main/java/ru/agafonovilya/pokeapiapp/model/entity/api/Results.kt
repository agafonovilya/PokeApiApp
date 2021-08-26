package ru.agafonovilya.pokeapiapp.model.entity.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Results (
    @SerializedName("name")
    @Expose
    val name : String
)