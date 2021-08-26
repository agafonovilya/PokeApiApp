package ru.agafonovilya.pokeapiapp

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.agafonovilya.pokeapiapp.model.retrofit.PokeApiService
import ru.agafonovilya.pokeapiapp.repository.PokeApiRepository
import ru.agafonovilya.pokeapiapp.view.ByNameFragment
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModelFactory

object Injection {

    /**
     * Creates an instance of [PokeApiRepository] based on the [PokeApiService]
     */
    private fun providePokeApiRepository(): PokeApiRepository {
        return PokeApiRepository(PokeApiService.create())
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(fragment: Fragment): ViewModelProvider.Factory =
        when(fragment::class.java) {
            ByNameFragment::class.java -> ByNameViewModelFactory(providePokeApiRepository())
            else -> ViewModelProvider.NewInstanceFactory()
        }

}