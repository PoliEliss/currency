package com.rorono.a22recycler.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.ActivityMainBinding
import com.rorono.a22recycler.utils.Settings
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (Settings.loadTheme(this)) {
            1 -> setTheme(R.style.Theme_22recycler)
            2 -> setTheme(R.style.Theme2)
        }
        when (Settings.loadLanguage(this)) {
            1 -> setLocale("ru")
            2 -> setLocale("en")
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (Settings.loadTheme(this)) {
            2 -> {
                setTheme(R.style.Theme2)
                binding.bottomNavigation.itemIconTintList =
                    getColorStateList(R.color.bottom_nav_dark_color)
            }
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navController = navController)
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, displayMetrics)
    }
}
