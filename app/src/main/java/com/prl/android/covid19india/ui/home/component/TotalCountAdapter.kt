package com.prl.android.covid19india.ui.home.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.model.country.Statewise

class TotalCountAdapter(private var data: Statewise?) :
    RecyclerView.Adapter<TotalCountAdapter.TotalCountViewHolder>() {
    private val cardProperties: List<Pair<String, Int>> = listOf(
        Pair("Confirmed", R.drawable.red_gradient),
        Pair("Active", R.drawable.blue_gradient),
        Pair("Recovered", R.drawable.green_gradient),
        Pair("Deceased", R.drawable.gray_gradient)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalCountViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return TotalCountViewHolder(inflater.inflate(R.layout.item_total_count, parent, false))
    }

    override fun getItemCount() = 4

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(newData: Statewise) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TotalCountViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TotalCountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val headerTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_header) }
        private val todayCountTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_today_count) }
        private val totalCountTextView: TextView by lazy { itemView.findViewById<TextView>(R.id.tv_total_count) }
        private val rootLayout: CardView by lazy { itemView.findViewById<CardView>(R.id.cardView) }

        fun bind(position: Int) {
            val details = cardProperties[position]
            rootLayout.setBackgroundResource(details.second)

            with(headerTextView) {
                text = details.first
            }

            with(todayCountTextView) {
                text = when (position) {
                    0 -> data?.deltaConfirmed ?: ""
                    2 -> data?.deltaRecovered ?: ""
                    3 -> data?.deltaDeaths ?: ""
                    else -> ""
                }
            }
            with(totalCountTextView) {
                text = when (position) {
                    0 -> data?.confirmed ?: ""
                    1 -> data?.active ?: ""
                    2 -> data?.recovered ?: ""
                    3 -> data?.deaths ?: ""
                    else -> ""
                }
            }
        }
    }
}