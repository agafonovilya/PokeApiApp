package ru.agafonovilya.pokeapiapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.agafonovilya.pokeapiapp.databinding.ByNameFragmentBinding
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModel

class ByNameFragment : Fragment() {

    companion object {
        fun newInstance() = ByNameFragment()
    }

    private var _binding: ByNameFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ByNameViewModel

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
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideViewmodelFactory(this))
            .get(ByNameViewModel::class.java)
    }

    private fun initTextInput() {
        binding.byNameFragmentTextInputLayout.setEndIconOnClickListener {
            val searchName = binding.byNameFragmentTextInputEditText.text.toString()
            // TODO: 24.08.2021   data request
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}