package com.rorono.a22recycler.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_currency")
data class SaveCurrencyItem(

    @PrimaryKey
    val fullName: String,
    @ColumnInfo(name = "char_code")
    val charCode: String,
    @ColumnInfo(name = "value")
    val value: Double,
    val favorite: Int = 0

)
