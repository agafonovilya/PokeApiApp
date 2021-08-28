package ru.agafonovilya.pokeapiapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.*
import ru.agafonovilya.pokeapiapp.R
import ru.agafonovilya.pokeapiapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavMenu()
    }

    private fun setupBottomNavMenu() {
        val navController = findNavController(R.id.my_nav_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
    }
}