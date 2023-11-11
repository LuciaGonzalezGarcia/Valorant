package com.example.valorant.listAgents

import com.example.valorant.model.database.Agent

interface IListAgentsView {

    var agentVisible : Boolean
    var progresBarVisible : Boolean

    fun gettingInitial() : Char?
    fun gettingRol() : String?
    fun gettingSearch(): Boolean

    fun showAgents(agents : List<Agent>)
    fun showErrors(error : String)
    fun goToAgentActivity(agentInfo : Agent)
}