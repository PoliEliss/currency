package com.rorono.a22recycler

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.CurrencyItemBinding
import com.rorono.a22recycler.models.Valuta
import java.util.Collections.addAll
import kotlin.math.floor

class CurrencyAdapter() : RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    var onItemClick: ((Valuta) -> Unit)? = null

    private var currencyList = mutableListOf<Valuta>()


    inner class CurrencyHolder(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val textViewNameRate = binding.textViewNameRate
        private val textViewExchangeRate = binding.textViewExchangeRate

        fun bind(money: Valuta) {
            itemView.setOnClickListener {
                onItemClick?.let { it ->
                    it(money)

                }
            }
            textViewNameRate.text = money.name
            textViewExchangeRate.text = (floor(money.value * 100) / 100).toString() + "Р"

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

    fun setItems(items: List<Valuta>) {
        currencyList.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}