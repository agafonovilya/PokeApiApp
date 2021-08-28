package ru.agafonovilya.pokeapiapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.agafonovilya.pokeapiapp.Injection
import ru.agafonovilya.pokeapiapp.databinding.FavoritesItemBinding
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB

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
        RecyclerView.ViewHolder(binding.root) {

        private val imageLoader = Injection.provideImageLoader()

        fun bind(pokemonFromDB: PokemonFromDB) {
            binding.pokemonItemName.text = pokemonFromDB.name
            imageLoader.loadInto(pokemonFromDB.imageUrl, binding.pokemonItemImage)
        }
    }
}