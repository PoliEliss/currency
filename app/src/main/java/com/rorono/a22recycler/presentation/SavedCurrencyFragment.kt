package com.rorono.a22recycler.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.adapter.ChosenCurrencyAdapter
import com.rorono.a22recycler.adapter.CurrenciesSaveAdapter
import com.rorono.a22recycler.database.SaveCurrencyItem
import com.rorono.a22recycler.databinding.FragmentSavedCurrencyBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.util.*


class SavedCurrencyFragment :
    BaseViewBindingFragment<FragmentSavedCurrencyBinding>(FragmentSavedCurrencyBinding::inflate) {
    private val adapter = CurrenciesSaveAdapter()
    private val adapterChosenCurrency = ChosenCurrencyAdapter()

    private val viewModel by activityViewModels<CurrencyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
        }
        binding.apply {
            recyclerViewSaveCurrency.layoutManager = GridLayoutManager(view.context, 3)
            recyclerViewSaveCurrency.adapter = adapter
        }
        binding.apply {
            recyclerViewChosenCurrency.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewChosenCurrency.adapter = adapterChosenCurrency
        }

        viewModel.getCurrencyDao()
        viewModel.getSaveCurrencyDao()

        viewModel.currencyDatabase.observe(viewLifecycleOwner) { listCurrency ->
            viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { listDataBase ->
                for (i in listCurrency) {
                    for (g in listDataBase) {
                        if (g.fullName == i.fullName) {
                            i.isFavorite = 1
                        }
                    }
                    adapter.setData(listCurrency)
                    adapterChosenCurrency.submitList(listDataBase)
                }
            }
            adapter.setData(listCurrency)
        }


        adapter.onItemClick = {
            val g = mutableListOf<Currency>()

            val list = mutableListOf<Currency>()
            list.add(it)
            if (it.isFavorite == 0) {
                Toast.makeText(requireContext(), "ADD", Toast.LENGTH_LONG).show()

                val listcurrencyTest = mutableListOf<Currency>()
                for (i in viewModel.currencyDatabase.value!!) {
                    listcurrencyTest.add(i)
                    if (i.fullName == it.fullName) {
                        listcurrencyTest.remove(i)
                        i.isFavorite = 1
                        listcurrencyTest.add(i)
                    }
                    adapter.setData(listcurrencyTest)

                }

                viewModel.setSaveCurrencyDao(list)
                viewModel.getSaveCurrencyDao()
                 adapterChosenCurrency.submitList(list)

            } else {
                //
                Toast.makeText(requireContext(), "DELETE", Toast.LENGTH_LONG).show()
                val listcurrencyTest = mutableListOf<Currency>()
                for (i in viewModel.currencyDatabase.value!!) {
                    listcurrencyTest.add(i)
                    if (i.fullName == it.fullName) {
                        listcurrencyTest.remove(i)
                        i.isFavorite = 0
                        listcurrencyTest.add(i)
                    }
                    adapter.setData(listcurrencyTest)
                }
                Log.d("TEST13", "list DELETE ${listcurrencyTest}")

                viewModel.deleteSaveCurrency(it)
                        list.remove(it)
                adapterChosenCurrency.submitList(list)
                viewModel.getSaveCurrencyDao()
                }

            }
        }

        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    val position = viewHolder.bindingAdapterPosition //start position
                    val toPosition = target.bindingAdapterPosition // end position
                    Collections.swap(
                        adapterChosenCurrency.chosenCurrencyList,
                        position,
                        toPosition
                    )
                    adapterChosenCurrency.notifyItemMoved(position, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    val currency =
                        adapterChosenCurrency.chosenCurrencyList.removeAt(position) //Это уже работать не будет
                    adapterChosenCurrency.notifyItemRemoved(position)
                    Log.d("TEST", "currency!!! ${currency}")
                    val saveCurrency = SaveCurrencyItem(
                        fullName = currency.fullName,
                        charCode = currency.charCode,
                        value = currency.value
                    )
                    Log.d("TEST", "saveCurrency ${saveCurrency}")
                    //viewModel.deleteSaveCurrency(saveCurrency) // вот только он не удаляет, во ViewModel я попадаю
                    // одной валюты из бд но опять же тут по позиции идет удаление а у меня по id
                    // должно быть

                }
            })


/*  val itemTouchHelper = ItemTouchHelper(adapterChosenCurrency.getSimpleCallback())
itemTouchHelper.attachToRecyclerView(binding.recyclerViewChosenCurrency)*/


    //    mIth.attachToRecyclerView(binding.recyclerViewChosenCurrency)
    }





