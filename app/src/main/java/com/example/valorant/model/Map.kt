package com.example.valorant.model

import android.os.Parcel
import android.os.Parcelable

data class Map(val id: String, val name: String, val image: String, val plan: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(plan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Map> {
        override fun createFromParcel(parcel: Parcel): Map {
            return Map(parcel)
        }

        override fun newArray(size: Int): Array<Map?> {
            return arrayOfNulls(size)
        }
    }
}