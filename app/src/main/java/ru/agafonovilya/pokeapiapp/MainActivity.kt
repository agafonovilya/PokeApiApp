package ru.agafonovilya.pokeapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.agafonovilya.pokeapiapp.databinding.MainActivityBinding
import ru.agafonovilya.pokeapiapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private var selectedFragment: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.search_by_name -> { showToast("search_by_name") }
                R.id.random_search -> { showToast("random_search") }
                R.id.favorites -> { showToast("favorites") }
            }
            selectedFragment?.let { replaceFragment(it) }
            true
        }

        if (savedInstanceState == null) {
            selectedFragment = MainFragment.newInstance()
            replaceFragment(selectedFragment as MainFragment)
        }

    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}