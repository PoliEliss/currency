package com.rorono.a22recycler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import android.widget.Toast.makeText as makeText1


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {

    val args: CurrencyTransferFragmentArgs by navArgs()

    private lateinit var binding: FragmentCurrencyTransferBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentCurrencyTransferBinding.inflate(layoutInflater)

        val textEditTextCurrencyConvertor = binding.textInputEditTextCurrencyConvertor
        val textEditTextTransferRubl = binding.textInputEditTextTransferRubl




        val titleToolbarTitle = args.test
        binding.toolbarCurrencyTransferFragment.title = titleToolbarTitle

        val textViewExchangeRate = args.test2
        binding.textViewExchangeRateTransferFragment.text = "$textViewExchangeRate Р"

        binding.textInputLayoutCurrencyConvertor.hint = titleToolbarTitle


        textEditTextCurrencyConvertor.addTextChangedListener {
            if (textEditTextCurrencyConvertor.text.toString()!=""){
                var valuta = textEditTextCurrencyConvertor.text.toString().toDouble() * args.test2.toDouble()
                val result = Math.floor(valuta*1000)/1000

                textEditTextTransferRubl.text?.clear()
                textEditTextTransferRubl.setText(result.toString())
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

            // val textInputEditTextCurrency =  binding.textInputLayoutCurrencyConvertor
            // textInputEditTextCurrency.hint = currency.name

        }


    }
}




