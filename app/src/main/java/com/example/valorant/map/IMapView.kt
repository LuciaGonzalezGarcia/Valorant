package com.example.valorant.map

import android.graphics.Bitmap
import com.example.valorant.model.Map

interface IMapView {
    var mapVisible: Boolean
    var progresBarVisible: Boolean

    fun gettingMapInfo(): Map
    fun showName(name : String)
    fun showImage(image: Bitmap?)
    fun showPlan(image: Bitmap?)
    fun showErrors(error : String)
}