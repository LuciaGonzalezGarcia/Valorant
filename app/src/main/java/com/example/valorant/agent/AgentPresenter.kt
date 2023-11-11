package com.example.valorant.agent

import android.graphics.Bitmap
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.valorant.model.Model
import com.example.valorant.model.database.Agent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgentPresenter (val view: IAgentView, val model : Model){
    val myAgent : Agent
    var myImage : Bitmap? = null

    init {
        view.progresBarVisible = true
        view.agentVisible = false

        myAgent = view.gettingAgentInfo()
        getBitmap(myAgent.image)
    }

    private fun getBitmap(url : String){
        model.getBitmapAgent(url, object : Response.Listener<Bitmap?>{
            override fun onResponse(image: Bitmap?) {
                view.progresBarVisible = false
                view.agentVisible = true
                myImage = image
                showInfo()

            }
        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }
        })
    }

    private fun showInfo(){
        view.showName(myAgent.name)
        view.showRol(myAgent.rol, myAgent.roleDescription)
        view.showDescription(myAgent.description)
        view.showImage(myImage)
        view.showAbilities(myAgent.abilities)
    }

    fun addAgent(){
        val newAgent = Agent(myAgent.id, myAgent.rol, myAgent.name, myAgent.description, myAgent.image, myAgent.roleDescription, myAgent.abilities)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                model.insertAgent(newAgent)
            }
        }
        view.showSaveDialog()
    }

}