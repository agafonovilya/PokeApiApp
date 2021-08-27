package ru.agafonovilya.pokeapiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import ru.agafonovilya.pokeapiapp.R
import ru.agafonovilya.pokeapiapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private var selectedFragment: Fragment? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.search_by_name -> replaceFragment(ByNameFragment.newInstance())
                R.id.random_search -> replaceFragment(RandomPokemonFragment.newInstance())
                R.id.favorites -> { showToast("favorites") }
            }
            selectedFragment?.let { replaceFragment(it) }
            true
        }

        if (savedInstanceState == null) {
            selectedFragment = ByNameFragment.newInstance()
            replaceFragment(selectedFragment as ByNameFragment)
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