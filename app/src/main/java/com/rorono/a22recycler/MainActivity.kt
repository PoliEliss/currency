package com.rorono.a22recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CurrencyViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_22recycler)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val repository = Repository()

       // val viewModelFactory = MainViewModelFactory(repository)

       // viewModel.getValute()
       // viewModel.myResponce.observe(this, Observer { responce->
          //  Log.d("Responce",responce.Name.toString())
          //  Log.d("Responce",responce.CharCode.toString())
          //  Log.d("Responce",responce.Value.toString())
       // })

    }


}