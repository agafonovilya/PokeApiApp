package ru.agafonovilya.pokeapiapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.agafonovilya.pokeapiapp.Injection
import ru.agafonovilya.pokeapiapp.R
import ru.agafonovilya.pokeapiapp.databinding.ByNameFragmentBinding
import ru.agafonovilya.pokeapiapp.model.entity.DataCode
import ru.agafonovilya.pokeapiapp.model.entity.ViewModelResult
import ru.agafonovilya.pokeapiapp.model.entity.api.Pokemon
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModel
import java.util.*


class ByNameFragment : Fragment() {

    private var _binding: ByNameFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ByNameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ByNameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initTextInput()
        initButton()
        observeToData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(this))
            .get(ByNameViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTextInput() {

        viewModel.getItemsForAutoCompleteTextView()

        binding.byNameFragmentAutoCompleteTextView.setOnTouchListener { _, _ ->
            binding.byNameFragmentAutoCompleteTextView.showDropDown()
            return@setOnTouchListener false
        }

        binding.byNameFragmentTextInputLayout.setEndIconOnClickListener {
            val searchName =
                binding.byNameFragmentAutoCompleteTextView.text.toString().lowercase().trim()
            viewModel.getPokemonByName(searchName)

            hideKeyboard()
        }
    }

    private fun initButton() {
        binding.byNameFragmentFavouritesButton.setOnClickListener {
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
        when (data.dataCode) {
            DataCode.POKEMON -> {
                renderPokemonData(data.data as Pokemon)
            }
            DataCode.LIST_OF_POKEMON_NAME -> {
                renderListOfPokemonName(data.data as List<String>)
            }
        }
    }

    private fun renderListOfPokemonName(items: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.auto_complete_text_view_item, items)
        binding.byNameFragmentAutoCompleteTextView.setAdapter(adapter)
    }

    private fun renderPokemonData(pokemon: Pokemon) {
        binding.byNameFragmentName.text = pokemon.name
        Injection.provideImageLoader()
            .loadInto(pokemon.sprites.other.officialArtwork.front_default,
                binding.byNameFragmentImage)
        binding.byNameFragmentFavouritesButton.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.byNameFragmentAutoCompleteTextView.windowToken, 0)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}