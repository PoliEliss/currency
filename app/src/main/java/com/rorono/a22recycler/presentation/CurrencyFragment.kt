package com.rorono.a22recycler.presentation

import android.annotation.SuppressLint
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
import com.rorono.a22recycler.adapter.CurrencyAdapter
import com.rorono.a22recycler.CurrencyViewModel
import com.rorono.a22recycler.NetworkConnection
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import java.util.*


class CurrencyFragment : Fragment(R.layout.fragment_currency) {
    private var adapter = CurrencyAdapter()
    private val viewModel by activityViewModels<CurrencyViewModel>() //исправить


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCurrencyBinding.bind(view)
        val recyclerView = binding.recyclerView
        val date: EditText = binding.etDate
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

        viewModel.messageError.observe(viewLifecycleOwner) { error ->
            getToastError(error)
        }



        val networkConnection = NetworkConnection(requireActivity())
        networkConnection.observe(viewLifecycleOwner, androidx.lifecycle.Observer { isConnected ->
            if (isConnected) {
                Toast.makeText(requireActivity(), "Интернет есть", Toast.LENGTH_LONG).show()
                viewModel.getCurrency(date.hint.toString())
                binding.tvAttention.visibility = View.GONE
                viewModel.listCurrency.observe(viewLifecycleOwner) { response ->
                    adapter.setItems(response)
                    adapter.notifyDataSetChanged()
                    adapter.onItemClick = {
                        val action =
                            CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyTransferFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                }
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), "Интернета Нет", Toast.LENGTH_LONG).show()
                viewModel.getCurrencyDao()
                viewModel.listRoom.observe(viewLifecycleOwner) { list ->
                    binding.tvAttention.visibility = View.VISIBLE
                    val textAttention =
                        getString(R.string.attention_error_get_data) + " ${viewModel.date.value}"
                    binding.tvAttention.text = textAttention
                    adapter.setItems(list)
                    adapter.notifyDataSetChanged()
                    adapter.onItemClick = {
                        val action =
                            CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyTransferFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        })
    }


    private fun createCalendar(): Triple<Int, Int, Int> {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return Triple(year, month, day)
    }

    private fun getToastError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show()
    }
}











