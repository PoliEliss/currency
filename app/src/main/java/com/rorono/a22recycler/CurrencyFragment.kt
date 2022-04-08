package com.rorono.a22recycler

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import com.rorono.a22recycler.models.Valute
import com.rorono.a22recycler.network.ApiService
import com.rorono.a22recycler.screens.AMD
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String.format
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.internal.Reflection


class CurrencyFragment : Fragment(R.layout.fragment_currency) {
    var adapter = CurrencyAdapter()
    private val viewModel by activityViewModels<CurrencyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCurrencyBinding.bind(view)
        val recyclerView = binding.recyclerView
        val data: EditText = binding.editTextData
        data.hint = viewModel.getData()


        adapter.onItemClick = {
            viewModel.changeCurrency = it

            val action =
                com.rorono.a22recycler.CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyTransferFragment()
            findNavController().navigate(action)
        }


        //calendar
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        data?.setOnClickListener {


            val datePickerDialog = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    //set to editText
                    // data.setText("$dayOfMonth $month $year")


                    //data.setText(selectedDate)
                    // до этого места работает все
                    if (month < 10 ) {
                        val montSmallTen = month + 1
                        data.setText("$montSmallTen") // так он увеличивает т.к. у нас же январь 0
                    }

                    data.hint = ("" + year + "-" + "0" + month + "-" + "0" + dayOfMonth)


                    //data.setText("$year-${month+1}-$dayOfMonth")


                },
                year,
                month,
                day
            )
            datePickerDialog.show()


        }




        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(view.context, 3)
            recyclerView.adapter = adapter
        }
        updateList()

    }

    fun updateList() {
        viewModel.getCbrEntity {
            val listMoney: MutableList<Money> = mutableListOf()
            listMoney.add(it.Valute.AUD)
            listMoney.add(it.Valute.AZN)
            listMoney.add(it.Valute.BGN)
            listMoney.add(it.Valute.BRL)
            listMoney.add(it.Valute.BYN)
            listMoney.add(it.Valute.CAD)
            listMoney.add(it.Valute.CHF)
            listMoney.add(it.Valute.CNY)
            listMoney.add(it.Valute.CZK)
            listMoney.add(it.Valute.DKK)
            listMoney.add(it.Valute.EUR)
            listMoney.add(it.Valute.GBP)
            listMoney.add(it.Valute.HKD)
            listMoney.add(it.Valute.HUF)
            listMoney.add(it.Valute.INR)
            listMoney.add(it.Valute.JPY)
            listMoney.add(it.Valute.KGS)
            listMoney.add(it.Valute.KRW)
            listMoney.add(it.Valute.KZT)
            listMoney.add(it.Valute.MDL)
            listMoney.add(it.Valute.NOK)
            listMoney.add(it.Valute.PLN)
            listMoney.add(it.Valute.RON)
            listMoney.add(it.Valute.SEK)
            requireActivity().runOnUiThread {
                adapter.setItems(listMoney)
            }

        }

    }


}












