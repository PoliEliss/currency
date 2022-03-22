package com.rorono.a22recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.databinding.CurrencyItemBinding

class CurrencyAdapter:RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {

    var  currencyList = ArrayList<Currency>()



   inner class CurrencyHolder(item:View): RecyclerView.ViewHolder(item) {

       val binding = CurrencyItemBinding.bind(item)
             fun bind(currency: Currency) = with(binding){
                 textViewNameRate.text = currency.name
                 textViewExchangeRate.text = currency.exchangeRate
             }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_item,parent,false)
        return CurrencyHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bind(currencyList[position])
    }

    override fun getItemCount(): Int {
       return currencyList.size
    }

    fun add(currency: Currency){
        for (i in 1 ..120){
            currencyList.add(Currency("AUD","55.00Р"))
        }
        notifyDataSetChanged()
    }
}