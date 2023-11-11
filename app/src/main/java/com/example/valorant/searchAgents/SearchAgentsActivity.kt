package com.example.valorant.searchAgents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.valorant.R
import com.example.valorant.listAgents.ListAgentsActivity
import com.example.valorant.model.Model
import com.example.valorant.model.database.Rol
import com.example.valorant.searchMaps.SearchMapsActivity

class SearchAgentsActivity : AppCompatActivity(), ISearchAgentsView {

    lateinit var valorantText : TextView
    lateinit var searchText : TextView
    lateinit var leftArrow : ImageButton
    lateinit var rightArrow : ImageButton

    lateinit var agentText : TextView
    lateinit var rolText : TextView
    lateinit var agents : Spinner
    lateinit var roles : Spinner

    lateinit var agentSearch : Button
    lateinit var rolSearch : Button

    lateinit var typeSearch: RadioGroup

    lateinit var progressBar: ProgressBar

    lateinit var presenter : SearchAgentsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_agents)

        valorantText = findViewById(R.id.valorantTextID)
        searchText = findViewById(R.id.searchTextID)
        leftArrow = findViewById(R.id.arrowLeftID)
        rightArrow = findViewById(R.id.arrowRightID)
        agentText = findViewById(R.id.agentsTextID)
        rolText = findViewById(R.id.rolsTextID)
        agents = findViewById(R.id.spinnerAgentsID)
        roles = findViewById(R.id.spinnerRolsID)
        agentSearch = findViewById(R.id.searchAgentButtonID)
        rolSearch = findViewById(R.id.searchRolsID)
        typeSearch = findViewById(R.id.radioGroup)
        progressBar = findViewById(R.id.progressBarAgentsID)

        val model = Model(applicationContext)
        presenter = SearchAgentsPresenter(this, model)
    }

    override var agentVisible: Boolean
        get() = valorantText.visibility == View.VISIBLE
        set(value) {
            val v = if (value) View.VISIBLE else View.GONE
            valorantText.visibility = v
            searchText.visibility = v
            leftArrow.visibility = v
            rightArrow.visibility = v
            agentText.visibility = v
            agents.visibility = v
            rolText.visibility = v
            roles.visibility = v
            agentSearch.visibility = v
            rolSearch.visibility = v
            typeSearch.visibility = v
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }



    //SPINNERS IMPLEMENTATION
    override fun showAgents(initialName : List<Char>) {

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, initialName)
        agents.setAdapter(adapter)

        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val initial : Char = agents.getItemAtPosition(p2) as Char
                presenter.setChosenInitial(initial)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }.also { agents.onItemSelectedListener = it }

    }

    override fun showRoles(rolesList: List<Rol>) {

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, rolesList)
        roles.setAdapter(adapter)

        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val rol : Rol = roles.getItemAtPosition(p2) as Rol
                presenter.setChosenRol(rol)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }.also { roles.onItemSelectedListener = it }

    }

    override fun showErrors(error : String) {
        progresBarVisible = false
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun disabledAgentSearch() {
        agentSearch.isEnabled = false
        rolSearch.isEnabled = true
    }

    override fun disabledRolSearch() {
        agentSearch.isEnabled = true
        rolSearch.isEnabled = false
    }

    //SEARCH BUTTONS INTERACTION
    override fun onSearchAgentButtonPressed(view: View) {
        presenter.doInitialSearch()
    }

    override fun onSearchRolButtonPressed(view: View) {
        presenter.doRolSearch()
    }

    //MAPS BUTTONS INTERACTION
    override fun onArrowButtonPressed(view: View) {
        val intent =  Intent(this, SearchMapsActivity::class.java)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


    //RADIO BUTTONS INTERACTION
    override fun setLocalSearch(view: View) {
        presenter.setChosenSearch(true)
    }

    override fun setInetSearch(view: View) {
        presenter.setChosenSearch(false)
    }


    //CHANGE ACTIVITY
    override fun goToAgentListActivity(initial: Char?, rol: String?, localSearch : Boolean) {
        val intent =  Intent(this, ListAgentsActivity::class.java).apply {
            putExtra(ListAgentsActivity.INITIAL, initial)
            putExtra(ListAgentsActivity.ROL, rol)
            putExtra(ListAgentsActivity.SEARCH, localSearch)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}