package ru.agafonovilya.pokeapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.agafonovilya.pokeapiapp.databinding.RandomPokemonFragmentBinding
import ru.agafonovilya.pokeapiapp.model.entity.DataCode
import ru.agafonovilya.pokeapiapp.model.entity.ViewModelResult
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.util.imageLoader.IImageLoader
import ru.agafonovilya.pokeapiapp.viewModel.RandomPokemonViewModel

class RandomPokemonFragment : Fragment() {

    private var _binding: RandomPokemonFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RandomPokemonViewModel by viewModel()
    private val imageLoader: IImageLoader by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = RandomPokemonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        observeToData()
    }

    private fun initButton() {
        binding.randomPokemonFragmentRefreshButton.setOnClickListener {
            viewModel.getRandomPokemon()
        }

        binding.randomPokemonFragmentFavouritesButton.setOnClickListener {
            viewModel.savePokemon()
        }
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
        if (data.dataCode == DataCode.POKEMON) {
            val pokemon = data.data as Pokemon
            binding.randomPokemonFragmentName.text = pokemon.name
            imageLoader.loadInto(pokemon.sprites.other.officialArtwork.front_default,
                    binding.randomPokemonFragmentImage)
            binding.randomPokemonFragmentFavouritesButton.visibility = View.VISIBLE
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}