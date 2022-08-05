package com.rorono.a22recycler.adapter

import com.rorono.a22recycler.models.remotemodels.Currency

interface OnItemClickListener {
    fun onItemClick(currency: Currency, position: Int)
}