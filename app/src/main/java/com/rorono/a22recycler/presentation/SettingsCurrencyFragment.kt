package com.rorono.a22recycler.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rorono.a22recycler.utils.BaseViewBindingFragment
import com.rorono.a22recycler.R
import com.rorono.a22recycler.Splach
import com.rorono.a22recycler.databinding.FragmentSettingsCurrencyBinding
import com.rorono.a22recycler.utils.Settings
import com.rorono.a22recycler.settings.ViewModelDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SettingsCurrencyFragment :
    BaseViewBindingFragment<FragmentSettingsCurrencyBinding>(FragmentSettingsCurrencyBinding::inflate) {

    private lateinit var viewModel: ViewModelDataStore



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST","onViewCreated")
        val settingAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation)
        binding.ivIconSettings.startAnimation(settingAnimation)

        viewModel = ViewModelProvider(this)[ViewModelDataStore::class.java]

        viewModel.test.observe(viewLifecycleOwner) {
            when (it) {
                "1" -> {
                    binding.radioButtonLightTheme.isChecked = true

                    ImageViewCompat.setImageTintList(
                        binding.ivIconSettings,
                        ContextCompat.getColorStateList(requireContext(), R.color.blu)
                    )
                    binding.tvToolbarTitleSettings.setTextColor(ContextCompat.getColor(requireContext(),R.color.blu))
                    binding.toolbarSettingsCurrencyFragment.setNavigationIconTint(
                        getResources().getColor(
                            R.color.blu
                        )
                    )
                }
                "2" -> {
                    ImageViewCompat.setImageTintList(
                        binding.ivIconSettings,
                        ContextCompat.getColorStateList(requireContext(), R.color.black)
                    )
                    binding.toolbarSettingsCurrencyFragment.setNavigationIconTint(
                        getResources().getColor(
                            R.color.black
                        )
                    )
                    binding.radioButtonDarkTheme.isChecked = true
                    binding.radioButtonDarkTheme.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseEN.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseRU.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseTile.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.radioButtonChooseLine.setBackgroundResource(R.drawable.radio_selector_black_theme)
                    binding.tvToolbarTitleSettings.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

                }
            }
        }
         viewModel.language.observe(viewLifecycleOwner){
             when(it){
                 "1" ->{
                     binding.radioButtonChooseRU.isChecked = true

                 }
                 "2" ->{
                     binding.radioButtonChooseEN.isChecked = true
                 }

             }
             Log.d("TEST","it it ${it}")
         }

        when (Settings.loadOrientation(requireContext())) {
            1 -> binding.radioButtonChooseLine.isChecked = true
            2 -> binding.radioButtonChooseTile.isChecked = true
        }
        binding.radioButtonLightTheme.setOnClickListener {
            lifecycleScope.launch {
                viewModel.saveToDataStore("theme", "1")
                delay(500)
            }
            requireActivity().recreate()

        }
        binding.radioButtonDarkTheme.setOnClickListener {
            viewModel.saveToDataStore("theme", "2")
            requireActivity().recreate()


        }

        binding.radioButtonChooseRU.setOnClickListener {
            viewModel.saveToDataStore("language", "1")
            Log.d("TEST","Click 1")


            lifecycleScope.launch {
                requireActivity().recreate()
                delay(500)
            }

            val intent = Intent(activity,MainActivity::class.java)
            intent.putExtra("Language","1")
            requireActivity().startActivity(intent)
        }
        binding.radioButtonChooseEN.setOnClickListener {
            viewModel.saveToDataStore("language", "2")
            Log.d("TEST","Click 2")

            requireActivity().recreate()
        }

        binding.radioButtonChooseLine.setOnClickListener {
            viewModel.saveToDataStore("orientation", "1")
            //  Settings.saveOrientation(requireContext(), 1)
            
        }
        binding.radioButtonChooseTile.setOnClickListener {
            viewModel.saveToDataStore("orientation", "2")
            //Settings.saveOrientation(requireContext(), 2)
            requireActivity().recreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TEST","onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST","onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("TEST"," super.onDetach()")
    }
}

