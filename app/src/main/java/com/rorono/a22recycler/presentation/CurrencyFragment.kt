package com.rorono.a22recycler.presentation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.rorono.a22recycler.*
import com.rorono.a22recycler.adapter.CurrencyAdapter
import com.rorono.a22recycler.databinding.FragmentCurrencyBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.time.Duration
import java.util.*


class CurrencyFragment :
    BaseViewBindingFragment<FragmentCurrencyBinding>(FragmentCurrencyBinding::inflate) {
    private var adapter = CurrencyAdapter()
    private val viewModel by activityViewModels<CurrencyViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date: EditText = binding.etDate
        date.hint = viewModel.getDate()

        binding.swipeRefreshLayout.setOnRefreshListener {
            getData(NetManager(requireContext()).isOnline(), date = viewModel.date.value.toString())
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.date.observe(requireActivity()) {
            Log.d("TEST", "date ${it}")
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
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            datePickerDialog.datePicker.maxDate = today
            datePickerDialog.show()
        }

        binding.apply {
            recyclerView.layoutManager = GridLayoutManager(view.context, 3)
            recyclerView.adapter = adapter
        }


        viewModel.messageError.observe(viewLifecycleOwner) { error ->
            getToastError(error)
        }

        viewModel.listCurrency.observe(viewLifecycleOwner) { response ->

            adapter.submitList(response)
            adapter.onItemClick = {
                val action =
                    CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyTransferFragment(
                        it
                    )
                findNavController().navigate(action)
            }
            searchCurrency(response)
        }
        viewModel.currencyDatabase.observe(viewLifecycleOwner) { list ->
            Log.d("TEST","from ${list}")
            val textAttention =
                getString(R.string.attention_error_get_data) + " ${viewModel.date.value}"
            binding.tvAttention.text = textAttention
            adapter.submitList(list)
            adapter.onItemClick = {
                val action =
                    CurrencyFragmentDirections.actionCurrencyFragmentToCurrencyTransferFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }
        binding.ivSearch.setOnClickListener {
            createAnimationOpenSearch()
        }
        binding.ivCancelSearch.setOnClickListener {
            cancelSearch()
        }
    }

    private fun searchCurrency(response: List<Currency>) {
        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                val n = mutableListOf<Currency>()
                binding.search.clearFocus()
                if (newText!!.isNotEmpty()) {
                    for (i in response) {
                        if (i.charCode.contains(newText.uppercase())) {
                            n.add(i)
                            adapter.submitList(n)

                        }
                    }
                } else {
                    adapter.submitList(response)
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getData(NetManager(context = requireContext()).isOnline(), viewModel.date.value.toString())
    }

    private fun createAnimationOpenSearch() {
        val objectAnimator = ObjectAnimator.ofFloat(binding.search, "translationX", -510f)
        objectAnimator.duration = 2000
        objectAnimator.start()
        objectAnimator.repeatCount
        binding.tvTitleToolbar.visibility = View.GONE
        binding.ivSearch.visibility = View.GONE
        binding.ivCancelSearch.visibility = View.VISIBLE
        binding.search.visibility = View.VISIBLE
    }

    private fun cancelSearch() {
        val slide = Slide()
        slide.slideEdge = Gravity.END
        TransitionManager.beginDelayedTransition(binding.contentLayout, slide)
        binding.tvTitleToolbar.visibility = View.VISIBLE
        binding.ivSearch.visibility = View.VISIBLE
        binding.ivCancelSearch.visibility = View.GONE
        binding.search.visibility = View.GONE
        val objectAnimator = ObjectAnimator.ofFloat(binding.search, "translationX", 510f)
        objectAnimator.duration = 1000
        objectAnimator.start()
    }

    private fun getData(networkConnection: Boolean, date: String) {
        if (networkConnection) {
            binding.tvAttention.visibility = View.GONE
            viewModel.getCurrency(date)

        } else {
            viewModel.getCurrencyDao()
            Log.d("TEST","viewModel ${viewModel.currencyDatabase.value}")
            binding.tvAttention.visibility = View.VISIBLE
        }
    }

    private fun getToastError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}

private fun createCalendar(): Triple<Int, Int, Int> {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return Triple(year, month, day)
}
















