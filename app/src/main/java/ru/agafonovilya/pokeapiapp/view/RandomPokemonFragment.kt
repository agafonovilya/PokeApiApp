package ru.agafonovilya.pokeapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.agafonovilya.pokeapiapp.Injection
import ru.agafonovilya.pokeapiapp.databinding.RandomPokemonFragmentBinding
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.viewModel.RandomPokemonViewModel

class RandomPokemonFragment : Fragment() {

    companion object {
        fun newInstance() = RandomPokemonFragment()
    }

    private var _binding: RandomPokemonFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RandomPokemonViewModel
    private var requestJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RandomPokemonFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initButton()
        request()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(RandomPokemonViewModel::class.java)
    }

    private fun request() {
        requestJob?.cancel()
        requestJob = lifecycleScope.launch {
            val pokemon = viewModel.getRandomPokemon()
            fillViews(pokemon)
        }
    }

    private fun fillViews(pokemon: Pokemon) {
        binding.randomPokemonFragmentName.text = pokemon.name
        Injection.provideImageLoader().loadInto(pokemon.sprites.other.officialArtwork.front_default, binding.randomPokemonFragmentImage)
        binding.randomPokemonFragmentFavouritesButton.visibility = View.VISIBLE
    }

    private fun initButton() {
        binding.randomPokemonFragmentRefreshButton.setOnClickListener {
            request()
        }

        binding.randomPokemonFragmentFavouritesButton.setOnClickListener {
            viewModel.savePokemon()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}