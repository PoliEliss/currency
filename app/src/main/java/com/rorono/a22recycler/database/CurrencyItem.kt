package com.rorono.a22recycler.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyItem(
    @PrimaryKey val fullName: String,
    @ColumnInfo(name = "char_code")
    val charCode: String,
    @ColumnInfo(name = "value")
    val value: Double
)


