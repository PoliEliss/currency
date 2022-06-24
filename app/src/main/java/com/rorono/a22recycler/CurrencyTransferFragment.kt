package com.rorono.a22recycler

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import com.rorono.a22recycler.utils.Rounding
import java.io.IOException
import java.lang.Exception

import kotlin.NumberFormatException


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {

    private val args: CurrencyTransferFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrencyTransferBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currency = args.currency
        val roundedCurrency = Rounding.getTwoNumbersAfterDecimalPoint(currency.value)
        binding = FragmentCurrencyTransferBinding.inflate(layoutInflater)
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
                        transferToRubles(
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
                    binding.etTransferRubles.setText("0")
                }
                val enteredValue = binding.etTransferRubles.text.toString().toDouble()
                try {
                    binding.etCurrencyConvertor.setText(
                        converterToCurrency(
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarCurrencyTransferFragment.setNavigationOnClickListener {
            val action =
                CurrencyTransferFragmentDirections.actionCurrencyTransferFragmentToCurrencyFragment()
            findNavController().navigate(action)
        }
    }

    fun converterToCurrency(rate: Double, enteredValue: Double): Double {
        if (rate < 0 || enteredValue < 0) {
            throw IllegalArgumentException()
        }
        val valuate = enteredValue / rate
        return Rounding.getTwoNumbersAfterDecimalPoint(valuate)
    }

    fun transferToRubles(rate: Double, enteredValue: Double): Double {
        if (rate < 0 || enteredValue < 0) {
            throw java.lang.IllegalArgumentException()
        }
        val valuate = enteredValue * rate
        return Rounding.getTwoNumbersAfterDecimalPoint(valuate)
    }
}




