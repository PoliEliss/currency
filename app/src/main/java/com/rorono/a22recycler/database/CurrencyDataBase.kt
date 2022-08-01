package com.rorono.a22recycler.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rorono.a22recycler.models.localmodels.CurrencyItem
import com.rorono.a22recycler.models.localmodels.SaveCurrencyItem

@Database(entities = [CurrencyItem::class, SaveCurrencyItem::class], version = 4)
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

                ).addMigrations(migration2_3).build()

                database as CurrencyDataBase
            } else database as CurrencyDataBase
        }

        private val migration2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE save_currency ADD COLUMN favorite INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }
}
