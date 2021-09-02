package ru.agafonovilya.pokeapiapp.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.agafonovilya.pokeapiapp.model.db.IDbRepository
import ru.agafonovilya.pokeapiapp.model.db.PokemonDatabase
import ru.agafonovilya.pokeapiapp.model.db.RoomDbRepository
import ru.agafonovilya.pokeapiapp.model.repository.IRepository
import ru.agafonovilya.pokeapiapp.model.repository.PokeApiRepository
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import ru.agafonovilya.pokeapiapp.util.imageLoader.GlideImageLoader
import ru.agafonovilya.pokeapiapp.util.imageLoader.IImageLoader
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModel
import ru.agafonovilya.pokeapiapp.viewModel.FavoritesViewModel
import ru.agafonovilya.pokeapiapp.viewModel.RandomPokemonViewModel

val application = module {
    single<IRepository> { PokeApiRepository(PokeApiService.create()) }
    single<IDbRepository> { RoomDbRepository(PokemonDatabase.getInstance(androidContext())) }
    factory<IImageLoader> { GlideImageLoader() }
    single { androidContext().resources }
}

val byNameScreen = module {
    viewModel { ByNameViewModel(get(), get(), get()) }
}

val randomScreen = module {
    viewModel { RandomPokemonViewModel(get(), get(), get()) }
}

val favoritesScreen = module {
    viewModel { FavoritesViewModel(get(), get()) }
}