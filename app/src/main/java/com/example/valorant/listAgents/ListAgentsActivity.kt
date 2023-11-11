package com.example.valorant.listAgents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valorant.R
import com.example.valorant.agent.AgentActivity
import com.example.valorant.model.Model
import com.example.valorant.model.database.Agent

class ListAgentsActivity : AppCompatActivity(), IListAgentsView {

    private var initial : Char? = null
    private var rol : String? = null
    private var localSearch : Boolean = false

    lateinit var agentsList: RecyclerView
    lateinit var progressBar: ProgressBar

    lateinit var presenter : ListAgentsPresenter

    companion object {
        const val INITIAL = "Initial"
        const val ROL = "Rol"
        const val SEARCH = "Search"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_agents)

        agentsList = findViewById(R.id.RecyclerViewID)
        agentsList.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.progressBarListAgents)

        initial = intent.getCharExtra(INITIAL, '#')
        rol = intent.getStringExtra(ROL)
        localSearch = intent.getBooleanExtra(SEARCH, false)

        val model = Model(applicationContext)
        presenter = ListAgentsPresenter(this, model)

    }

    override var agentVisible: Boolean
        get() = agentsList.visibility == View.VISIBLE
        set(value) {
            agentsList.visibility = if (value) View.VISIBLE else View.GONE
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }

    override fun gettingInitial(): Char? {
        return initial
    }

    override fun gettingRol(): String? {
        return rol
    }

    override fun gettingSearch(): Boolean {
        return localSearch
    }

    override fun showAgents(agents: List<Agent>) {
        agentsList.adapter = AdapterAgentsRV(agents){ position ->  presenter.onAgentSelected(position) }
    }

    override fun showErrors(error : String) {
        progresBarVisible = false
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    //CHANGE ACTIVITY
    override fun goToAgentActivity(agentInfo : Agent){
        val intent =  Intent(this, AgentActivity::class.java).apply {
            putExtra(AgentActivity.AGENTINFO, agentInfo)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}