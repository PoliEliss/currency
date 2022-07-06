package com.rorono.a22recycler.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.database.CurrencyDataBase
import com.rorono.a22recycler.databinding.ChosenCurrencyItemBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.presentation.MainActivity
import com.rorono.a22recycler.presentation.SavedCurrencyFragment
import com.rorono.a22recycler.utils.Rounding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class ChosenCurrencyAdapter : ListAdapter<Currency, ChosenCurrencyAdapter.ChosenCurrencyHolder>(DiffUtil()) {

    var onItemClick: ((Currency) -> Unit)? = null
     val chosenCurrencyList = mutableListOf<Currency>()

    class DiffUtil:androidx.recyclerview.widget.DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return    oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return when {
                oldItem.fullName != newItem.fullName -> {
                    return false
                }
                oldItem.isFavorite != newItem.isFavorite -> {
                    return false
                }
                oldItem.charCode != newItem.charCode -> {
                    return false
                }
                oldItem.value != newItem.value -> {
                    return false
                }
                else -> true
            }
        }
    }



    inner class ChosenCurrencyHolder(binding: ChosenCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvNameRate = binding.textViewNameRate
        private val tvExchangeRate = binding.textViewExchangeRate
        private val ivDelete = binding.ivDelete

        fun bind(currency: Currency) {
            itemView.setOnClickListener {
             Log.d("TEST","----------------")
            }
            ivDelete.setOnClickListener {

            }

            tvNameRate.text = currency.charCode
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                tvExchangeRate.text = it
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenCurrencyHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ChosenCurrencyHolder, position: Int) {
        TODO("Not yet implemented")
    }


}
