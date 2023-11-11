package com.example.valorant.agent

import android.graphics.Bitmap
import android.view.View
import com.example.valorant.model.database.Agent

interface IAgentView {
    var agentVisible: Boolean
    var progresBarVisible: Boolean

    fun gettingAgentInfo(): Agent
    fun showName(name : String)
    fun showRol(rol: String, rolDescription: String)
    fun showDescription(description: String)
    fun showImage(image: Bitmap?)
    fun showAbilities(abilities: String)

    fun onSaveButtonPressed(view: View)
    fun showSaveDialog()
    fun showErrors(error : String)

}