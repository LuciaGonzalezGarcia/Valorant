package com.example.valorant.searchAgents

import android.view.View
import com.example.valorant.model.database.Rol

interface ISearchAgentsView {
    var agentVisible : Boolean
    var progresBarVisible : Boolean

    fun showAgents(initialName : List<Char>)
    fun showRoles(rolesList : List<Rol>)
    fun showErrors(error : String)

    fun disabledAgentSearch()
    fun disabledRolSearch()

    fun onSearchAgentButtonPressed(view: View)
    fun onSearchRolButtonPressed(view: View)
    fun onArrowButtonPressed(view: View)
    fun setLocalSearch(view: View)
    fun setInetSearch(view: View)

    fun goToAgentListActivity(initial: Char?, rol: String?, localSearch : Boolean)
}