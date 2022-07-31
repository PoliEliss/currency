package com.rorono.a22recycler.adapter

import com.rorono.a22recycler.models.remotemodels.Currency

interface OnItemClickChosenCurrency {

    fun onItemClick(currency: Currency, position:Int)
    fun onItemClickDeleteFavoriteCurrency(currency: Currency, position: Int)
}