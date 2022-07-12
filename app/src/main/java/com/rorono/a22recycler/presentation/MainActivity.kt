package com.rorono.a22recycler.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.R
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.databinding.ActivityMainBinding
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.settings.Settings
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (Settings.loadTheme(this)) {
            1 -> setTheme(R.style.Theme_22recycler)
            2 -> setTheme(R.style.Theme2)
        }

          when(Settings.loadLanguage(this)){
              1-> setLocale("ru")
              2-> setLocale("en")
          }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(retrofit = RetrofitInstance) //убрать  в Dagger
        val dataBase = CurrencyDataBase.getInstance(context = this)
        val dataBaseDao = dataBase.currencyDao()
        val viewModelFactory = MainViewModelFactory(repository, dataBase = dataBaseDao)
        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navController = navController)
    }
    fun setLocale(language:String) {
        val locale = Locale(language)
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(locale)
        resources.updateConfiguration(conf, dm)
    }
}
