package com.rorono.a22recycler.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.adapter.ChosenCurrencyAdapter
import com.rorono.a22recycler.adapter.CurrenciesSaveAdapter
import com.rorono.a22recycler.adapter.OnItemClickChosenCurrency
import com.rorono.a22recycler.database.SaveCurrencyItem
import com.rorono.a22recycler.databinding.FragmentSavedCurrencyBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.settings.Settings
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.util.*


class SavedCurrencyFragment :
    BaseViewBindingFragment<FragmentSavedCurrencyBinding>(FragmentSavedCurrencyBinding::inflate) {
    private val adapter = CurrenciesSaveAdapter()
    private var behavior: BottomSheetBehavior<*>? = null
    private val adapterChosenCurrency = ChosenCurrencyAdapter(object : OnItemClickChosenCurrency {
        override fun onItemClick(currency: Currency) {
            val action = SavedCurrencyFragmentDirections.actionSavedCurrencyFragmentToCurrencyTransferFragment(currency)
            findNavController().navigate(action)
        }
//Что сейчас тут круто получилось. Из-за того, что viewModel.currencyDatabase обновляется каждый раз как новый день
        // то при добавление  в избранное у меня будут последние данные в бд записаны на текущей день и тем самым
        //мне не нужно заморачивать с тем как передавать значение!!!
        //Неее мне еще нужно будет обновлять saveCurrencyDatabase т.к он то сохранит, обновится viewModel.currencyDatabaseб
        // а saveCurrencyDatabase неттт.....ну ладно

    })
    private val viewModel by activityViewModels<CurrencyViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        behavior = BottomSheetBehavior.from(binding.include.bottomSheet)
        behavior!!.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                binding.fab.visibility = when(newState){
                   BottomSheetBehavior.STATE_COLLAPSED->View.VISIBLE
                    else -> View.GONE
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        })
        binding.fab.setOnClickListener {
            behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
        when (Settings.loadOrientation(requireContext())) {
            1 -> changAdapter(1)
            2 -> changAdapter(2)
        }
        binding.include.recyclerViewSaveCurrency.adapter = adapter

        /*binding.apply {
            recyclerViewSaveCurrency.layoutManager = GridLayoutManager(view.context, 3)
            recyclerViewSaveCurrency.adapter = adapter
        }*/
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
            val listFavoriteCurrency = mutableListOf<Currency>()
            listFavoriteCurrency.add(it)
            if (it.isFavorite == 0) {
                Toast.makeText(requireContext(), "ADD", Toast.LENGTH_LONG).show()
                val listCurrency = mutableListOf<Currency>()
                for (i in viewModel.currencyDatabase.value!!) {
                    listCurrency.add(i)
                    if (i.fullName == it.fullName) {
                        listCurrency.remove(i)
                        i.isFavorite = 1
                        listCurrency.add(i)
                    }
                    adapter.setData(listCurrency)

                }
                viewModel.setSaveCurrencyDao(listFavoriteCurrency)
                viewModel.getSaveCurrencyDao()
                adapterChosenCurrency.submitList(listFavoriteCurrency)

            } else {
                Toast.makeText(requireContext(), "DELETE", Toast.LENGTH_LONG).show()
                val listCurrency = mutableListOf<Currency>()
                for (i in viewModel.currencyDatabase.value!!) {
                    listCurrency.add(i)
                    if (i.fullName == it.fullName) {
                        listCurrency.remove(i)
                        i.isFavorite = 0
                        listCurrency.add(i)
                    }
                    adapter.setData(listCurrency)
                }
                viewModel.deleteSaveCurrency(it)
                listFavoriteCurrency.remove(it)
                adapterChosenCurrency.submitList(listFavoriteCurrency)
                viewModel.getSaveCurrencyDao()
            }

        }
    }

    /*val mIth = ItemTouchHelper(
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
        })*/


/*  val itemTouchHelper = ItemTouchHelper(adapterChosenCurrency.getSimpleCallback())
itemTouchHelper.attachToRecyclerView(binding.recyclerViewChosenCurrency)*/


    //    mIth.attachToRecyclerView(binding.recyclerViewChosenCurrency)

    fun changAdapter(change: Int) {
        if (change == 1) {
            binding.apply {
                recyclerViewChosenCurrency.layoutManager = LinearLayoutManager(requireContext())
                recyclerViewChosenCurrency.adapter = adapterChosenCurrency
            }
        } else {
            binding.apply {
                recyclerViewChosenCurrency.layoutManager =
                    GridLayoutManager(requireView().context, 3)
                recyclerViewChosenCurrency.adapter = adapterChosenCurrency
            }
        }
    }
}





