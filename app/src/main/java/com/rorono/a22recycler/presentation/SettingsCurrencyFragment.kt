package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentSettingsCurrencyBinding
import com.rorono.a22recycler.settings.DataStoreRepository
import com.rorono.a22recycler.settings.Settings
import com.rorono.a22recycler.settings.ViewModelDataStore
import kotlinx.coroutines.launch


class SettingsCurrencyFragment :
    BaseViewBindingFragment<FragmentSettingsCurrencyBinding>(FragmentSettingsCurrencyBinding::inflate) {

    private lateinit var viewModel: ViewModelDataStore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModelDataStore::class.java]

        viewModel.test.observe(viewLifecycleOwner) {
            when (it) {
                "1" -> binding.radioButtonLightTheme.isChecked = true
                "2" -> {
                    binding.radioButtonDarkTheme.isChecked = true
                    binding.radioButtonDarkTheme.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseEN.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseRU.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseTile.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseLine.setBackgroundResource(R.drawable.radio_selector_black_theme)

                }
            }
        }
       /* viewModel.readLanguageFromDataStore.observe(viewLifecycleOwner){
            when(it){
                "1" -> binding.radioButtonChooseRU.isChecked = true
                "2" -> binding.radioButtonChooseEN.isChecked = true
            }
        }*/
        /*when (Settings.loadTheme(requireContext())) {
            1 -> binding.radioButtonLightTheme.isChecked = true
            2 -> {
                binding.radioButtonDarkTheme.isChecked = true
                binding.radioButtonDarkTheme.setBackgroundResource(R.drawable.radio_selector_black_theme)
                binding.radioButtonChooseEN.setBackgroundResource(R.drawable.radio_selector_black_theme)
                binding.radioButtonChooseRU.setBackgroundResource(R.drawable.radio_selector_black_theme)
                binding.radioButtonChooseTile.setBackgroundResource(R.drawable.radio_selector_black_theme)
                binding.radioButtonChooseLine.setBackgroundResource(R.drawable.radio_selector_black_theme)

            }
        }*/
       /* when (Settings.loadLanguage(requireContext())) {
            1 -> binding.radioButtonChooseRU.isChecked = true
            2 -> binding.radioButtonChooseEN.isChecked = true
        }*/
        when (Settings.loadOrientation(requireContext())) {
            1 -> binding.radioButtonChooseLine.isChecked = true
            2 -> binding.radioButtonChooseTile.isChecked = true
        }
        binding.radioButtonLightTheme.setOnClickListener {

            // Settings.saveTheme(requireContext(), 1)
            viewModel.saveToDataStore("theme", "1")
            requireActivity().recreate()
        }
        binding.radioButtonDarkTheme.setOnClickListener {
            //Settings.saveTheme(requireContext(), 2)
            viewModel.saveToDataStore("theme", "2")
            requireActivity().recreate()
        }

        binding.radioButtonChooseRU.setOnClickListener {
            //  Settings.saveLanguage(requireContext(), 1)
               viewModel.saveToDataStore("language","1")
            requireActivity().recreate()
        }
        binding.radioButtonChooseEN.setOnClickListener {
            //  Settings.saveLanguage(requireContext(), 2)

              viewModel.saveToDataStore("language","2")
            requireActivity().recreate()
        }

        binding.radioButtonChooseLine.setOnClickListener {
             viewModel.saveToDataStore("orientation","1")
          //  Settings.saveOrientation(requireContext(), 1)
            requireActivity().recreate()
        }
        binding.radioButtonChooseTile.setOnClickListener {
            viewModel.saveToDataStore("orientation","2")
            //Settings.saveOrientation(requireContext(), 2)
            requireActivity().recreate()
        }
    }

}

