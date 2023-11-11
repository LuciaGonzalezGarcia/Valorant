package com.example.valorant.listAgents

import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.valorant.model.Model
import com.example.valorant.model.database.Agent

class ListAgentsPresenter (val view: IListAgentsView, val model : Model){

    private var initial : Char? = null
    private var rol : String? = null
    private var localSearch : Boolean = false
    private var agentsList = ArrayList<Agent>()

    init {
        initial = view.gettingInitial()
        rol = view.gettingRol()
        localSearch = view.gettingSearch()

        view.progresBarVisible = true
        view.agentVisible = false

        if (initial != '#'){    //IF WE DO DE SEARCH BY INITIAL
            if (!localSearch){    //IF WE DO NETWORK SEARCH
                model.getAgentByInitial(initial!!, object : Response.Listener<List<String>>{
                    override fun onResponse(agents: List<String>) {
                        for (i in 0 until agents.size){

                            model.getAgent(agents[i], object : Response.Listener<Agent>{
                                override fun onResponse(agent: Agent) {
                                    agentsList.add(agent)
                                    view.progresBarVisible = false
                                    view.agentVisible = true
                                    if (agentsList.size == agents.size){
                                        view.showAgents(agentsList)
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
            else{      //IF WE DO LOCAL SEARCH
                model.getAgentByInitialLocal(initial!!, object : Response.Listener<List<Agent>>{
                    override fun onResponse(agents: List<Agent>) {
                        for (i in 0 until agents.size){
                            agentsList.add(agents[i])
                            if (agentsList.size == agents.size){
                                view.showAgents(agentsList)
                            }
                        }
                        view.progresBarVisible = false
                        view.agentVisible = true
                    }
                })
            }
        }
        else if (rol != null){     //IF WE DO DE SEARCH BY ROL
            if (!localSearch){            //IF WE DO NETWORK SEARCH
                model.getAgentByRol(rol!!, object : Response.Listener<List<String>>{
                    override fun onResponse(agents: List<String>) {
                        for (i in 0 until agents.size){

                            model.getAgent(agents[i], object : Response.Listener<Agent>{
                                override fun onResponse(agent: Agent) {
                                    agentsList.add(agent)

                                    if (agentsList.size == agents.size){
                                        view.progresBarVisible = false
                                        view.agentVisible = true
                                        view.showAgents(agentsList)
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
            else{       //IF WE DO LOCAL SEARCH
                model.getAgentByRolLocal(rol!!, object : Response.Listener<List<Agent>>{
                    override fun onResponse(agents: List<Agent>) {
                        for (i in 0 until agents.size){
                            agentsList.add(agents[i])
                            if (agentsList.size == agents.size){
                                view.showAgents(agentsList)
                            }
                        }
                        view.progresBarVisible = false
                        view.agentVisible = true
                    }
                })
            }
        }
    }



    fun onAgentSelected(position: Int){
        view.goToAgentActivity(agentsList[position])
    }

}