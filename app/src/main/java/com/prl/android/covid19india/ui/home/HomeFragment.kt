package com.prl.android.covid19india.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prl.android.covid19india.R
import com.prl.android.covid19india.ui.home.component.StateWiseAdapter
import com.prl.android.covid19india.ui.home.component.TotalCountAdapter
import com.prl.android.covid19india.ui.home.details.StateDetailFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val adapterTotalCount: TotalCountAdapter = TotalCountAdapter(null)
    private val adapterStateWise = StateWiseAdapter(emptyList()){
        val bundle = Bundle()
        bundle.putParcelable(StateDetailFragment.STATE_DETAIL_KEY, it)
        findNavController(this).navigate(R.id.action_navigation_home_to_stateDetailFragment, bundle)
    }
    private lateinit var lastUpdateTv : TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val totalCountRv = root.findViewById<RecyclerView>(R.id.rv_total_count)
        lastUpdateTv = root.findViewById(R.id.tv_last_updated)
        totalCountRv.layoutManager = GridLayoutManager(activity, 4)
        totalCountRv.setHasFixedSize(true)
        totalCountRv.adapter = adapterTotalCount

        val stateWiseRv = root.findViewById<RecyclerView>(R.id.rv_state_wise)
        stateWiseRv.layoutManager = LinearLayoutManager(activity)
        stateWiseRv.setHasFixedSize(true)
        stateWiseRv.adapter = adapterStateWise
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.getData()

        homeViewModel.covid19TotalCountData.observe(viewLifecycleOwner,
            Observer {
                adapterTotalCount.updateData(it)
                lastUpdateTv.text = getString(R.string.last_updated_time, it.lastUpdatedTime)
            })

        homeViewModel.covid19StateWiseData.observe(viewLifecycleOwner,
        Observer {
            adapterStateWise.updateData(it)
        })
    }
}
