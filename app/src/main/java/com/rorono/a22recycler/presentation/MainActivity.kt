package com.rorono.a22recycler.presentation


import android.content.res.Resources
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
import com.rorono.a22recycler.settings.DataStoreRepository
import com.rorono.a22recycler.settings.Settings
import com.rorono.a22recycler.settings.ViewModelDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ViewModelDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_22recycler)
        viewModel = ViewModelProvider(this)[ViewModelDataStore::class.java]

        super.onCreate(savedInstanceState)
        viewModel.test.observe(this, object : Observer<String> {
            /* override fun onChanged(t: OrderCreationState?) {
                if (t?.isPropsLoading?.not() == true) {
                    viewModelOrderCreation.createOrder()
                    viewModelOrderCreation.state.removeObserver(this)
                }
            }*/
            override fun onChanged(t: String?) {
                if (t == "1") {
                    setTheme(R.style.Theme_22recycler)
                    Toast.makeText(this@MainActivity,"1",Toast.LENGTH_LONG).show()

                }
                if (t == "2") {
                    setTheme(R.style.Theme2)
                    Toast.makeText(this@MainActivity,"2",Toast.LENGTH_LONG).show()

                }
            }
        })

     val them =   viewModel.test.observe(this) {

        }


/*
        viewModel.readThemeFromDataStore.observe(this) {
            Log.d("TEST61", "theme ${it}")
            when (it) {
                "1" -> setTheme(R.style.Theme_22recycler)
                "2" -> setTheme(R.style.Theme2)
            }
        }*/



        viewModel.readLanguageFromDataStore.observe(this) {
            when (it) {
                "1" -> setLocale("ru")
                "2" -> setLocale("en")
            }
        }
        /*  lifecycleScope.launch {
              when (viewModel.read("theme")) {
                  "1" -> setTheme(R.style.Theme_22recycler)
                  "2" -> setTheme(R.style.Theme2)
              }
          }*/

        Log.d("TEST", "OnCreate")
        /*  viewModel = ViewModelProvider(this)[ViewModelDataStore::class.java]
          viewModel.readThemeFromDataStore.observe(this) {
              when (it) {
                  "1" -> setTheme(R.style.Theme_22recycler)
                  "2" -> setTheme(R.style.Theme2)
              }
          }*/
/*
         GlobalScope.launch { //НУ ПОЧЕМУУУ вот это работает похоже что-то с жизненным циклом, может смена темы в активити не отображается в фрагменте
            val result = viewModel.repository.read("theme")
            when (result) {
                "1" -> setTheme(R.style.Theme_22recycler)
                "2" -> setTheme(R.style.Theme2)
            }

            val test = viewModel.repository.read("language")
            when(test){
                "1" -> setLocale("ru")
                "2" -> setLocale("en")
            }
        }*/
        /*  GlobalScope.launch {
              viewModel.repository.readFromThemeDataStore.collect {
                  Log.d("TEST", "collect")
                  Log.d(
                      "TEST",
                      "collect it ${it}"
                  ) // в коллекте кто-то живет и он запоминает т.е коллект хорошо работает  или нет т.е похоже тему вызвать отсюда нельзя все белое
                  when (it) {
                      "1" -> setTheme(R.style.Theme_22recycler)
                      "2" -> setTheme(R.style.Theme2)
                  }
              }*/


        /* viewModel.readLanguageFromDataStore.observe(this) {
 Log.d("TEST61","viewModel.readLanguageFromDataStore.value ${viewModel.readLanguageFromDataStore.value}")
 when (it) {
     "1" -> {
         Log.d("TEST61","kkkk")
         setLocale("ru")
     }
     "2" -> setLocale("en")
 }
}*/
        /*viewModel.readThemeFromDataStore.observe(this) {
Log.d("TEST61","theme ${it}")
when (it) {
    "1" -> setTheme(R.style.Theme_22recycler)
    "2" -> setTheme(R.style.Theme2)
}
}*/


        /*when (Settings.loadTheme(this)) {
1 -> setTheme(R.style.Theme_22recycler)
2 -> setTheme(R.style.Theme2)
}*/
        /* when (Settings.loadLanguage(this)) {
 1 -> setLocale("ru")
 2 -> setLocale("en")
}*/
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*  when (Settings.loadTheme(this)) {
  2 -> {
      setTheme(R.style.Theme2)
      binding.bottomNavigation.itemIconTintList =
          getColorStateList(R.color.bottom_nav_dark_color)
  }
}*/
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




