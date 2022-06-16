package com.rorono.a22recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.CurrencyItemBinding
import com.rorono.a22recycler.models.Valuate
import kotlin.math.floor

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    var onItemClick: ((Valuate) -> Unit)? = null

    private var currencyList = mutableListOf<Valuate>()


    inner class CurrencyHolder(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewNameRate = binding.textViewNameRate
        private val textViewExchangeRate = binding.textViewExchangeRate

        fun bind(money: Valuate) {
            itemView.setOnClickListener {
                onItemClick?.let { view ->
                   view(money)
                }
            }
            textViewNameRate.text = money.charCode
            ((floor(money.value * 100) / 100).toString() + "Р").also { textViewExchangeRate.text = it }
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
    fun setItems(items: List<Valuate>) {
        currencyList.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}