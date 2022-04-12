package com.example.marvel_app_test.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvel_app_test.model.Characters

@Database(entities = [Characters::class], version = 1)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun marvelDAO() : MarvelDAO

    companion object{
        fun getDatabase(context: Context) : AppDatabase{
            return Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"marvel_app_test")
                .allowMainThreadQueries()
                .build()
        }
    }
}
