package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.utils.BaseViewBindingFragment
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.R
import  com.rorono.a22recycler.models.remotemodels.Currency
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import com.rorono.a22recycler.utils.Rounding
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import javax.inject.Inject


class CurrencyTransferFragment :
    BaseViewBindingFragment<FragmentCurrencyTransferBinding>(FragmentCurrencyTransferBinding::inflate) {

    @Inject
    lateinit var factory: MainViewModelFactory

    lateinit var viewModel: CurrencyViewModel
    private val args: CurrencyTransferFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]
        val currency = args.currency
        val roundedCurrency = Rounding.getTwoNumbersAfterDecimalPoint(currency.value)


        with(binding) {
            toolbarCurrencyTransferFragment.title = currency.charCode
            tvFullNameCurrency.text = currency.fullName
            textInputLayoutCurrencyConvertor.hint = currency.charCode
            etCurrencyConvertor.hint = getString(R.string._0)
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + " ₽").also {
                tvRate.text = it
            }
            toolbarCurrencyTransferFragment.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
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
    }
}




