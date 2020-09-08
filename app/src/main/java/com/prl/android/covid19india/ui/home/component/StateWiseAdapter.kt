package com.prl.android.covid19india.ui.home.component

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.model.country.Statewise
import com.prl.android.covid19india.ui.home.inflateLayout

class StateWiseAdapter(private var data: List<Statewise>?, val listener: (Statewise?) -> Unit) :
    RecyclerView.Adapter<StateWiseAdapter.StateWiseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StateWiseViewHolder(parent.inflateLayout(R.layout.item_statewise))

    override fun getItemCount() = data?.size ?: 0

    override fun getItemId(position: Int) = position.toLong()

    override fun onBindViewHolder(holder: StateWiseViewHolder, position: Int) {
        holder.bind(data?.get(position))
    }

    fun updateData(data: List<Statewise>?) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class StateWiseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stateTv: TextView = itemView.findViewById(R.id.tv_district)
        private val todayConfirmedTv: TextView = itemView.findViewById(R.id.tv_today_confirmed)
        private val totalConfirmedTv: TextView = itemView.findViewById(R.id.tv_total_confirmed)
        private val todayRecoveredTv: TextView = itemView.findViewById(R.id.tv_today_recovered)
        private val totalRecoveredTv: TextView = itemView.findViewById(R.id.tv_total_recovered)
        private val todayDeathTv: TextView = itemView.findViewById(R.id.tv_today_death)
        private val totalDeathTv: TextView = itemView.findViewById(R.id.tv_total_death)
        private val totalActiveTv: TextView = itemView.findViewById(R.id.tv_total_active)
        private val stateCardView: CardView = itemView.findViewById(R.id.cv_state_wise)

        fun bind(state: Statewise?) {
            val context = todayDeathTv.context
            with(context) {
                stateTv.text = state?.state ?: "NA"
                todayConfirmedTv.text = getString(R.string.today_confirmed, state?.deltaConfirmed)
                todayRecoveredTv.text = getString(R.string.today_recovered, state?.deltaRecovered)
                todayDeathTv.text = getString(R.string.today_deaths, state?.deltaDeaths)
                totalConfirmedTv.text = getString(R.string.total_confirmed, state?.confirmed)
                totalRecoveredTv.text = getString(R.string.total_recovered, state?.recovered)
                totalDeathTv.text = getString(R.string.total_deaths, state?.deaths)
                totalActiveTv.text = getString(R.string.total_active, state?.active)
                stateCardView.setOnClickListener {
                    listener.invoke(state)
                }
            }
        }
    }
}