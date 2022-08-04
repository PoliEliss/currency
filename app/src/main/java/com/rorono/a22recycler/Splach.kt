package com.rorono.a22recycler

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rorono.a22recycler.databinding.ActivitySplashBinding
import com.rorono.a22recycler.presentation.MainActivity
import com.rorono.a22recycler.settings.ViewModelDataStore
import com.rorono.a22recycler.utils.SplashViewModels
import java.util.*

class Splach : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SplashViewModels::class.java]

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val money = AnimationUtils.loadAnimation(this, R.anim.aplha_animation_variant_2)
        binding.tvAppName.startAnimation(money)
        val set = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyYen.startAnimation(set)
        val rubles = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyRuble.startAnimation(rubles)
        val dollar = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.currencyDollar.startAnimation(dollar)
        val cronDatsk = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyDanishKrone.startAnimation(cronDatsk)
        val dollar2 = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyDollar2.startAnimation(dollar2)
        val currencyLb = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyLb.startAnimation(currencyLb)
        val currencyLb2 = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyLb2.startAnimation(currencyLb2)
        val currencyPolskZloty = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyPolishZloty.startAnimation(currencyPolskZloty)
        val monat = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyLira.startAnimation(monat)
        val evro = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyEuro.startAnimation(evro)
        val evro2 = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyEuro2.startAnimation(evro2)
        val monat2 = AnimationUtils.loadAnimation(this, R.anim.search_anim)
        binding.ivCurrencyLira2.startAnimation(monat2)

        var intentTheme = "1"
        var intentLanguage = "2"

        Handler().postDelayed({
            viewModel.selectTheme.observe(this@Splach, object : Observer<String> {
                override fun onChanged(t: String?) {
                    if (t == "1") {
                        setTheme(R.style.Theme_22recycler)
                        Toast.makeText(this@Splach, "1", Toast.LENGTH_LONG).show()
                        intentTheme ="1"
                    }
                    if (t == "2") {
                        setTheme(R.style.Theme2)
                        Toast.makeText(this@Splach, "2", Toast.LENGTH_LONG).show()
                        intentTheme ="2"
                    }
                }
            })

            viewModel.language.observe(this@Splach,object :Observer<String>{
                override fun onChanged(t: String?) {
                    if (t =="1"){
                        setLocale("ru")
                        intentLanguage="1"
                    }
                    if (t =="2"){
                        setLocale("en")
                        intentLanguage="2"
                    }
                }

            })

            val intent = Intent(this@Splach,MainActivity::class.java)
            intent.putExtra("Theme",intentTheme)
            intent.putExtra("Language",intentLanguage)
            startActivity(intent)
        }, 10000)


    }

    fun setLocale(language: String) {
        val locale = Locale(language)
        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.setLocale(locale)
        resources.updateConfiguration(conf, dm)
    }
}
