package ru.agafonovilya.pokeapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.agafonovilya.pokeapiapp.databinding.FavoritesFragmentBinding
import ru.agafonovilya.pokeapiapp.model.entity.DataCode
import ru.agafonovilya.pokeapiapp.model.entity.ViewModelResult
import ru.agafonovilya.pokeapiapp.model.entity.db.PokemonFromDB
import ru.agafonovilya.pokeapiapp.view.adapter.FavoritesAdapter
import ru.agafonovilya.pokeapiapp.viewModel.FavoritesViewModel

class FavoritesFragment: Fragment()  {

    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeToData()
        request()
    }

    private fun initRecyclerView() {
        binding.favoritesFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesAdapter = FavoritesAdapter()
        binding.favoritesFragmentRecyclerView.adapter = favoritesAdapter
    }

    private fun observeToData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ViewModelResult.Loading -> {
                        }
                        is ViewModelResult.Success<*> -> {
                            renderSuccessData(it)
                        }
                        is ViewModelResult.Error -> {
                            it.error.message?.let { message -> showSnackbar(message) }
                        }
                    }
                }
            }
        }
    }

    private fun renderSuccessData(data: ViewModelResult.Success<*>) {
        if (data.dataCode == DataCode.LIST_OF_DB_POKEMON ) {
            val pokemonList = data.data as List<PokemonFromDB>
            favoritesAdapter.pokemonList = pokemonList
        }
    }

    private fun request() {
        viewModel.getPokemonList()
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}