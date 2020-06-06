package com.prl.android.covid19india.ui.home.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.model.country.Statewise

class TotalCountAdapter(private var data : Statewise?) : RecyclerView.Adapter<TotalCountAdapter.TotalCountViewHolder>() {
    private lateinit var context: Context
    private val cardProperties: List<Triple<String, Int, Int>> = listOf(
        Triple("Confirmed", R.color.colorDarkRed, R.color.colorLightRed),
        Triple("Active", R.color.colorDarkBlue, R.color.colorLightBlue),
        Triple("Recovered", R.color.colorDarkGreen, R.color.colorLightGreen),
        Triple("Deceased", R.color.colorDarkGrey, R.color.colorLightGrey))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TotalCountViewHolder{
        context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
         return TotalCountViewHolder(inflater.inflate(R.layout.item_total_count, parent, false))
    }

    override fun getItemCount() = 4

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(newData : Statewise){
        data = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TotalCountViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TotalCountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val headerTextView: TextView = itemView.findViewById(R.id.tv_header)
        private val todayCountTextView: TextView = itemView.findViewById(R.id.tv_today_count)
        private val totalCountTextView: TextView = itemView.findViewById(R.id.tv_total_count)
        private val rootLayout : ConstraintLayout = itemView.findViewById(R.id.root_total_count)

        fun bind(position: Int) {
            val details = cardProperties[position]
            rootLayout.setBackgroundColor(rootLayout.context.resources.getColor(details.third))

            with(headerTextView) {
                text = details.first
                setTextColor(context.resources.getColor(details.second))
            }

            with(todayCountTextView) {
                text = when (position) {
                    0 -> data?.deltaConfirmed ?: ""
                    2 -> data?.deltaRecovered ?: ""
                    3 -> data?.deltaDeaths ?: ""
                    else -> ""
                }
                setTextColor(context.resources.getColor(details.second))
            }
            with(totalCountTextView) {
                text = when (position) {
                    0 -> data?.confirmed ?: ""
                    1 -> data?.active ?: ""
                    2 -> data?.recovered ?: ""
                    3 -> data?.deaths ?: ""
                    else -> ""
                }
                setTextColor(context.resources.getColor(details.second))
            }
        }
    }
}