package com.rorono.a22recycler.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rorono.a22recycler.BaseViewBindingFragment
import com.rorono.a22recycler.MainViewModelFactory
import com.rorono.a22recycler.MyApplication
import com.rorono.a22recycler.adapter.ChosenCurrencyAdapter
import com.rorono.a22recycler.adapter.CurrenciesSaveAdapter
import com.rorono.a22recycler.adapter.OnItemClickChosenCurrency
import com.rorono.a22recycler.adapter.OnItemClickListener
import com.rorono.a22recycler.database.CurrencyDao
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.database.SaveCurrencyItem
import com.rorono.a22recycler.databinding.FragmentSavedCurrencyBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.network.RetrofitInstance
import com.rorono.a22recycler.repository.Repository
import com.rorono.a22recycler.settings.Settings
import com.rorono.a22recycler.viewmodel.CurrencyViewModel
import java.io.File
import java.util.*
import javax.inject.Inject


class SavedCurrencyFragment :
    BaseViewBindingFragment<FragmentSavedCurrencyBinding>(FragmentSavedCurrencyBinding::inflate) {

    @Inject
    lateinit var factory: MainViewModelFactory
    lateinit var viewModel: CurrencyViewModel

    //private val adapter = CurrenciesSaveAdapter()
    // private var behavior: BottomSheetBehavior<*>? = null

    private val adapterChosenCurrency = ChosenCurrencyAdapter()


    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                Log.d("TEST15","currency ${viewModel.saveCurrencyDatabase.value}")
                Log.d("TEST15","ListFavprite ${listFavorite}")

                adapterChosenCurrency.setData(listFavorite)
                adapterChosenCurrency.notifyItemRemoved(position)
                viewModel.getSaveCurrencyDao()

            }
        })
        viewModel = ViewModelProvider(this, factory)[CurrencyViewModel::class.java]

        when (Settings.loadOrientation(requireContext())) {
            1 -> changAdapter(1)
            2 -> changAdapter(2)
        }




        viewModel.getSaveCurrencyDao()

               //
        viewModel.saveCurrencyDatabase.observe(viewLifecycleOwner) {
            Log.d("TEST15","addDataBaseCurrency ${it}")
            Log.d("TEST15","viewModel ${viewModel.saveCurrencyDatabase.value}")
            adapterChosenCurrency.setData(it)

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
                    return true
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                }
            })

        mIth.attachToRecyclerView(binding.recyclerViewChosenCurrency)
    }

    fun changAdapter(change: Int) {
        (binding.recyclerViewChosenCurrency.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false

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








