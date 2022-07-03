package com.rorono.a22recycler.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import com.rorono.a22recycler.utils.Rounding
import java.lang.Exception


class CurrencyTransferFragment :
    BaseViewBindingFragment<FragmentCurrencyTransferBinding>(FragmentCurrencyTransferBinding::inflate) {

    private val args: CurrencyTransferFragmentArgs by navArgs()
    private val viewModel by activityViewModels<CurrencyViewModel>() //исправить

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currency = args.currency
        val roundedCurrency = Rounding.getTwoNumbersAfterDecimalPoint(currency.value)

        binding.toolbarCurrencyTransferFragment.title = currency.charCode
        binding.tvFullNameCurrency.text = currency.fullName
        binding.textInputLayoutCurrencyConvertor.hint = currency.charCode
        binding.etCurrencyConvertor.hint = getString(R.string._0)
        (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + " P").also {
            binding.tvRate.text = it
        }
        binding.etCurrencyConvertor.addTextChangedListener {
            if (binding.etCurrencyConvertor.text.toString() != "" && binding.etCurrencyConvertor.hasFocus()) {
                if (binding.etCurrencyConvertor.text.toString() == ".") {
                    binding.etCurrencyConvertor.setText("0")
                }
                val enteredValue = binding.etCurrencyConvertor.text.toString().toDouble()
                try {
                    binding.etTransferRubles.setText(
                        viewModel.transferToRubles(
                            roundedCurrency,
                            enteredValue = enteredValue
                        ).toString()
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.entered_value_greater_zero),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (binding.etCurrencyConvertor.text.isNullOrBlank()) {
                binding.etTransferRubles.text?.clear()
            }
        }
        binding.etTransferRubles.addTextChangedListener {
            if (binding.etTransferRubles.text.toString() != "" && binding.etTransferRubles.hasFocus()) {
                if (binding.etTransferRubles.text.toString() == ".") {
                    binding.etTransferRubles.setText("0.")
                    binding.etTransferRubles.setSelection(binding.etTransferRubles.length())
                }
                val enteredValue = binding.etTransferRubles.text.toString().toDouble()
                try {
                    binding.etCurrencyConvertor.setText(
                        viewModel.converterToCurrency(
                            roundedCurrency,
                            enteredValue
                        ).toString()
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.entered_value_greater_zero),
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else if (binding.etTransferRubles.text.isNullOrBlank()) {
                binding.etCurrencyConvertor.text?.clear()
            }
        }

        binding.toolbarCurrencyTransferFragment.setNavigationOnClickListener {
            val action =
                CurrencyTransferFragmentDirections.actionCurrencyTransferFragmentToCurrencyFragment()
            findNavController().navigate(action)
        }
    }

}







