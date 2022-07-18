package com.rorono.a22recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.ChosenCurrencyItemBinding
import com.rorono.a22recycler.models.Currency


class ChosenCurrencyAdapter() :
    RecyclerView.Adapter<ChosenCurrencyAdapter.ChosenCurrencyHolder>() {


   lateinit var onItemClickChosenCurrency: OnItemClickChosenCurrency

    fun setOnListener(listener: OnItemClickChosenCurrency) {
        onItemClickChosenCurrency = listener
    }

    var oldList = emptyList<Currency>()

    inner class ChosenCurrencyHolder(binding: ChosenCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvNameRate = binding.textViewNameRate
        private val ivDelete = binding.ivDelete


        fun bind(currency: Currency) {

            itemView.setOnClickListener {
                onItemClickChosenCurrency.onItemClick(currency = currency, layoutPosition)
            }
            ivDelete.setOnClickListener {
                onItemClickChosenCurrency.onItemClickDeleteFavoriteCurrency(currency = currency, position = layoutPosition)
            }
            tvNameRate.text = currency.charCode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenCurrencyHolder {
        val binding =
            ChosenCurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
