package com.example.valorant.searchMaps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.valorant.R
import com.example.valorant.model.MapImage

class AdapterMapsRV (val maps: List<MapImage>, val onClickListener: (Int)-> Unit) : RecyclerView.Adapter<AdapterMapsRV.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var myImage: ImageView = view.findViewById(R.id.imageMapRVID)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recycler_maps, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            if (maps[position].image != null){
                myImage.setImageBitmap(maps[position].image)
            }
            else{
                myImage.setImageResource(R.drawable.imagenotavailable)
            }

            holder.itemView.setOnClickListener {
                onClickListener(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return maps.size
    }
}