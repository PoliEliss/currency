package com.rorono.a22recycler.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentSettingsCurrencyBinding
import com.rorono.a22recycler.settings.Settings


class SettingsCurrencyFragment :
    BaseViewBindingFragment<FragmentSettingsCurrencyBinding>(FragmentSettingsCurrencyBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (Settings.loadTheme(requireContext())) {
            1 -> binding.radioButtonLightTheme.isChecked = true
            2 -> {
                with(binding) {
                    radioButtonDarkTheme.isChecked = true
                    radioButtonDarkTheme.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    radioButtonChooseEN.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    radioButtonChooseRU.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    radioButtonChooseTile.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    radioButtonChooseLine.setBackgroundResource(R.drawable.radio_selector_black_theme)
                }
            }
        }
        when (Settings.loadLanguage(requireContext())) {
            1 -> binding.radioButtonChooseRU.isChecked = true
            2 -> binding.radioButtonChooseEN.isChecked = true
        }
        when (Settings.loadOrientation(requireContext())) {
            1 -> binding.radioButtonChooseLine.isChecked = true
            2 -> binding.radioButtonChooseTile.isChecked = true
        }
        binding.radioButtonLightTheme.setOnClickListener {
            Settings.saveTheme(requireContext(), 1)
            requireActivity().recreate()
        }
        binding.radioButtonDarkTheme.setOnClickListener {
            Settings.saveTheme(requireContext(), 2)
            requireActivity().recreate()
        }

        binding.radioButtonChooseRU.setOnClickListener {
            Settings.saveLanguage(requireContext(), 1)
            requireActivity().recreate()
        }
        binding.radioButtonChooseEN.setOnClickListener {
            Settings.saveLanguage(requireContext(), 2)
            requireActivity().recreate()
        }

        binding.radioButtonChooseLine.setOnClickListener {
            Settings.saveOrientation(requireContext(), 1)
            requireActivity().recreate()
        }
        binding.radioButtonChooseTile.setOnClickListener {
            Settings.saveOrientation(requireContext(), 2)
            requireActivity().recreate()
        }

        binding.toolbarSettingsCurrencyFragment.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


}

