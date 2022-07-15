package com.rorono.a22recycler.adapter

import com.rorono.a22recycler.models.Currency

interface OnItemClickChosenCurrency {

    fun onItemClick(currency: Currency)

    fun onItemClickDeleteFavoriteCurrency(currency: Currency)
}