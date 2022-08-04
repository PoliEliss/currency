package com.rorono.a22recycler.presentation


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.ActivityMainBinding
import com.rorono.a22recycler.settings.ViewModelDataStore
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ViewModelDataStore

    override fun onCreate(savedInstanceState: Bundle?) {


        viewModel = ViewModelProvider(this)[ViewModelDataStore::class.java]
        val theme = intent.extras?.getString("Theme")
        val language = intent.extras?.getString("Language")

        when (theme) {
            "1" -> setTheme(R.style.Theme_22recycler)
            "2" -> setTheme(R.style.Theme2)
        }
        when (language) {
            "1" -> setLocale("ru")
            "2" -> setLocale("en")
        }
        viewModel.language.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                if (t == "1") {
                    setLocale("ru")
                    Log.d("TEST", "!!ru ${t}")
                    Toast.makeText(this@MainActivity, "ru", Toast.LENGTH_SHORT).show()
                }
                if (t == "2") {
                    setLocale("en")
                    Log.d("TEST", "!!en ${t}")
                    Toast.makeText(this@MainActivity, "en", Toast.LENGTH_SHORT).show()
                }
            }

        })

        viewModel.selectTheme.observe(this, object : Observer<String> {

            override fun onChanged(t: String?) {
                if (t == "1") {
                    Toast.makeText(this@MainActivity, "10000", Toast.LENGTH_SHORT).show()
                    binding.bottomNavigation.itemIconTintList =
                        getColorStateList(R.color.bottom_nav_color)
                    setTheme(R.style.Theme_22recycler)
                }
                if (t == "2") {
                    Toast.makeText(this@MainActivity, "20000", Toast.LENGTH_SHORT).show()
                    binding.bottomNavigation.itemIconTintList =
                        getColorStateList(R.color.bottom_nav_dark_color)
                    setTheme(R.style.Theme2)
                }
            }
        })
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navController = navController)

    }

    fun setLocale(language: String) {
        val locale = Locale(language)
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(locale)
        resources.updateConfiguration(conf, dm)
    }

}




