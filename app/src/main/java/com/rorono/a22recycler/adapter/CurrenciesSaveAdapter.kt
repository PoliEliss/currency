package com.rorono.a22recycler.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.R

import com.rorono.a22recycler.databinding.SaveCurrencyItemBinding
import com.rorono.a22recycler.models.Currency


class CurrenciesSaveAdapter :
    ListAdapter<Currency, CurrenciesSaveAdapter.CurrencySaveHolder>(DiffUtil()) {
    var onItemClick: ((Currency) -> Unit)? = null

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


    inner class CurrencySaveHolder(binding: SaveCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvNameRate = binding.textViewNameRate
        private val ivFavorite = binding.ivFavorite


        fun bind(currency: Currency) {
            Log.d("TEST9","IsFavorite")
            if (currency.isFavorite == 1) {
                ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
            itemView.setOnClickListener {
                onItemClick?.let { view ->
                    view(currency)
                   // ivFavorite.setImageResource(R.drawable.ic_favorite)

                   /* if (currency.isFavorite == 1) {
                        Log.d("TEST2", "тыкбтык1")
                        currency.isFavorite == 0
                        ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                    } else {
                        Log.d("TEST2", "тыкбтык0")
                        ivFavorite.setImageResource(R.drawable.ic_favorite)
                    }*/
                }

            }
            tvNameRate.text = currency.charCode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencySaveHolder {
        val binding =
            SaveCurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencySaveHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencySaveHolder, position: Int) {
     holder.bind(getItem(position))
    }
}