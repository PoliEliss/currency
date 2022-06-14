package com.rorono.a22recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        val repository = Repository(retrofit = RetrofitInstance)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[CurrencyViewModel::class.java]

    }


}