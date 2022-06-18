package com.rorono.a22recycler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import com.rorono.a22recycler.models.Valuate
import kotlin.math.floor


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {

    private val args:CurrencyTransferFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrencyTransferBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var currency = args.data
        binding = FragmentCurrencyTransferBinding.inflate(layoutInflater)

        binding.toolbarCurrencyTransferFragment.title = currency.value.toString()
        binding.tvFullNameCurrency.text = currency.name.toString()
        binding.textInputLayoutCurrencyConvertor.hint = (floor(currency.value*100)/100).toString()
        binding.tvExchangeRate.text = (floor(currency.value*100)/100).toString()


       binding.etCurrencyConvertor.addTextChangedListener {
            if (binding.etCurrencyConvertor.text.toString() != "" && binding.etCurrencyConvertor.hasFocus()) {
                currency?.value?.let {
                    convertetToRubls(it,binding.etCurrencyConvertor,binding.etTransferRubles)

                }
            } else if (binding.etCurrencyConvertor.text.isNullOrBlank()) {
                binding.etTransferRubles.text?.clear()
            }
        }
        binding.etTransferRubles.addTextChangedListener {
            if (binding.etTransferRubles.text.toString() != "" && binding.etTransferRubles.hasFocus()) {
                currency?.value?.let {
                     converterToCurrency(it,binding.etTransferRubles,binding.etCurrencyConvertor)
                }
            } else if (binding.etTransferRubles.text.isNullOrBlank()) {
                binding.etCurrencyConvertor.text?.clear()

            }
        }
        return binding.root
    }

    private  fun converterToCurrency(i:Double,inET:EditText, ouET:EditText){
        val valuta =
            inET.text.toString().toDouble() / i
        val result = floor(valuta * 1000) / 1000
       ouET.setText(result.toString())
    }

    private fun convertetToRubls(i: Double,inET: EditText,ouET: EditText){
        val valuta =
            inET.text.toString().toDouble() * i
        val result = floor(valuta * 1000) / 1000
        ouET.setText(result.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarCurrencyTransferFragment.setNavigationOnClickListener {
            val action =
                CurrencyTransferFragmentDirections.actionCurrencyTransferFragmentToCurrencyFragment()
            findNavController().navigate(action)
        }
    }
}




