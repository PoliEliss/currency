package com.rorono.a22recycler

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import kotlin.math.floor


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {

    private val args: CurrencyTransferFragmentArgs by navArgs()

    private lateinit var binding: FragmentCurrencyTransferBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            if (textEditTextCurrencyConvertor.text.toString() != "" && textEditTextCurrencyConvertor.hasFocus()) {
                val valuta =
                    textEditTextCurrencyConvertor.text.toString().toDouble() * args.test2.toDouble()
                val result = floor(valuta * 1000) / 1000
                textEditTextTransferRubl.setText(result.toString())
            } else if (textEditTextCurrencyConvertor.text.isNullOrBlank()) {
                textEditTextTransferRubl.text?.clear()
            }
        }
        textEditTextTransferRubl.addTextChangedListener {
            if (textEditTextTransferRubl.text.toString() != "" && textEditTextTransferRubl.hasFocus()) {
                val valuta =
                    textEditTextTransferRubl.text.toString().toDouble() / args.test2.toDouble()
                val result = floor(valuta * 1000) / 1000
                textEditTextCurrencyConvertor.setText(result.toString())

            } else if (textEditTextTransferRubl.text.isNullOrBlank()) {
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




