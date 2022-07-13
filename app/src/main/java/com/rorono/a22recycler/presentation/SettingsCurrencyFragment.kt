package com.rorono.a22recycler.presentation

import android.content.Intent
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
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        binding.buttonChooseNightTheme.setOnClickListener {
            Settings.saveTheme(requireContext(), 2)
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        binding.buttonChooseRU.setOnClickListener {
            Settings.saveLanguage(requireContext(), 1)
            requireActivity().recreate()
        }
        binding.buttonChooseENG.setOnClickListener {
            Settings.saveLanguage(requireContext(), 2)
            requireActivity().recreate()
        }

        binding.buttonChooseLine.setOnClickListener {
            Settings.saveOrientation(requireContext(),1)
            requireActivity().recreate()
        }
        binding.buttonChooseTile.setOnClickListener {
            Settings.saveOrientation(requireContext(),2)
            requireActivity().recreate()
        }
    }
}