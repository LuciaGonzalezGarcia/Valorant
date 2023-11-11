package com.example.valorant.model

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.valorant.model.database.Agent
import com.example.valorant.model.database.Rol
import org.json.JSONException
import org.json.JSONObject

//AGENTS
private const val URL_AGENTSLIST = "https://valorant-api.com/v1/agents"
private const val URL_AGENT = "https://valorant-api.com/v1/agents/"
private const val LIST = "data"

private const val AGENTID_LABEL = "uuid"
private const val PLAYABLE_LABEL = "isPlayableCharacter"   //A character is duplicated

private const val AGENTNAME_LABEL = "displayName"
private const val AGENTIMAGE_LABEL = "displayIcon"
private const val AGENTDESCPRIPTION_LABEL = "description"
private const val ROLE_LABEL = "role"
private const val ROLE_NAME_LABEL = "displayName"
private const val ROLE_DESCPRIPTION_LABEL = "description"

private const val ABILITIESLIST = "abilities"
private const val ABILITIES_NAME_LABEL = "displayName"


//MAPS
private const val URL_MAPSLIST = "https://valorant-api.com/v1/maps"
private const val URL_MAP = "https://valorant-api.com/v1/maps/"
private const val LISTMAP = "data"

private const val MAPID_LABEL = "uuid"
private const val MAPNAME_LABEL = "displayName"
private const val MAPIMAGE_LABEL = "splash"
private const val MAPPLANIMAGE_LABEL = "displayIcon"       //There is a map which doesn't have plan



class Network private constructor(context : Context) {
    companion object : SingletonHolder<Network, Context>(::Network)

    val queue = Volley.newRequestQueue(context)

