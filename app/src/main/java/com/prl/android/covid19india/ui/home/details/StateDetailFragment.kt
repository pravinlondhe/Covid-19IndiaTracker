package com.prl.android.covid19india.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prl.android.covid19india.MainActivity
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.model.country.Statewise
import com.prl.android.covid19india.ui.home.component.TotalCountAdapter

class StateDetailFragment : Fragment() {

    private lateinit var viewModel: StateDetailViewModel
    private lateinit var stateLastUpdatedTv: TextView
    private lateinit var stateTotalCountRv: RecyclerView
    private lateinit var districtDetailRv: RecyclerView
    private lateinit var districtAdapter: StateDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.state_detail_fragment, container, false)
        stateLastUpdatedTv = view.findViewById(R.id.tv_state_last_updated)
        stateTotalCountRv = view.findViewById(R.id.rv_state_total_count)
        districtDetailRv = view.findViewById(R.id.rv_district_detail)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StateDetailViewModel::class.java)
        val stateData: Statewise? = arguments?.getParcelable(STATE_DETAIL_KEY)
        stateData?.let {
            (activity as MainActivity).setMaterialTitle(stateData.state ?: "Not available   ")
            stateLastUpdatedTv.text =
                getString(R.string.last_updated_time, stateData.lastUpdatedTime)
        }

        stateTotalCountRv.setHasFixedSize(true)
        stateTotalCountRv.layoutManager = GridLayoutManager(activity, 4)
        stateTotalCountRv.adapter = TotalCountAdapter(stateData)

        viewModel.setState(stateData?.state ?: "")

        districtDetailRv.setHasFixedSize(true)
        districtDetailRv.layoutManager = LinearLayoutManager(activity)
        districtAdapter = StateDetailAdapter(null)
        districtDetailRv.adapter = districtAdapter

        viewModel.stateDetail.observe(viewLifecycleOwner,
            Observer {
                districtAdapter.updateStateDetail(it)
            })
    }

    companion object {
        const val STATE_DETAIL_KEY = "state_detail"
    }
}
