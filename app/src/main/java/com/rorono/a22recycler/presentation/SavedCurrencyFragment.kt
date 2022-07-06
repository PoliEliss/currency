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
        viewModel.getSaveCurrencyDao()

       viewModel.listCurrency.observe(viewLifecycleOwner) { listCurrency ->
           viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { listDataBase ->

                Log.d("TEST3", "11listDataBase ${listDataBase}")
                for (i in listDataBase) {
                    Log.d("TEST",")))))))))")
                    for (g in listCurrency) {
                        if (g.fullName == i.fullName) {
                            g.isFavorite = 1
                        }
                    }
                    adapter.submitList(listCurrency)
                }
                Log.d("TEST3", "adapterSubmit ${listCurrency}")


            }
            adapter.submitList(listCurrency)
        }



       /* viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { list -> //Это не работает в том случае если
            adapterChosenCurrency.setItems(list)
            adapterChosenCurrency.notifyDataSetChanged()


        }*/



        adapter.onItemClick = {
            val list = mutableListOf<Currency>()
            Log.d("TEST", "Click")
            list.add(it)
            if (it.isFavorite == 0) {
                Toast.makeText(requireContext(), "hhhhh", Toast.LENGTH_LONG).show()
                Log.d("TEST3", "isFavorite0")
                for ( i in viewModel.listCurrency.value!!){
                    if (i.fullName == it.fullName){
                        i.isFavorite = 1
                        Log.d("TEST3","пробуем ${i.fullName}")
                    }
                }
                viewModel.setSaveCurrencyDao(list)
                viewModel.getSaveCurrencyDao()
            } else {
                Log.d("TEST3", "isFavorite1")
                Toast.makeText(requireContext(), "DELETE CURRENCY", Toast.LENGTH_LONG).show()
                viewModel.deleteSaveCurrency(it)

                for ( i in viewModel.listCurrency.value!!){
                    if (i.fullName == it.fullName){
                        i.isFavorite = 0
                        Log.d("TEST3","пробуем ${i.fullName}")
                    }
                }
                viewModel.getSaveCurrencyDao()
            }


            // если я буду сохранять этот список валют в БД то и значение валюты будет так скажем за день, когда я ее добавила
            // а еще наверное при добавление в бд и тот ресайкл из этого удалять значение или помечать его сердечком, чтобы нельзя было второй раз добавить

            //мне здесь нужно в бз заводить их.
            //а еще проблема в том, что мне нужно как-то записывать false или tru по поводу нажатия
            // но смена картинки идет в адаптере.
            //может как-то передавать значение в адаптер и в зависимости от этого закрашивать сердечко
            // ведь у меня нет доступа напрямую к одной айтим из фрагмента
            // а еще в тестовое отображение нужно добавить карзинку ( чтобы удалять элемент)
        }
        viewModel.saveCurrencyDatabase.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                Log.d("TEST3","ВТОРОЙ АДАПТЕР ${it}")
                adapterChosenCurrency.setItems(it)
                adapterChosenCurrency.notifyDataSetChanged()

            })




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
                    val currency = adapterChosenCurrency.chosenCurrencyList.removeAt(position)
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


        mIth.attachToRecyclerView(binding.recyclerViewChosenCurrency)

    }

}
