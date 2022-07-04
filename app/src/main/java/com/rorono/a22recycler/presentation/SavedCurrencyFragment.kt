package com.rorono.a22recycler.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentSavedCurrencyBinding
import com.rorono.a22recycler.viewmodel.CurrencyViewModel


class SavedCurrencyFragment :
    BaseViewBindingFragment<FragmentSavedCurrencyBinding>(FragmentSavedCurrencyBinding::inflate) {
    private val viewModel by activityViewModels<CurrencyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
        }
    }
}