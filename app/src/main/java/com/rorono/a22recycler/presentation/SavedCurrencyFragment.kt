package com.rorono.a22recycler.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.R
import com.rorono.a22recycler.adapter.ChosenCurrencyAdapter
import com.rorono.a22recycler.adapter.CurrenciesSaveAdapter
import com.rorono.a22recycler.adapter.CurrencyListener
import com.rorono.a22recycler.databinding.FragmentSavedCurrencyBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.viewmodel.CurrencyViewModel


class SavedCurrencyFragment :
    BaseViewBindingFragment<FragmentSavedCurrencyBinding>(FragmentSavedCurrencyBinding::inflate) {
    private lateinit var adapter: CurrenciesSaveAdapter
    private val adapterChosenCurrency = ChosenCurrencyAdapter()
    private val viewModel by activityViewModels<CurrencyViewModel>()
    private var behavior: BottomSheetBehavior<*>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior = BottomSheetBehavior.from(binding.include.bottomSheet)
        behavior!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                binding.fab.visibility = when(newState){
                    4->View.VISIBLE
                    else -> View.GONE
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        binding.fab.setOnClickListener { behavior!!.state = BottomSheetBehavior.STATE_EXPANDED }
        adapter = CurrenciesSaveAdapter(object : CurrencyListener {
            override fun onClickAddFavorite(currency: Currency) {
               Log.d("TEST111","currencylist adapter ${adapter.currentList.filter { it1->it1.isFavorite==1 }}")
                if (currency.isFavorite == 0) {
                    Toast.makeText(requireContext(),
                        " ${currency.charCode} \n  ${currency.isFavorite}",
                        Toast.LENGTH_LONG).show()
                    val listTemp = viewModel.currencyDatabase.value!!
                    listTemp.forEach { currencyDatabase ->
                        if (currencyDatabase == currency) currencyDatabase.isFavorite =  1
                      //
                    }
                    viewModel.updateCurrencyDatabase(listTemp)
                    Log.d("TEST111","currency.isFavorite == 0 setCurrencyDao")
                    viewModel.setCurrencyDao(listTemp)
                    adapter.submitList(listTemp)
                }
          /*      if (currency.isFavorite == 1) {
                    Toast.makeText(requireContext(),
                        " ${currency.charCode} \n  ${currency.isFavorite}",
                        Toast.LENGTH_LONG).show()
                    val listTemp = viewModel.currencyDatabase.value!!
                    listTemp.forEach { currencyDatabase ->
                        if (currencyDatabase == currency) currencyDatabase.isFavorite =  0
                        //  viewModel.updateCurrencyDatabase(listTemp)
                    }
                    viewModel.updateCurrencyDatabase(listTemp)
                    Log.d("TEST111","currency.isFavorite == 1 setCurrencyDao")
                    viewModel.setCurrencyDao(listTemp)
                    adapter.submitList(listTemp)
                }*/
            }

            override fun onClickRemoveFavorite(currency: Currency) = Unit

        })
        binding.include.recyclerViewSaveCurrency.adapter = adapter
        binding.recyclerViewChosenCurrency.adapter = adapterChosenCurrency

        viewModel.getCurrencyDao()
        viewModel.currencyDatabase.observe(viewLifecycleOwner) { currencyList ->
      //      Log.d("TEST111","observe ${currencyList.filter { it1->it1.isFavorite==1 }}")
            val list = adapterChosenCurrency.currentList.toMutableList()
            currencyList.forEach { currency ->
                if (currency.isFavorite == 1) {
                    list.add(currency)
                }
            }
            adapterChosenCurrency.submitList(list.toSet().toList())
            adapter.submitList(currencyList)
            adapter.notifyDataSetChanged()
        }
    }
}




