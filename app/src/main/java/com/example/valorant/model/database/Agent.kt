package com.example.valorant.model.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(entity = Rol::class,
        parentColumns = ["name"],
        childColumns = ["rol"])],
    indices = [Index(value = ["rol"])])

class Agent (@PrimaryKey val id: String,
             @ColumnInfo(name="rol") val rol: String,
             val name: String,
             val description: String,
             val image: String,
             val roleDescription: String,
             val abilities : String
             ) :Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(rol)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(roleDescription)
        parcel.writeString(abilities)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Agent> {
        override fun createFromParcel(parcel: Parcel): Agent {
            return Agent(parcel)
        }

        override fun newArray(size: Int): Array<Agent?> {
            return arrayOfNulls(size)
        }
    }
}