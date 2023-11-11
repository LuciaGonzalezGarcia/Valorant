package com.example.valorant.searchMaps

import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.valorant.model.Map
import com.example.valorant.model.MapImage
import com.example.valorant.model.Model

class SearchMapsPresenter (val view: ISearchMapsView, val model : Model) {

    private var mapsList = ArrayList<Map>()
    private var urlsList = ArrayList<String>()
    private var imagesList : List<MapImage>? = null

    init {
        view.progresBarVisible = true
        view.mapVisible = false

        model.getMaps(object : Response.Listener<List<String>>{
            override fun onResponse(maps: List<String>) {
                for (i in 0 until maps.size){

                    model.getMap(maps[i], object : Response.Listener<Map>{
                        override fun onResponse(map: Map) {

                            mapsList.add(map)
                            urlsList.add(map.image)

                            if (urlsList.size == maps.size){
                                getBitmaps(urlsList)
                            }
                        }
                    }, object : Response.ErrorListener{
                        override fun onErrorResponse(error: VolleyError?) {
                            view.showErrors(error.toString())
                        }
                    })

                }
            }
        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }
        })
    }

    private fun getBitmaps(urls : List<String>){
        model.getBitmapMaps(urls, object : Response.Listener<List<MapImage>>{
            override fun onResponse(images: List<MapImage>) {
                view.progresBarVisible = false
                view.mapVisible = true
                imagesList = images
                view.showMaps(images)

            }
        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }
        })
    }

    fun onMapSelected(position: Int){
        for(i in 0 until mapsList.size){
            if(imagesList!![position].url == mapsList[i].image){
                view.goToMapActivity(mapsList[i])
            }

        }

    }
}