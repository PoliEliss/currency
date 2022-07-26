package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentCalculateCurrencyBinding
import com.rorono.a22recycler.utils.Rounding
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.lang.Exception
import javax.inject.Inject


class CalculateCurrencyFragment : BaseViewBindingFragment<FragmentCalculateCurrencyBinding>(FragmentCalculateCurrencyBinding::inflate) {
    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var viewModel: CurrencyViewModel
    private var rateCurrency1:Double = 0.0
    private var rateCurrency2:Double = 0.0

    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]
        viewModel.getCurrencyDao()
        binding.spinnerCurrencyTransfer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(adapterView:  AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.currencyDatabase.observe(viewLifecycleOwner){
                   for (i in it){
                       if (i.charCode == adapterView?.getItemAtPosition(position) ){
                           binding.tvRateTransferTo.text = i.value.toString()
                           binding.textInputLayoutTransferCurrency.hint = i.charCode
                                      rateCurrency2 = i.value
                       }
                   }
                }
            }

        }

        binding.spinnerSelectedCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(adapterView:  AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.currencyDatabase.observe(viewLifecycleOwner){
                    for (i in it){
                        if (i.charCode == adapterView?.getItemAtPosition(position) ){
                            binding.tvRate.text = i.value.toString()
                            binding.textInputLayoutCurrencyConvertor.hint = i.charCode
                                  rateCurrency1 = i.value

                        }
                    }
                }
            }

        }
        binding.etCurrencyConvertor.hint ="0"
        binding.etCurrencyConvertor.addTextChangedListener {
            if (binding.etCurrencyConvertor.text.toString() != "" && binding.etCurrencyConvertor.hasFocus()) {
                if (binding.etCurrencyConvertor.text.toString() == ".") {
                    binding.etCurrencyConvertor.setText("0")
                }
                val enteredValue = binding.etCurrencyConvertor.text.toString().toDouble()
                try {
                    binding.etTransferRubles.setText(
                        viewModel.transferToCurrency(
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrency1),
                            enteredValue = enteredValue,
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrency2),
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
                        viewModel.transferToCurrency(
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrency2),
                            enteredValue = enteredValue,
                            Rounding.getTwoNumbersAfterDecimalPoint(rateCurrency1),
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
    }
}