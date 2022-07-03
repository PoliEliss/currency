package com.rorono.a22recycler.presentation

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


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_22recycler)
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
}