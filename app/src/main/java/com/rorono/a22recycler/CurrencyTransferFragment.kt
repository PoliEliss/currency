package com.rorono.a22recycler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import com.rorono.a22recycler.utils.Rounding


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {

    private val args: CurrencyTransferFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrencyTransferBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currency = args.currency
        val roundedCurrency = Rounding.getRounding(currency.value)
        binding = FragmentCurrencyTransferBinding.inflate(layoutInflater)
        binding.toolbarCurrencyTransferFragment.title = currency.charCode
        binding.tvFullNameCurrency.text = currency.fullName
        binding.textInputLayoutCurrencyConvertor.hint = currency.charCode
        binding.etCurrencyConvertor.hint = getString(R.string._0)
        (Rounding.getRounding(currency.value).toString() + " P").also {
            binding.tvRate.text = it
        }
        binding.etCurrencyConvertor.addTextChangedListener {
            if (binding.etCurrencyConvertor.text.toString() != "" && binding.etCurrencyConvertor.hasFocus()) {
                val enteredValue = binding.etCurrencyConvertor.text.toString().toDouble()
                binding.etTransferRubles.setText(
                    transferToRubles(
                        roundedCurrency,
                        enteredValue = enteredValue
                    ).toString()
                )
            } else if (binding.etCurrencyConvertor.text.isNullOrBlank()) {
                binding.etTransferRubles.text?.clear()
            }
        }
        binding.etTransferRubles.addTextChangedListener {
            if (binding.etTransferRubles.text.toString() != "" && binding.etTransferRubles.hasFocus()) {
                val enteredValue = binding.etTransferRubles.text.toString().toDouble()
                binding.etCurrencyConvertor.setText(
                    converterToCurrency(
                        roundedCurrency,
                        enteredValue
                    ).toString()
                )
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

    private fun converterToCurrency(rate: Double, enteredValue: Double): Double {
        val valuate = enteredValue / rate
        return Rounding.getRounding(valuate)
    }

    private fun transferToRubles(rate: Double, enteredValue: Double): Double {
        val valuate = enteredValue * rate
        return Rounding.getRounding(valuate)
    }
}




