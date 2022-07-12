package com.rorono.a22recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.CurrencyItemBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.utils.Rounding

class CurrencyAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    private var currencyList = mutableListOf<Currency>()

    inner class CurrencyHolder(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewNameRate = binding.textViewNameRate
        private val textViewExchangeRate = binding.textViewExchangeRate

        fun bind(currency: Currency) {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(currency=currency)
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
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Currency>) {
        currencyList.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}