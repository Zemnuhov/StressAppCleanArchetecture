package com.neurotech.stressapp.ui.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.neurotech.domain.models.DeviceDomainModel
import com.neurotech.stressapp.R

class SearchCardAdapter(private val devices: List<DeviceDomainModel>): RecyclerView.Adapter<SearchCardAdapter.CardViewHolder>() {

    lateinit var callBack: CallBack

    fun registerCallback(callBack: CallBack){
        this.callBack = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_search_card,parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.nameDevice.text = devices[position].name
        holder.deviceMAC.text = devices[position].MAC
        holder.itemView.setOnClickListener{
            callBack.connectDeviceCallBack(devices[position].MAC)
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.card_view)
        val nameDevice:TextView = itemView.findViewById(R.id.name_device)
        val deviceMAC:TextView = itemView.findViewById(R.id.mac_device)

    }

    interface CallBack{
        fun connectDeviceCallBack(MAC: String)
    }
}