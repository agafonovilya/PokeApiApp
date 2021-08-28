package ru.agafonovilya.pokeapiapp

import android.content.Context
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.db.PokemonDatabase
import ru.agafonovilya.pokeapiapp.model.db.RoomDbRepository
import ru.agafonovilya.pokeapiapp.model.repository.IRepository
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import ru.agafonovilya.pokeapiapp.model.repository.PokeApiRepository
import ru.agafonovilya.pokeapiapp.util.imageLoader.GlideImageLoader
import ru.agafonovilya.pokeapiapp.util.imageLoader.IImageLoader
import ru.agafonovilya.pokeapiapp.view.ByNameFragment
import ru.agafonovilya.pokeapiapp.view.FavoritesFragment
import ru.agafonovilya.pokeapiapp.view.RandomPokemonFragment
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModelFactory
import ru.agafonovilya.pokeapiapp.viewModel.FavoritesViewModelFactory
import ru.agafonovilya.pokeapiapp.viewModel.RandomPokemonViewModelFactory

object Injection {

    /**
     * Creates an instance of [PokeApiRepository] based on the [PokeApiService]
     */
    private fun providePokeApiRepository(): IRepository {
        return PokeApiRepository(PokeApiService.create())
    }

    /**
     * Creates an instance of [PokemonDatabase]
     */
    private fun provideDatabaseRepository(context: Context): IDbRepository {
        return RoomDbRepository(PokemonDatabase.getInstance(context))
    }

    /**
     * Creates an instance of [GlideImageLoader]
     */
    fun provideImageLoader(): IImageLoader {
        return GlideImageLoader()
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(fragment: Fragment): ViewModelProvider.Factory =
        when (fragment::class.java) {
            ByNameFragment::class.java -> ByNameViewModelFactory(providePokeApiRepository(),
                provideDatabaseRepository(fragment.requireContext()))
            RandomPokemonFragment::class.java -> RandomPokemonViewModelFactory(
                providePokeApiRepository(), provideDatabaseRepository(fragment.requireContext()))
            FavoritesFragment::class.java -> FavoritesViewModelFactory(provideDatabaseRepository(
                fragment.requireContext()))
            else -> ViewModelProvider.NewInstanceFactory()
        }


}