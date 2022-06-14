package com.rorono.a22recycler

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

var name: String = "2022-04-05"

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
        val materialDatePicker = MaterialDatePicker.Builder.datePicker().build()
        /*data.setOnClickListener {

            materialDatePicker.show(parentFragmentManager, "DatePicker")
            data.setOnClickListener {
                materialDatePicker.addOnPositiveButtonClickListener {
                   selection:Long?-> selection?.let {
                       val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val date = dateFormat.format(Date(it))
                    data.setText(date.toString())

                }
                }


            }
        }*/


        data?.setOnClickListener {

            val datePickerDialog = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener (){


                        view, year, month, dayOfMonth ->
                    //set to editText
                    // data.setText("$dayOfMonth $month $year")
                    //data.setText(selectedDate)
                    // до этого места работает все
                    var day: String = dayOfMonth.toString()
                    var montSmallTen: String = (month + 1).toString()
                    if (month < 10) {
                        montSmallTen = "0$montSmallTen"
                        //data.setText("0$montSmallTen") // так он увеличивает т.к. у нас же январь 0
                    }
                    if (dayOfMonth < 10) {
                        day = "0$dayOfMonth"
                    }
                    data.setText("$year-$montSmallTen-$day")
                    //Log.d("y", "$year-$montSmallTen-$day+++++++++++++++")
                    name = "2022-04-12"
                    Log.d("Test", "$name")


                   // var netv: NetworkService = NetworkService()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")


                    //data.hint = ("" + year + "-" + "0" + month + "-" + "0" + dayOfMonth)


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
        getCurrency()
    }


    fun getCurrency(){
        viewModel.getCurrency()
        viewModel.listCurrency.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            response->
            adapter.setItems(response)
            adapter.notifyDataSetChanged()
        })
    }
}











