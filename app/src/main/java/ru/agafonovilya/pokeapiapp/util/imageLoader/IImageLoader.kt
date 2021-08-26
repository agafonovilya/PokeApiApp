package ru.agafonovilya.pokeapiapp.util.imageLoader

import android.widget.ImageView

interface IImageLoader {
    fun loadInto(url: String, container: ImageView)
}