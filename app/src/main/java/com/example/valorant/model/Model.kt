package com.example.valorant.model

import android.content.Context
import android.graphics.Bitmap
import com.android.volley.Response
import com.example.valorant.model.database.Agent
import com.example.valorant.model.database.Rol
import com.example.valorant.model.database.ValorantDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Model(context : Context) {
    private val network = Network.getInstance(context)
    private val ValorantDao = ValorantDatabase.getInstance(context).db

    fun getInitials(listener: Response.Listener<List<Char>>, errorListener: Response.ErrorListener){
        network.getInitials(listener, errorListener)
    }

    fun getRoles(listener: Response.Listener<List<Rol>>,
                       errorListener: Response.ErrorListener) =
        GlobalScope.launch(Dispatchers.Main) {

            val roles = withContext(Dispatchers.IO) {
                ValorantDao.loadAllRoles()
            }
            if (roles.isEmpty()) {
                network.getRoles({
                    GlobalScope.launch { ValorantDao.insertRol(it) }
                    listener.onResponse(it)
                }, {
                    errorListener.onErrorResponse(it)
                })
            } else {
                listener.onResponse(roles)
            }
        }


    //NETWORK SEARCH
       //AGENTS
    fun getAgentByInitial(initial: Char, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener){
        network.getAgentByInitial(initial, listener, errorListener)
    }

    fun getAgentByRol(rol: String, listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener){
        network.getAgentByRol(rol, listener, errorListener)
    }

    fun getAgent(id: String,listener: Response.Listener<Agent>, errorListener: Response.ErrorListener){
        network.getAgent(id, listener, errorListener)
    }
    fun getBitmapAgent(url: String, listener: Response.Listener<Bitmap?>, errorListener: Response.ErrorListener){
        network.getBitmap(url, listener, errorListener)
    }

       //MAPS
    fun getMaps(listener: Response.Listener<List<String>>, errorListener: Response.ErrorListener){
        network.getMaps(listener, errorListener)
    }

    fun getMap(id: String,listener: Response.Listener<Map>, errorListener: Response.ErrorListener){
        network.getMap(id, listener, errorListener)
    }

    fun getBitmapMaps(url: List<String>, listener: Response.Listener<List<MapImage>>, errorListener: Response.ErrorListener){
        network.getBitmapMaps(url, listener, errorListener)
    }
    fun getBitmapMap(url: String, listener: Response.Listener<Bitmap?>, errorListener: Response.ErrorListener){
        network.getBitmap(url, listener, errorListener)
    }


    //LOCAL SEARCH
    fun getAgentByInitialLocal(initial : Char, listener: Response.Listener<List<Agent>>) {
        GlobalScope.launch(Dispatchers.Main) {
            val agents = withContext(Dispatchers.IO) {
                ValorantDao.loadAllAgentsByInitial(initial.toString())
            }
            listener.onResponse(agents)
        }
    }

    fun getAgentByRolLocal(rol: String, listener: Response.Listener<List<Agent>>){
        GlobalScope.launch(Dispatchers.Main) {
            val agents = withContext(Dispatchers.IO) {
                ValorantDao.loadAllAgentsByRol(rol)
            }
            listener.onResponse(agents)
        }
    }


    //INSERTION IN THE DATABASE
    fun insertAgent(agent: Agent){
        ValorantDao.insertAgent(agent)
    }
}