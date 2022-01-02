package com.neurotech.stressapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.neurotech.stressapp.R
import com.neurotech.stressapp.domain.Device
import com.neurotech.stressapp.ui.viewmodel.SearchFragmentViewModel

class SearchCardAdapter(private val devices: List<Device>): RecyclerView.Adapter<SearchCardAdapter.Companion.CardViewHolder>() {
    private val viewModel: SearchFragmentViewModel = SearchFragment.viewModel

    companion object{
        class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val cardView: CardView = itemView.findViewById(R.id.card_view)
            val nameDevice:TextView = itemView.findViewById(R.id.name_device)
            val deviceMAC:TextView = itemView.findViewById(R.id.mac_device)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.search_card_item,parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.nameDevice.text = devices[position].name
        holder.deviceMAC.text = devices[position].MAC
        holder.itemView.setOnClickListener{
            viewModel.connectionToDevice(devices[position].MAC)
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }
}