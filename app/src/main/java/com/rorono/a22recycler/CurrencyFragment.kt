package com.rorono.a22recycler

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
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
import java.lang.reflect.Field
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
        val data = binding.editTextData




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

        data.setOnClickListener {


            val datePickerDialog = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    //set to editText
                    data.setText("" + dayOfMonth + " " + month + " " + year)
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
            requireActivity().runOnUiThread {
                adapter.setItems(listMoney)
            }

        }

    }


}