    //AGENTS
    fun getInitials(listener: Response.Listener<List<Char>>, errorListener: Response.ErrorListener) {
        val url = URL_AGENTSLIST
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response -> processInitials(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }
    private fun processInitials(response: JSONObject, listener: Response.Listener<List<Char>>, errorListener: Response.ErrorListener) {
        val initials = ArrayList<Char>()
        try{
            val agentArray = response.getJSONArray(LIST)
            for (i in 0 until agentArray.length()){
                val agentObject : JSONObject = agentArray[i] as JSONObject
                val name = agentObject.getString(AGENTNAME_LABEL)
                val initial = name[0]
                var add =  true
                for (j in 0 until initials.size){
                    if (initials[j] == initial){
                        add = false
                        break
                    }
                }
                if(add){
                    initials.add(initial)
                }
            }
        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing initials"))
        }
        initials.sort()
        listener.onResponse(initials)
    }



    fun getRoles(listener: Response.Listener<List<Rol>>, errorListener: Response.ErrorListener) {
        val url = URL_AGENTSLIST
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response -> processRoles(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processRoles(response: JSONObject, listener: Response.Listener<List<Rol>>, errorListener: Response.ErrorListener) {
        val roles = ArrayList<Rol>()
        try{
            val agentArray = response.getJSONArray(LIST)
            for (i in 0 until agentArray.length()){
                val agentObject : JSONObject = agentArray[i] as JSONObject
                val rolStr : String = agentObject.getString(ROLE_LABEL)
                if (rolStr != "null"){
                    val rol : JSONObject  = agentObject.getJSONObject(ROLE_LABEL)
                    val rolName = rol.getString(ROLE_NAME_LABEL)
                    var add =  true
                    for (j in 0 until roles.size){
                        if (roles[j].toString() == rolName){
                            add = false
                            break
                        }
                    }
                    if(add){
                        val newRol = Rol(rolName)
                        roles.add(newRol)
                    }
                }

            }
        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing roles"))
        }
        roles.sortBy {it.name}
        listener.onResponse(roles)
    }


    fun getAgentByInitial(initial : Char, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
        val url = URL_AGENTSLIST
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processAgentByInitial(initial, response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processAgentByInitial(initial : Char, response: JSONObject, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
        val agentsIDs = ArrayList<String>()
        try{
            val agentArray = response.getJSONArray(LIST)
            for (i in 0 until agentArray.length()){
                val agentObject : JSONObject = agentArray[i] as JSONObject
                val id = agentObject.getString(AGENTID_LABEL)
                val name = agentObject.getString(AGENTNAME_LABEL)
                val playable = agentObject.getString(PLAYABLE_LABEL)

                if(playable == "true" && name[0] == initial){
                    agentsIDs.add(id)
                }

            }

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing agents"))
        }
        agentsIDs.sort()
        listener.onResponse(agentsIDs)
    }

    fun getAgentByRol(rol : String, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
        val url = URL_AGENTSLIST
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processAgentByRol(rol, response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processAgentByRol(rol : String, response: JSONObject, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
        val agentsIDs = ArrayList<String>()
        try{
            val agentArray = response.getJSONArray(LIST)
            for (i in 0 until agentArray.length()){
                val agentObject : JSONObject = agentArray[i] as JSONObject
                val id = agentObject.getString(AGENTID_LABEL)
                val playable = agentObject.getString(PLAYABLE_LABEL)
                val rolStr : String = agentObject.getString(ROLE_LABEL)
                if (rolStr != "null"){
                    val rolObj : JSONObject  = agentObject.getJSONObject(ROLE_LABEL)
                    val rolName = rolObj.getString(ROLE_NAME_LABEL)

                    if(playable == "true" && rolName == rol){
                        agentsIDs.add(id)
                    }
                }


            }

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing agents"))
        }
        agentsIDs.sort()
        listener.onResponse(agentsIDs)
    }


    fun getAgent(id : String, listener: Response.Listener<Agent>, errorListener: Response.ErrorListener){
        val url = "$URL_AGENT$id"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processAgent(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processAgent(response: JSONObject, listener: Response.Listener<Agent>, errorListener: Response.ErrorListener){
        var agent : Agent? = null
        var abilities = ""
        try{
            val agentObject = response.getJSONObject(LIST)
            val id = agentObject.getString(AGENTID_LABEL)
            val rolObj : JSONObject  = agentObject.getJSONObject(ROLE_LABEL)
            val rolName = rolObj.getString(ROLE_NAME_LABEL)
            val rolDescription = rolObj.getString(ROLE_DESCPRIPTION_LABEL)
            val name = agentObject.getString(AGENTNAME_LABEL)
            val description = agentObject.getString(AGENTDESCPRIPTION_LABEL)
            val image = agentObject.getString(AGENTIMAGE_LABEL)

            val abilitiesArray = agentObject.getJSONArray(ABILITIESLIST)
            for (i in 0 until abilitiesArray.length()){
                val abilityObject : JSONObject = abilitiesArray[i] as JSONObject
                val abilityName = abilityObject.getString(ABILITIES_NAME_LABEL)
                if(i != abilitiesArray.length() -1 ){
                    abilities = abilities + abilityName + ", "
                }
                else{
                    abilities = abilities + abilityName
                }
            }

            agent = Agent(id,rolName,name,description,image,rolDescription,abilities)

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing agent"))
        }
        listener.onResponse(agent)
    }

    fun getBitmap(url : String, listener: Response.Listener<Bitmap?>, errorListener: Response.ErrorListener){
        var myImage : Bitmap?
        try{

            val imageRequest = ImageRequest(
                url,
                {bitmap ->
                    myImage = bitmap
                    listener.onResponse(myImage)
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                {error ->
                    myImage = null
                    listener.onResponse(myImage)
                }
            )

            queue.add(imageRequest)

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing image"))
        }
    }


    //MAPS
    fun getMaps(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
        val url = URL_MAPSLIST
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processMaps(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processMaps(response: JSONObject, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener) {
        val mapsIDs = ArrayList<String>()
        try{
            val mapArray = response.getJSONArray(LISTMAP)
            for (i in 0 until mapArray.length()){
                val mapObject : JSONObject = mapArray[i] as JSONObject
                val id = mapObject.getString(MAPID_LABEL)
                mapsIDs.add(id)
            }

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing maps"))
        }
        mapsIDs.sort()
        listener.onResponse(mapsIDs)
    }


    fun getMap(id : String, listener: Response.Listener<Map>, errorListener: Response.ErrorListener){
        val url = "$URL_MAP$id"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response -> processMap(response, listener, errorListener) },
            { error -> errorListener.onErrorResponse(error) })
        queue.add(jsonObjectRequest)
    }

    private fun processMap(response: JSONObject, listener: Response.Listener<Map>, errorListener: Response.ErrorListener){
        var map : Map? = null
        try{
            val mapObject = response.getJSONObject(LISTMAP)
            val id = mapObject.getString(MAPID_LABEL)
            val name = mapObject.getString(MAPNAME_LABEL)
            val image = mapObject.getString(MAPIMAGE_LABEL)
            val plan = mapObject.getString(MAPPLANIMAGE_LABEL)

            map = Map(id,name,image,plan)

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing map"))
        }
        listener.onResponse(map)
    }

    fun getBitmapMaps(urls : List<String>, listener: Response.Listener<List<MapImage>>, errorListener: Response.ErrorListener){
        val myImages = ArrayList<MapImage>()
        try{

            for (i in 0 until urls.size){
                val imageRequest = ImageRequest(
                    urls[i],
                    {bitmap ->
                        val myImage = MapImage(urls[i], bitmap)
                        myImages.add(myImage)
                        collectBitmapsMap(urls.size,listener,myImages)
                    },
                    0,
                    0,
                    ImageView.ScaleType.CENTER_CROP,
                    Bitmap.Config.ARGB_8888,
                    {error ->  // error listener
                        val myImage = MapImage(urls[i], null)
                        myImages.add(myImage)
                        collectBitmapsMap(urls.size,listener,myImages)
                    }
                )

                queue.add(imageRequest)


            }

        }catch (e : JSONException){
            errorListener.onErrorResponse(VolleyError("Bad JSON format: Processing image"))
        }
    }

    private fun collectBitmapsMap(size: Int, listener: Response.Listener<List<MapImage>>, myImages: ArrayList<MapImage>) {
        if(myImages.size == size){
            listener.onResponse(myImages)
        }

    }

}