package com.rorono.a22recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rorono.a22recycler.R

import com.rorono.a22recycler.models.remotemodels.Currency
import com.rorono.a22recycler.utils.Rounding


class ChosenCurrencyAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onItemClickChosenCurrency: OnItemClickChosenCurrency

    fun setOnListener(listener: OnItemClickChosenCurrency) {
        onItemClickChosenCurrency = listener
    }

    var oldList = mutableListOf<Currency>()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(currency: Currency) {
            val tvNameRate = itemView.findViewById<TextView>(R.id.textViewNameRate)
            val ivClose = itemView.findViewById<ImageView>(R.id.ivClose)
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
            ivClose.setOnClickListener {
                onItemClickChosenCurrency.onItemClickDeleteFavoriteCurrency(
                    currency = currency,
                    position = layoutPosition
                )
            }
        }
    }

    inner class ChosenCurrencyHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(currency: Currency) {
            val tvNameRate = itemView.findViewById<TextView>(R.id.textViewNameRate)
            tvNameRate.text = currency.charCode
            val ivClose = itemView.findViewById<ImageView>(R.id.ivClose)
            ivClose.visibility = View.VISIBLE
            val tvExchangeRate = itemView.findViewById<TextView>(R.id.textViewCurrencyRate)
            (Rounding.getTwoNumbersAfterDecimalPoint(currency.value).toString() + "₽").also {
                tvExchangeRate.text = it
            }
            itemView.setOnClickListener {
                onItemClickChosenCurrency.onItemClick(currency = currency, layoutPosition)
            }
            ivClose.setOnClickListener {
                onItemClickChosenCurrency.onItemClickDeleteFavoriteCurrency(
                    currency = currency,
                    position = layoutPosition
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LIST_TYPE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_chosen_currency, parent, false)
            ListViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency, parent, false)
            ChosenCurrencyHolder(view)
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
        val mutableNewList = mutableListOf<Currency>()
        mutableNewList.addAll(newList)
        oldList = mutableNewList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val LIST_TYPE = 0
        private const val GRID_TYPE = 1
    }
}
