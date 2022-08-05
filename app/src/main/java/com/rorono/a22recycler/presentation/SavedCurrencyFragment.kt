package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.R
import com.rorono.a22recycler.adapter.ChosenCurrencyAdapter
import com.rorono.a22recycler.adapter.OnItemClickChosenCurrency
import com.rorono.a22recycler.databinding.FragmentSavedCurrencyBinding
import com.rorono.a22recycler.models.remotemodels.Currency
import com.rorono.a22recycler.utils.BaseViewBindingFragment
import com.rorono.a22recycler.utils.Settings
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.util.*
import javax.inject.Inject


class SavedCurrencyFragment :
    BaseViewBindingFragment<FragmentSavedCurrencyBinding>(FragmentSavedCurrencyBinding::inflate) {

    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var viewModel: CurrencyViewModel
    private val adapterChosenCurrency = ChosenCurrencyAdapter()

    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationFavorite =
            AnimationUtils.loadAnimation(requireContext(), R.anim.aplpha_animation)
        binding.ivIconFavorite.startAnimation(animationFavorite)

        adapterChosenCurrency.setOnListener(object : OnItemClickChosenCurrency {
            override fun onItemClick(currency: Currency, position: Int) {
                val action =
                    SavedCurrencyFragmentDirections.actionSavedCurrencyFragmentToCurrencyTransferFragment(
                        currency
                    )
                findNavController().navigate(action)
            }
            override fun onItemClickDeleteFavoriteCurrency(currency: Currency, position: Int) {
                val listFavorite = mutableListOf<Currency>()
                viewModel.saveCurrencyDatabase.value?.let { listFavorite.addAll(it) }
                viewModel.deleteSaveCurrency(currency)
                listFavorite.remove(currency)
                adapterChosenCurrency.setData(listFavorite)
                adapterChosenCurrency.notifyItemRemoved(position)
            }
        })
        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]
        when (Settings.loadOrientation(requireContext())) {
            1 -> changAdapter(1)
            2 -> changAdapter(2)
        }
        viewModel.getSaveCurrencyDao()
        viewModel.getCurrencyDao()
        viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) {
            adapterChosenCurrency.setData(it)
        }
        viewModel.currencyDatabase.observe(viewLifecycleOwner) { listCurrency ->
            if (listCurrency.isNotEmpty()) {
                viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) { listFavorite ->
                    for (i in listCurrency) {
                        for (g in listFavorite) {
                            if (i.charCode == g.charCode) {
                                g.value = i.value
                            }
                        }
                    }
                }
            }
        }
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.UP
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    val position = viewHolder.bindingAdapterPosition //start position
                    val toPosition = target.bindingAdapterPosition // end position

                    Collections.swap(
                        adapterChosenCurrency.oldList,
                        position,
                        toPosition
                    )
                    adapterChosenCurrency.notifyItemMoved(position, toPosition)
                    updateData()
                    return true
                }
                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {}
            })
        mIth.attachToRecyclerView(binding.recyclerViewChosenCurrency)
    }

    private fun updateData() {
        val updateListFavoriteCurrency = mutableListOf<Currency>()
        updateListFavoriteCurrency.addAll(adapterChosenCurrency.oldList)
        viewModel.saveCurrencyDatabase.value = updateListFavoriteCurrency
        adapterChosenCurrency.setData(updateListFavoriteCurrency)
        viewModel.setSaveCurrencyDao(updateListFavoriteCurrency)
    }

    private fun changAdapter(adapterOption: Int) {
        if (adapterOption == 1) {
            viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) {
                for (i in it) {
                    i.isFavorite = 1
                }
                adapterChosenCurrency.setData(it)
            }
            with(binding) {
                recyclerViewChosenCurrency.layoutManager =
                    GridLayoutManager(requireView().context, 3)
                recyclerViewChosenCurrency.adapter = adapterChosenCurrency
            }
        } else {
            viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) {
                for (i in it) {
                    i.isFavorite = 0
                }
                adapterChosenCurrency.setData(it)
            }
            with(binding) {
                recyclerViewChosenCurrency.layoutManager = LinearLayoutManager(requireContext())
                recyclerViewChosenCurrency.adapter = adapterChosenCurrency
            }
        }
    }
}









