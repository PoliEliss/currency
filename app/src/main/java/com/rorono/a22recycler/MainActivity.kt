package com.rorono.a22recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.rorono.a22recycler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFrag(CurrencyFragment.newInstance(),R.id.container)
    }

    private fun openFrag(fragment: CurrencyFragment, idContainer:Int){
        supportFragmentManager.beginTransaction().replace(idContainer,fragment).commit()
    }


}