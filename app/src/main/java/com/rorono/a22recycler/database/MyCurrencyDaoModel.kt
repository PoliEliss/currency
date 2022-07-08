package com.rorono.a22recycler.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class MyCurrencyDaoModel(
    @PrimaryKey var id: Int = 1,
    @ColumnInfo(name = "currency")
    var currency: String,
)


