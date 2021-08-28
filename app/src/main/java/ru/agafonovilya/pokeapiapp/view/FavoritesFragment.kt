package ru.agafonovilya.pokeapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.agafonovilya.pokeapiapp.Injection
import ru.agafonovilya.pokeapiapp.databinding.FavoritesFragmentBinding
import ru.agafonovilya.pokeapiapp.view.adapter.FavoritesAdapter
import ru.agafonovilya.pokeapiapp.viewModel.FavoritesViewModel

class FavoritesFragment: Fragment()  {

    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesViewModel
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

        initViewModel()
        initRecyclerView()
        request()

    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(FavoritesViewModel::class.java)
    }

    private fun initRecyclerView() {
        binding.favoritesFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoritesAdapter = FavoritesAdapter()
        binding.favoritesFragmentRecyclerView.adapter = favoritesAdapter
    }

    private fun request() {
        lifecycleScope.launch {
            val pokemonList = viewModel.getPokemonList()
            favoritesAdapter.pokemonList = pokemonList
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}