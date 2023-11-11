package com.example.valorant.map

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.valorant.R
import com.example.valorant.model.Map
import com.example.valorant.model.Model

class MapActivity : AppCompatActivity(), IMapView {

    lateinit var mapInfo : Map
    lateinit var mapName : TextView
    lateinit var mapImage : ImageView
    lateinit var mapPlanImage : ImageView

    lateinit var progressBar: ProgressBar

    lateinit var presenter : MapPresenter

    companion object {
        const val MAPINFO = "MapInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapName = findViewById(R.id.NameMapTextView)
        mapImage = findViewById(R.id.imagenMapa)
        mapPlanImage = findViewById(R.id.imagePlanID)

        progressBar = findViewById(R.id.progressBarMap)

        mapInfo = intent.getParcelableExtra(MAPINFO)!!

        val model = Model(applicationContext)
        presenter = MapPresenter(this, model)
    }

    override var mapVisible: Boolean
        get() = mapName.visibility == View.VISIBLE
        set(value) {
            val v = if (value) View.VISIBLE else View.GONE
            mapName.visibility = v
            mapImage.visibility = v
            mapPlanImage.visibility = v
        }
    override var progresBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }

    override fun gettingMapInfo(): Map{
        return mapInfo
    }


    override fun showName(name : String){
        mapName.text = name
    }

    override fun showImage(image: Bitmap?) {
        if (image != null){
            mapImage.setImageBitmap(image)
        }
        else{
            mapImage.setImageResource(R.drawable.imagenotavailable)
        }
    }

    override fun showPlan(image: Bitmap?) {
        if (image != null){
            mapPlanImage.setImageBitmap(image)
        }
        else{
            mapPlanImage.setImageResource(R.drawable.imagenotavailable)
        }
    }

    override fun showErrors(error : String) {
        progresBarVisible = false
        val toast = Toast.makeText(this, error, Toast.LENGTH_LONG)
        toast.show()
    }
}