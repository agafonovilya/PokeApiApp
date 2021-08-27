package ru.agafonovilya.pokeapiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navOptions
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

        val navOptions = navOptions {
            anim {
                enter = R.anim.fade_in
                exit - R.anim.fade_out
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.search_by_name -> findNavController(R.id.my_nav_host_fragment).navigate(R.id.destination_byNameFragment, null, navOptions)
                R.id.random_search -> findNavController(R.id.my_nav_host_fragment).navigate(R.id.destination_randomPokemonFragment, null, navOptions)
                R.id.favorites -> { showToast("favorites") }
            }
            true
        }

    }


    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}