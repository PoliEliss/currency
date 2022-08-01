package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rorono.a22recycler.utils.BaseViewBindingFragment
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

    private var currencyValueToConvert: Double = 1.0
    private var currencyValueConverted: Double = 1.0
    private var charCodeCurrencyConverted = ""

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
                                val rateCharCodeText =
                                    "1 " + adapterView.getItemAtPosition(position)
                                binding.tvRate.text = rateCharCodeText
                                binding.textInputLayoutCurrencyAmount.hint = i.charCode
                                currencyValueToConvert = i.value
                            }
                        }
                    }
                    val resultTransferCurrency = viewModel.transferToCurrency(
                        rate = currencyValueToConvert,
                        enteredValue = 1.0,
                        convertedTo = currencyValueConverted
                    ).toString()
                    val resultTransferCurrencyWithCharCode =
                        "$resultTransferCurrency $charCodeCurrencyConverted"
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
                                currencyValueConverted = i.value
                            }
                        }
                    }

                    charCodeCurrencyConverted = adapterView?.getItemAtPosition(position).toString()
                    val calculateResult = viewModel.transferToCurrency(
                        rate = currencyValueToConvert,
                        enteredValue = 1.0,
                        convertedTo = currencyValueConverted
                    ).toString()
                    val calculateResultWithCharCode = "$calculateResult $charCodeCurrencyConverted"
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
                            Rounding.getTwoNumbersAfterDecimalPoint(currencyValueToConvert),
                            enteredValue = enteredValue,
                            Rounding.getTwoNumbersAfterDecimalPoint(currencyValueConverted),
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
                            Rounding.getTwoNumbersAfterDecimalPoint(currencyValueConverted),
                            enteredValue = enteredValue,
                            Rounding.getTwoNumbersAfterDecimalPoint(currencyValueToConvert),
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