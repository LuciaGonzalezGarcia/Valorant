package com.example.valorant.listAgents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.valorant.R
import com.example.valorant.model.database.Agent

class AdapterAgentsRV (val agents: List<Agent>, val onClickListener: (Int)-> Unit) : RecyclerView.Adapter<AdapterAgentsRV.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var myName: TextView = view.findViewById(R.id.nameID)
        var myRol: TextView = view.findViewById(R.id.rolID)
        var myAbilities: TextView = view.findViewById(R.id.abilitiesID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler_agents, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            myName.text = agents[position].name
            myRol.text = agents[position].rol
            myAbilities.text = agents[position].abilities

            holder.itemView.setOnClickListener {
                onClickListener(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return agents.size
    }

}