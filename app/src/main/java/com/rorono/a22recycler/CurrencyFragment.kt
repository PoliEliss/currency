package com.rorono.a22recycler

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CurrencyFragment : Fragment(R.layout.fragment_currency) {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCurrencyBinding.bind(view)
        val recyclerView = binding.recyclerView
        val data = binding.editTextData
        data.hint = getData()
        val adapter = CurrencyAdapter()
        adapter.onItemClick = {currencyList -> Toast.makeText(view.context, currencyList.name,Toast.LENGTH_LONG).show() }



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
                    data.setText("" + dayOfMonth + month + year)
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
        //val currency = Currency("jj", "iii")
        val currencyList = ArrayList<Currency>()
        for (i in 1 ..120){
            currencyList.add(Currency("AUD","55.00Р"))
        }
        adapter.setItems(currencyList)
    }


    companion object {
        @JvmStatic
        fun newInstance() = CurrencyFragment()
    }


    private fun getData(): String {
        val currentDate = Date()

        val dataFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        return dataFormat.format(currentDate)
    }


}