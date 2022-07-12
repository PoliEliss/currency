package com.rorono.a22recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.CurrencyItemBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.utils.Rounding

class CurrencyAdapter(private val onItemClickListener: OnItemClickListener) :
    ListAdapter<Currency, CurrencyAdapter.CurrencyHolder>(
        DiffUtil()
    ) {
    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
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


    inner class CurrencyHolder(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewNameRate = binding.textViewNameRate
        private val textViewExchangeRate = binding.textViewExchangeRate
        private val ivFavorite = binding.ivFavorite

        fun bind(currency: Currency) {
            if (currency.isFavorite == 1) {
                ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(currency = currency)
            }
            textViewNameRate.text = currency.charCode

            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                textViewExchangeRate.text = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(getItem(position))
    }

}