package com.example.valorant.searchMaps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valorant.R
import com.example.valorant.map.MapActivity
import com.example.valorant.model.Map
import com.example.valorant.model.MapImage
import com.example.valorant.model.Model
import com.example.valorant.searchAgents.SearchAgentsActivity

class SearchMapsActivity : AppCompatActivity() , ISearchMapsView {

    lateinit var searchText : TextView
    lateinit var leftArrow : ImageButton
    lateinit var rightArrow : ImageButton

    lateinit var mapsList: RecyclerView
    lateinit var progressBar: ProgressBar

    lateinit var presenter : SearchMapsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_maps)

        searchText = findViewById(R.id.mapsTextID)
        leftArrow = findViewById(R.id.arrowLeftMapsID)
        rightArrow = findViewById(R.id.arrowRightMapsID)

        mapsList = findViewById(R.id.recyclerViewMaps)
        mapsList.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.progressBarMapsID)

        val model = Model(applicationContext)
        presenter = SearchMapsPresenter(this, model)
    }

    override var mapVisible: Boolean
        get() = mapsList.visibility == View.VISIBLE
        set(value) {
            val v = if (value) View.VISIBLE else View.GONE
            mapsList.visibility = v
            searchText.visibility = v
            leftArrow.visibility = v
            rightArrow.visibility = v
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }


    //AGENTS BUTTONS INTERACTION
    override fun onArrowButtonPressed(view: View) {
        val intent =  Intent(this, SearchAgentsActivity::class.java)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }


    override fun showMaps(maps: List<MapImage>) {
        mapsList.adapter = AdapterMapsRV(maps){ position ->  presenter.onMapSelected(position) }
    }

    override fun showErrors(error : String) {
        progresBarVisible = false
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }

    //CHANGE ACTIVITY
    override fun goToMapActivity(mapInfo : Map){
        val intent =  Intent(this, MapActivity::class.java).apply {
            putExtra(MapActivity.MAPINFO, mapInfo)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}