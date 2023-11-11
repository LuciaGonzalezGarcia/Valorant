package com.example.valorant.searchAgents

import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.valorant.model.Model
import com.example.valorant.model.database.Rol

class SearchAgentsPresenter(val view: ISearchAgentsView, val model : Model) {

    private var initial : Char? = null
    private var rol : String? = null
    private var localSearch : Boolean = false

    init{
        view.progresBarVisible = true
        view.agentVisible = false


        //GET LIST OF INITIALS AND ROLES
        model.getInitials(object : Response.Listener<List<Char>>{
            override fun onResponse(initials: List<Char>) {
                view.showAgents(initials)
                view.progresBarVisible = false
                view.agentVisible = true


            }

        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }

        })

        model.getRoles(object : Response.Listener<List<Rol>>{
            override fun onResponse(roles: List<Rol>) {
                view.showRoles(roles)
                view.progresBarVisible = false
                view.agentVisible = true

            }

        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                view.showErrors(error.toString())
            }

        })
    }

    fun setChosenInitial(initial : Char){
        this.initial = initial
        view.disabledRolSearch()
    }

    fun setChosenRol(rol : Rol){
        this.rol = rol.toString()
        view.disabledAgentSearch()
    }

    fun setChosenSearch(search : Boolean){
        localSearch = search
    }

    fun doInitialSearch(){
        view.goToAgentListActivity(initial, null, localSearch)
    }

    fun doRolSearch(){
        view.goToAgentListActivity(null, rol, localSearch)
    }
}