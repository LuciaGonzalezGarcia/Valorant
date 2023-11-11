package com.example.valorant.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rol(@PrimaryKey val name: String)
{
    override fun toString(): String {
        return name
    }

}