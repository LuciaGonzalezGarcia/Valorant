package com.example.valorant.map

import android.graphics.Bitmap
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.valorant.model.Map
import com.example.valorant.model.Model

class MapPresenter (val view: IMapView, val model : Model){
    val myMap : Map
    var myImage : Bitmap? = null
    var myPlanImage : Bitmap? = null

    init {
        view.progresBarVisible = true
        view.mapVisible = false
        myMap = view.gettingMapInfo()
        getBitmap(myMap.image)
    }

    private fun getBitmap(url : String){
        model.getBitmapMap(url, object : Response.Listener<Bitmap?>{
            override fun onResponse(image: Bitmap?) {
                myImage = image

                model.getBitmapMap(myMap.plan, object : Response.Listener<Bitmap?>{
                    override fun onResponse(image: Bitmap?) {
                        view.progresBarVisible = false
                        view.mapVisible = true
                        myPlanImage = image
                        showInfo()

                    }
                }, object : Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {
                        view.showErrors(error.toString())
                    }
                })

            }
        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }
        })
    }

    private fun showInfo(){
        view.showName(myMap.name)
        view.showImage(myImage)
        view.showPlan(myPlanImage)
    }
}