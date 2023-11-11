package com.example.valorant.agent

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.valorant.R
import com.example.valorant.model.Model
import com.example.valorant.model.database.Agent

class AgentActivity : AppCompatActivity() , IAgentView {

    lateinit var agentInfo : Agent
    lateinit var agentName : TextView
    lateinit var rolText : TextView
    lateinit var agentRol : TextView
    lateinit var agentRolDescription : TextView
    lateinit var descriptionText : TextView
    lateinit var agentDescription : TextView
    lateinit var agentImage : ImageView
    lateinit var abilitiesText : TextView
    lateinit var agentabilities : TextView
    lateinit var saveButton : Button

    lateinit var progressBar: ProgressBar

    lateinit var presenter : AgentPresenter

    companion object {
        const val AGENTINFO = "AgentInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent)

        agentName = findViewById(R.id.nameAgentID)
        rolText = findViewById(R.id.RolText)
        agentRol = findViewById(R.id.rolTextViewID)
        agentRolDescription = findViewById(R.id.DescriptionRolID)
        descriptionText = findViewById(R.id.DescriptionText)
        agentDescription = findViewById(R.id.descriptionID)
        agentImage = findViewById(R.id.agentImageID)
        abilitiesText = findViewById(R.id.habilitiesText)
        agentabilities = findViewById(R.id.habilitiesID)
        saveButton = findViewById(R.id.buttonSave)

        progressBar = findViewById(R.id.progressBarAgent)

        agentInfo = intent.getParcelableExtra(AGENTINFO)!!

        val model = Model(applicationContext)
        presenter = AgentPresenter(this, model)
    }

    override var agentVisible: Boolean
        get() = agentName.visibility == View.VISIBLE
        set(value) {
            val v = if (value) View.VISIBLE else View.GONE
            agentName.visibility = v
            rolText.visibility = v
            agentRol.visibility = v
            agentRolDescription.visibility = v
            descriptionText.visibility = v
            agentDescription.visibility = v
            agentImage.visibility = v
            abilitiesText.visibility = v
            agentabilities.visibility = v
            saveButton.visibility = v
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }


    override fun gettingAgentInfo(): Agent{
        return agentInfo
    }

    override fun showName(name : String){
        agentName.text = name
    }

    override fun showRol(rol: String, rolDescription: String) {
        agentRol.text = rol
        agentRolDescription.text = rolDescription
    }

    override fun showDescription(description: String) {
        agentDescription.text = description
    }

    override fun showImage(image: Bitmap?) {
        if (image != null){
            agentImage.setImageBitmap(image)
        }
        else{
            agentImage.setImageResource(R.drawable.usernotfound)
        }

    }

    override fun showAbilities(abilities: String) {
        agentabilities.text = abilities
    }


    //BUTTONS INTERACTION
    override fun onSaveButtonPressed(view: View) {
        presenter.addAgent()
    }

    //DIALOG
    override fun showSaveDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Database")
            setMessage("Agent inserted in local database")
            setPositiveButton("OK") { _, _ ->
                finish()
            }
            show()
        }
    }

    override fun showErrors(error : String) {
        progresBarVisible = false
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }
}