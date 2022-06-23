package com.rorono.a22recycler

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import java.util.*


class CurrencyFragment : Fragment(R.layout.fragment_currency) {
    private var adapter = CurrencyAdapter()
    private val viewModel by activityViewModels<CurrencyViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCurrencyBinding.bind(view)
        val recyclerView = binding.recyclerView
        val date: EditText = binding.editTextData
        date.hint = viewModel.getData()
        viewModel.date.observe(requireActivity()) {
            viewModel.getCurrency(it)
            date.hint = it
        }

        val (year, month, day) = createCalendar()

        date.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                view.context,
                { view, year, month, dayOfMonth ->
                    val month = (month + 1)

                    var day: String = dayOfMonth.toString()
                    var montSmallTen: String = month.toString()

                    if (month < 10) {
                        montSmallTen = "0$month"
                    }
                    if (dayOfMonth < 10) {
                        day = "0$dayOfMonth"
                    }
                    date.hint = "$year-$montSmallTen-$day"
                   viewModel.getCurrency(date = date.hint.toString())
                    viewModel.date.value = date.hint.toString()
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

        viewModel.getCurrency(date = date.hint.toString())
        viewModel.messageError.observe(viewLifecycleOwner) { error ->
            getToastError(error)
        }
        viewModel.listCurrency.observe(viewLifecycleOwner) { response ->
            adapter.setItems(response)
            adapter.notifyDataSetChanged()
            adapter.onItemClick = {
                val action =
                    CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyTransferFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun createCalendar(): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return Triple(year, month, day)
    }

    private fun getToastError(error: String) {
          Toast.makeText(requireActivity(),error,Toast.LENGTH_LONG).show()
    }
}











