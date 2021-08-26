package ru.agafonovilya.pokeapiapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.agafonovilya.pokeapiapp.Injection
import ru.agafonovilya.pokeapiapp.databinding.ByNameFragmentBinding
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModel

class ByNameFragment : Fragment() {

    companion object {
        fun newInstance() = ByNameFragment()
    }

    private var _binding: ByNameFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ByNameViewModel
    private var requestJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ByNameFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initTextInput()
        initButton()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(ByNameViewModel::class.java)
    }

    private fun initTextInput() {
        binding.byNameFragmentTextInputLayout.setEndIconOnClickListener {
            val searchName = binding.byNameFragmentTextInputEditText.text.toString()
            request(searchName)
        }
    }

    private fun request(name: String) {
        requestJob?.cancel()
        requestJob = lifecycleScope.launch {
            val pokemon = viewModel.getPokemonByName(name)
            fillViews(pokemon)
        }
    }

    private fun fillViews(pokemon: Pokemon) {
        binding.byNameFragmentName.text = pokemon.name
        Injection.provideImageLoader().loadInto(pokemon.sprites.other.officialArtwork.front_default, binding.byNameFragmentImage)
        binding.byNameFragmentFavouritesButton.visibility = View.VISIBLE
    }

    private fun initButton() {
        binding.byNameFragmentFavouritesButton.setOnClickListener {
                viewModel.savePokemon()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}