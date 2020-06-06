package com.prl.android.covid19india.ui.home.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.model.statewise.DistrictData
import com.prl.android.covid19india.data.model.statewise.StateCovid19DataResponseItem

class StateDetailAdapter(private var data: StateCovid19DataResponseItem?) :
    RecyclerView.Adapter<StateDetailAdapter.StateDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StateDetailViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_district_detail, parent, false)
    )

    override fun onBindViewHolder(holder: StateDetailViewHolder, position: Int) {
        holder.bind(data?.districtData?.get(position))
    }

    fun updateStateDetail(newData: StateCovid19DataResponseItem?){
        data = newData
        notifyDataSetChanged()
    }
    override fun getItemCount() = data?.districtData?.size ?: 0

    override fun getItemId(position: Int) = position.toLong()

    inner class StateDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val districtTv: TextView = itemView.findViewById(R.id.tv_district)
        private val todayConfirmedTv: TextView = itemView.findViewById(R.id.tv_today_confirmed)
        private val totalConfirmedTv: TextView = itemView.findViewById(R.id.tv_total_confirmed)

        fun bind(data: DistrictData?) {

            data?.let {
                with(districtTv.context) {
                    districtTv.text = data.district
                    todayConfirmedTv.text =
                        getString(R.string.today_confirmed, data.delta.confirmed.toString())
                    totalConfirmedTv.text =
                        getString(R.string.total_confirmed, data.confirmed.toString())
                }

            }

        }
    }
}