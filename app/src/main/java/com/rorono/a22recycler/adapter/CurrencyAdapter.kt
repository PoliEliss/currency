package com.rorono.a22recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.ItemCurrencyBinding
import com.rorono.a22recycler.models.remotemodels.Currency
import com.rorono.a22recycler.utils.Rounding

class CurrencyAdapter :
    ListAdapter<Currency, CurrencyAdapter.CurrencyHolder>(
        DiffUtil()
    ) {

    lateinit var onItemClickListener: OnItemClickListener

    fun setOnListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    inner class CurrencyHolder(binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewNameRate = binding.textViewNameRate
        private val textViewExchangeRate = binding.textViewCurrencyRate

        fun bind(currency: Currency) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(currency = currency, position = layoutPosition)
            }
            textViewNameRate.text = currency.charCode
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                textViewExchangeRate.text = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(getItem(position))
    }
}