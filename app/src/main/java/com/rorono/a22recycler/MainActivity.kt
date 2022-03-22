package com.rorono.a22recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.rorono.a22recycler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val adapter = CurrencyAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
   init()

    }

    private fun init(){
        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity,3)
            recyclerView.adapter = adapter
            val currency = Currency("jj","iii")
            adapter.add( currency)

        }
    }

}