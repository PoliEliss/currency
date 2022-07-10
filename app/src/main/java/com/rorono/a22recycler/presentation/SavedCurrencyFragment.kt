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
    val listChosenCurrency = mutableListOf<Currency>()
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

        // viewModel.getSaveCurrencyDao()

        /* viewModel.currencyDatabase.observe(viewLifecycleOwner) { listCurrency ->
             viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { listDataBase ->
                 for (i in listCurrency) {
                     for (g in listDataBase) {
                         if (g.fullName == i.fullName) {
                             i.isFavorite = 1
                         }
                     }
                 }
                 adapter.setData(listCurrency)
                 adapterChosenCurrency.submitList(listDataBase)
             }
             adapter.setData(viewModel.currencyDatabase.value!!)
         }*/


        /* viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { list -> //Это не работает в том случае если
             adapterChosenCurrency.setItems(list)
             adapterChosenCurrency.notifyDataSetChanged()


         }*/

        viewModel.getCurrencyDao()


        viewModel.currencyDatabase.observe(viewLifecycleOwner) {
                Log.d("TEST16"," viewModel.currencyDatabase.observe ${it}")
            it.forEach { currency ->
                if (currency.isFavorite == 1) {
                    listChosenCurrency.add(currency)
                }
            }
            adapterChosenCurrency.submitList(listChosenCurrency)
            adapter.submitList(it)
        }

        adapter.onItemClick = {

            if (it.isFavorite == 0) {
                Toast.makeText(requireContext(), " ${it.charCode} \n  ${it.isFavorite}", Toast.LENGTH_LONG).show()

                viewModel.currencyDatabase.value!!.forEach { currency ->
                    if (it.fullName == currency.fullName) {
                        currency.isFavorite = 1
                        viewModel.setCurrencyDao(
                            viewModel.currencyDatabase.value!!,
                            viewModel.getDate()
                        )
                        //listChosenCurrency.add(currency)
                        Log.d("TEST16","listChosenCurrency!!! ${listChosenCurrency}")
                       // adapterChosenCurrency.submitList(listChosenCurrency)
                      //  adapter.submitList(viewModel.currencyDatabase.value!!)

                    }
                    viewModel.getCurrencyDao()
                }
                /* for (i in viewModel.currencyDatabase.value!!) {
                     listСurrencyTest.add(i)
                     if (i.fullName == it.fullName) {
                         listСurrencyTest.remove(i)
                         i.isFavorite = 1
                         listСurrencyTest.add(i)
                         list.add(i)
                     }
                 }*/


            } /*else {
                Toast.makeText(requireContext(), "DELETE", Toast.LENGTH_LONG).show()
                val listcurrencyTest = mutableListOf<Currency>()
                for (i in viewModel.currencyDatabase.value!!) {
                    listcurrencyTest.add(i)
                    if (i.fullName == it.fullName) {
                        listcurrencyTest.remove(i)
                        i.isFavorite = 0
                        listcurrencyTest.add(i)
                    }
                }
                adapter.submitList(listcurrencyTest)
                viewModel.setCurrencyDao(listcurrencyTest, viewModel.getDate())
                viewModel.getCurrencyDao()
            }*/
        }


        // если я буду сохранять этот список валют в БД то и значение валюты будет так скажем за день, когда я ее добавила
        // а еще наверное при добавление в бд и тот ресайкл из этого удалять значение или помечать его сердечком, чтобы нельзя было второй раз добавить

        //мне здесь нужно в бз заводить их.
        //а еще проблема в том, что мне нужно как-то записывать false или tru по поводу нажатия
        // но смена картинки идет в адаптере.
        //может как-то передавать значение в адаптер и в зависимости от этого закрашивать сердечко
        // ведь у меня нет доступа напрямую к одной айтим из фрагмента
        // а еще в тестовое отображение нужно добавить карзинку ( чтобы удалять элемент)

/* viewModel.saveCurrencyDatabase.observe( // тут понятно, просто нет изменений т.к. лист пустой поэтому и последний не удаляется
     viewLifecycleOwner,
     androidx.lifecycle.Observer {
         Log.d("TEST8","ВТОРОЙ АДАПТЕР ${it}")
         adapterChosenCurrency.submitList(it)
     })*/


        /* val mIth = ItemTouchHelper(
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
                        // adapterChosenCurrency.chosenCurrencyList,
                         position,
                         toPosition
                     )
                     adapterChosenCurrency.notifyItemMoved(position, toPosition)
                     return true
                 }
 */
        /*override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            val currency =
               // adapterChosenCurrency.chosenCurrencyList.removeAt(position) //Это уже работать не будет
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

        }*/
        // })


/*  val itemTouchHelper = ItemTouchHelper(adapterChosenCurrency.getSimpleCallback())
itemTouchHelper.attachToRecyclerView(binding.recyclerViewChosenCurrency)*/


        //  mIth.attachToRecyclerView(binding.recyclerViewChosenCurrency)
    }
}




