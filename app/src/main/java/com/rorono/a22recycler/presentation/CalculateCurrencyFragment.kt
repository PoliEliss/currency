package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentCalculateCurrencyBinding
import com.rorono.a22recycler.utils.Rounding
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import javax.inject.Inject


class CalculateCurrencyFragment :
    BaseViewBindingFragment<FragmentCalculateCurrencyBinding>(FragmentCalculateCurrencyBinding::inflate) {
    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var viewModel: CurrencyViewModel

    private var rateCurrency: Double = 1.0
    private var rateCurrencyConvertedTo: Double = 1.0
    private var charCodeConvertedCurrency = ""

    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]
        viewModel.getCurrencyDao()

        binding.spinnerSelectedCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.currencyDatabase.observe(viewLifecycleOwner) {
                        for (i in it) {
                            if (i.charCode == adapterView?.getItemAtPosition(position)) {
                                val rateCharCodeText = "1 " + adapterView.getItemAtPosition(position)
                                binding.tvRate.text = rateCharCodeText
                                binding.textInputLayoutCurrencyAmount.hint = i.charCode
                                rateCurrency = i.value
                            }
                        }
                    }
                    val resultTransferCurrency = viewModel.transferToCurrency(
                        rate = rateCurrency,
                        enteredValue = 1.0,
                        convertedTo = rateCurrencyConvertedTo
                    ).toString()
                    val resultTransferCurrencyWithCharCode = "$resultTransferCurrency $charCodeConvertedCurrency"
                    binding.tvRateConvertedTo.text = resultTransferCurrencyWithCharCode
                }
            }

        binding.spinnerTransferredCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.currencyDatabase.observe(viewLifecycleOwner) {
                        for (i in it) {
                            if (i.charCode == adapterView?.getItemAtPosition(position)) {
                                binding.textInputLayoutCurrencyConvertedTo.hint = i.charCode
                                rateCurrencyConvertedTo = i.value
                            }
                        }
                    }

                    charCodeConvertedCurrency = adapterView?.getItemAtPosition(position).toString()
                    val calculateResult = viewModel.transferToCurrency(
                        rate = rateCurrency,
                        enteredValue = 1.0,
                        convertedTo = rateCurrencyConvertedTo
                    ).toString()
                    val calculateResultWithCharCode = "$calculateResult $charCodeConvertedCurrency"
                    binding.tvRateConvertedTo.text = calculateResultWithCharCode
                }
            }

        binding.etCurrencyAmount.hint = "0"
        binding.etCurrencyAmount.addTextChangedListener {
            if (binding.etCurrencyAmount.text.toString() != "" && binding.etCurrencyAmount.hasFocus()) {
                if (binding.etCurrencyAmount.text.toString() == ".") {
                    binding.etCurrencyAmount.setText("0")
                }
                val enteredValue = binding.etCurrencyAmount.text.toString().toDouble()
                try {
                    binding.etCurrencyConvertedTo.setText(
                        viewModel.transferToCurrency(
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrency),
                            enteredValue = enteredValue,
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrencyConvertedTo),
                        ).toString()
                    )

                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.entered_value_greater_zero),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (binding.etCurrencyAmount.text.isNullOrBlank()) {
                binding.etCurrencyConvertedTo.text?.clear()
            }
        }
        binding.etCurrencyConvertedTo.addTextChangedListener {
            if (binding.etCurrencyConvertedTo.text.toString() != "" && binding.etCurrencyConvertedTo.hasFocus()) {
                if (binding.etCurrencyConvertedTo.text.toString() == ".") {
                    binding.etCurrencyConvertedTo.setText("0.")
                    binding.etCurrencyConvertedTo.setSelection(binding.etCurrencyConvertedTo.length())
                }
                val enteredValue = binding.etCurrencyConvertedTo.text.toString().toDouble()
                try {
                    binding.etCurrencyAmount.setText(
                        viewModel.transferToCurrency(
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrencyConvertedTo),
                            enteredValue = enteredValue,
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrency),
                        ).toString()
                    )

                } catch (e: Exception) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.entered_value_greater_zero),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (binding.etCurrencyConvertedTo.text.isNullOrBlank()) {
                binding.etCurrencyAmount.text?.clear()
            }
        }
        binding.toolbarCurrencyCalculateFragment.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}