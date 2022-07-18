package com.rorono.a22recycler.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.R

import com.rorono.a22recycler.databinding.SaveCurrencyItemBinding
import com.rorono.a22recycler.models.Currency


class CurrenciesSaveAdapter :
    ListAdapter<Currency,CurrenciesSaveAdapter.CurrencySaveHolder>(DIFF_CALLBACK) {

    lateinit var onItemClickSaveCurrency: OnItemClickListener


    fun setFavoriteListener(listener: OnItemClickListener){
        onItemClickSaveCurrency = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(oldItem: Currency, newItem: Currency) =
                oldItem.isFavorite == newItem.isFavorite

            override fun areContentsTheSame(oldItem: Currency, newItem: Currency) =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
    inner class CurrencySaveHolder(binding: SaveCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvNameRate = binding.textViewNameRate
        private val ivFavorite = binding.ivFavorite

        fun bind(currency: Currency) {
            if (currency.isFavorite == 1) {
                ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
            itemView.setOnClickListener {
                onItemClickSaveCurrency.onItemClick(currency = currency,layoutPosition)
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