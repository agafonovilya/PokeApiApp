package ru.agafonovilya.pokeapiapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.agafonovilya.pokeapiapp.R
import ru.agafonovilya.pokeapiapp.viewModel.ByNameViewModel

class ByNameFragment : Fragment() {

    companion object {
        fun newInstance() = ByNameFragment()
    }

    private lateinit var viewModel: ByNameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.by_name_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ByNameViewModel::class.java)
        // TODO: Use the ViewModel
    }

}