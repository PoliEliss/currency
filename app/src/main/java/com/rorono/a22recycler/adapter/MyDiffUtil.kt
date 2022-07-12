package com.rorono.a22recycler.adapter

import androidx.recyclerview.widget.DiffUtil
import com.rorono.a22recycler.models.Currency

class MyDiffUtil(
    private val oldList: List<Currency>,
    private val newList: List<Currency>

) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isFavorite == newList[newItemPosition].isFavorite
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].fullName != newList[newItemPosition].fullName -> {
                return false
            }
            oldList[oldItemPosition].isFavorite != newList[newItemPosition].isFavorite -> {
                return false
            }
            oldList[oldItemPosition].charCode != newList[newItemPosition].charCode -> {
                return false
            }
            oldList[oldItemPosition].value != newList[newItemPosition].value -> {
                return false
            }
            else -> true
        }
    }
}