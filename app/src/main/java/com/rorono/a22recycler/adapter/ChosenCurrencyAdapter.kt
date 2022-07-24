package com.rorono.a22recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.ChosenCurrencyItemBinding
import com.rorono.a22recycler.databinding.CurrencyItemBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.utils.Rounding


class ChosenCurrencyAdapter() :
    RecyclerView.Adapter<ChosenCurrencyAdapter.ChosenCurrencyHolder>() {


   lateinit var onItemClickChosenCurrency: OnItemClickChosenCurrency

    fun setOnListener(listener: OnItemClickChosenCurrency) {
        onItemClickChosenCurrency = listener
    }

    var oldList = emptyList<Currency>()

    inner class ChosenCurrencyHolder(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvNameRate = binding.textViewNameRate
        private val tvExchangeRate = binding.textViewExchangeRate


        fun bind(currency: Currency) {

            itemView.setOnClickListener {
                onItemClickChosenCurrency.onItemClick(currency = currency, layoutPosition)
            }

            itemView.setOnLongClickListener {
                onItemClickChosenCurrency.onItemClickDeleteFavoriteCurrency(
                    currency = currency,
                    position = layoutPosition
                )
                true
            }
            tvNameRate.text = currency.charCode
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                tvExchangeRate.text = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenCurrencyHolder {
        val binding =
           CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChosenCurrencyHolder(binding)
    }

    override fun onBindViewHolder(holder: ChosenCurrencyHolder, position: Int) {
        holder.bind(oldList[position])
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setData(newList: List<Currency>) {
        val diffUtil = MyDiffUtil(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)

    }
}
