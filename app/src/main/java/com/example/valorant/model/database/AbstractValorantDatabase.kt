package com.example.valorant.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.valorant.model.SingletonHolder

@Database(entities=[
    Rol::class,
    Agent::class
], version = 1)

private abstract class AbstractValorantDatabase: RoomDatabase() {
    abstract fun getDAO(): ValorantDAO
}

class ValorantDatabase private constructor(context: Context) {

    val db = Room.databaseBuilder(context, AbstractValorantDatabase::class.java, "database")
        .build()
        .getDAO()
    companion object : SingletonHolder<ValorantDatabase, Context>(::ValorantDatabase)
}