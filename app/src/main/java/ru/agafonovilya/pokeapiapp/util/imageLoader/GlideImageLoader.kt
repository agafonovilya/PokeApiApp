package ru.agafonovilya.pokeapiapp.util.imageLoader

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader: IImageLoader {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .centerInside()
            .into(container)
    }
}