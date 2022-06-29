package com.rorono.a22recycler.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyItem::class], version = 1)
abstract class CurrencyDataBase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var database: CurrencyDataBase? = null

        @Synchronized
        fun getInstance(context: Context): CurrencyDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(
                    context, CurrencyDataBase::class.java,
                    "database"
                ).build()
                database as CurrencyDataBase
            } else database as CurrencyDataBase
        }
    }
}