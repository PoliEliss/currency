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
import kotlin.math.floor
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


        textEditTextCurrencyConvertor.setOnClickListener {
            if (textEditTextCurrencyConvertor.text.toString()!=""){
                var valuta = textEditTextCurrencyConvertor.text.toString().toDouble() * args.test2.toDouble()
               var  result = floor(valuta * 1000) /1000

                textEditTextTransferRubl.text?.clear()
                textEditTextTransferRubl.setText(result.toString())
            } else{
                textEditTextTransferRubl.text?.clear()
            }
        }
        textEditTextTransferRubl.setOnClickListener {
            if ( textEditTextTransferRubl.text.toString()!=""){
                var valuta = textEditTextTransferRubl.text.toString().toDouble() / args.test2.toDouble()
                var  result = floor(valuta * 1000) / 1000
                textEditTextCurrencyConvertor.text?.clear()
                textEditTextCurrencyConvertor.setText(result.toString())
            } else{
                textEditTextCurrencyConvertor.text?.clear()
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
}




