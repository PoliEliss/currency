package com.rorono.a22recycler.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentSettingsCurrencyBinding
import com.rorono.a22recycler.settings.Settings


class SettingsCurrencyFragment :
    BaseViewBindingFragment<FragmentSettingsCurrencyBinding>(FragmentSettingsCurrencyBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonChooseDayTheme.setOnClickListener {
            Settings.saveTheme(requireContext(), 1)
            requireActivity().recreate()
        }
        binding.buttonChooseNightTheme.setOnClickListener {
            Settings.saveTheme(requireContext(), 2)
            requireActivity().recreate()
        }
    }

}