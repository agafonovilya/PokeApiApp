package ru.agafonovilya.pokeapiapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.agafonovilya.pokeapiapp.databinding.FavoritesItemBinding
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB
import ru.agafonovilya.pokeapiapp.util.imageLoader.IImageLoader

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    var pokemonList: List<PokemonFromDB> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount() = pokemonList.size

    inner class FavoritesViewHolder(private val binding: FavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root), KoinComponent {

        private val imageLoader: IImageLoader by inject()

        fun bind(pokemonFromDB: PokemonFromDB) {
            binding.pokemonItemName.text = pokemonFromDB.name
            imageLoader.loadInto(pokemonFromDB.imageUrl, binding.pokemonItemImage)
        }
    }
}