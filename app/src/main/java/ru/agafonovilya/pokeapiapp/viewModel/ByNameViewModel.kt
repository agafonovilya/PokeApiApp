package ru.agafonovilya.pokeapiapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.agafonovilya.pokeapiapp.model.repository.IRepository

class ByNameViewModel(private val repository: IRepository) : ViewModel() {
    // TODO: Implement the ViewModel
}

class ByNameViewModelFactory(private val repository: IRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ByNameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ByNameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}