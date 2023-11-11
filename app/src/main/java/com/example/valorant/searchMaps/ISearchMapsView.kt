package com.example.valorant.searchMaps

import android.view.View
import com.example.valorant.model.Map
import com.example.valorant.model.MapImage

interface ISearchMapsView {

    var mapVisible: Boolean
    var progresBarVisible: Boolean

    fun onArrowButtonPressed(view: View)

    fun showMaps(maps: List<MapImage>)
    fun showErrors(error : String)

    fun goToMapActivity(mapInfo : Map)

}