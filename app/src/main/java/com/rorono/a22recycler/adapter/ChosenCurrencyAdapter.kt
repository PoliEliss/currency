package com.rorono.a22recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.R
import com.rorono.a22recycler.databinding.ChosenCurrencyItemBinding
import com.rorono.a22recycler.databinding.CurrencyItemBinding
import com.rorono.a22recycler.databinding.ListChosenCurrencyItemBinding
import com.rorono.a22recycler.models.Currency
import com.rorono.a22recycler.utils.Rounding


private const val LIST_TYPE = 0
private const val GRID_TYPE = 1

class ChosenCurrencyAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onItemClickChosenCurrency: OnItemClickChosenCurrency

    fun setOnListener(listener: OnItemClickChosenCurrency) {
        onItemClickChosenCurrency = listener
    }

    var oldList = emptyList<Currency>()


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: Currency) {
           val tvNameRate = itemView.findViewById<TextView>(R.id.textViewNameRate)
            tvNameRate.text = currency.charCode
            val tvExchangeRate = itemView.findViewById<TextView>(R.id.textViewExchangeRate)
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                tvExchangeRate.text = it
            }
            val fullName = itemView.findViewById<TextView>(R.id.tvFullName)
            fullName.text = currency.fullName
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
        }
    }

    inner class ChosenCurrencyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(currency: Currency) {
           val tvNameRate = itemView.findViewById<TextView>(R.id.textViewNameRate)
            tvNameRate.text = currency.charCode
            val tvExchangeRate = itemView.findViewById<TextView>(R.id.textViewExchangeRate)
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                tvExchangeRate.setText(it)
            }
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LIST_TYPE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_chosen_currency_item, parent, false)
            return ListViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.chosen_currency_item, parent, false)
            return ChosenCurrencyHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == LIST_TYPE) {
            (holder as ListViewHolder).bind(oldList[position])
        } else {
            (holder as ChosenCurrencyHolder).bind(oldList[position])
        }

    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (oldList[position].isFavorite == 0) {
            LIST_TYPE
        } else {
            GRID_TYPE
        }
    }

    fun setData(newList: List<Currency>) {
        val diffUtil = MyDiffUtil(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}
