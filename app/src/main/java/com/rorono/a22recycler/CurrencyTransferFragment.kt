package com.rorono.a22recycler

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rorono.a22recycler.databinding.FragmentCurrencyTransferBinding
import kotlin.math.floor


class CurrencyTransferFragment : Fragment(R.layout.fragment_currency_transfer) {
    private val viewModel by activityViewModels <CurrencyViewModel>()

    private var currency:Money? = null

    //private val args: CurrencyTransferFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currency = viewModel.changeCurrency
    }
    private lateinit var binding: FragmentCurrencyTransferBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCurrencyTransferBinding.inflate(layoutInflater)
        //var viewModel = ViewModelProvider(this).get(CurrencyTransferFragmentViewModel::class.java)


       // val titleToolbarTitle = args.test
        binding.toolbarCurrencyTransferFragment.title = currency?.getCode().toString()
        binding.tvFullNameCurrency.text = currency?.getTitle()
       // val textViewExchangeRate = args.test2
       // binding.textViewExchangeRateTransferFragment.text = "$textViewExchangeRate Р"
        binding.textInputLayoutCurrencyConvertor.hint = currency?.getCode()


            binding.tvExchangeRate.text = (floor(currency!!.getCurrency()*100)/100).toString() //данные передаем


       binding.etCurrencyConvertor.addTextChangedListener {
            if (binding.etCurrencyConvertor.text.toString() != "" && binding.etCurrencyConvertor.hasFocus()) {
                currency?.getCurrency()?.let {
                    convertetToRubls(it,binding.etCurrencyConvertor,binding.etTransferRubles)

                }
            } else if (binding.etCurrencyConvertor.text.isNullOrBlank()) {
                binding.etTransferRubles.text?.clear()
            }
        }
        binding.etTransferRubles.addTextChangedListener {
            if (binding.etTransferRubles.text.toString() != "" && binding.etTransferRubles.hasFocus()) {
                currency?.getCurrency()?.let {
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




