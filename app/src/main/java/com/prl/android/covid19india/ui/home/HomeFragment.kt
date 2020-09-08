package com.prl.android.covid19india.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.model.country.Statewise
import com.prl.android.covid19india.data.network.Loading
import com.prl.android.covid19india.data.network.Success
import com.prl.android.covid19india.ui.home.component.StateWiseAdapter
import com.prl.android.covid19india.ui.home.component.TotalCountAdapter
import com.prl.android.covid19india.ui.home.details.StateDetailFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val adapterTotalCount: TotalCountAdapter by lazy { TotalCountAdapter(null) }
    private val adapterStateWise = StateWiseAdapter(emptyList()) {
        val bundle = Bundle()
        bundle.putParcelable(StateDetailFragment.STATE_DETAIL_KEY, it)
        findNavController(this).navigate(R.id.action_navigation_home_to_stateDetailFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(rvTotalCount) {
            layoutManager = GridLayoutManager(activity, 4)
            setHasFixedSize(true)
            adapter = adapterTotalCount
        }
        with(rvStateWise) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterStateWise
        }

        homeViewModel.getCovid19AllData()
        initObservers()
    }

    private fun initObservers() {

        //Total count data
        homeViewModel.covid19TotalCountData.observe(viewLifecycleOwner,
            Observer { data ->
                when (data) {
                    is Loading -> {
                        pbHome

                            .visibility = View.VISIBLE
                    }
                    is Success<*> -> {
                        pbHome.visibility = View.GONE
                        adapterTotalCount.updateData(data.data as Statewise)
                        tvLastUpdated.text = getString(
                            R.string.last_updated_time,
                            data.data.lastUpdatedTime
                        )
                    }
                    is Error -> {
                        pbHome.visibility = View.GONE
                    }
                }
            })
        //Statewise data
        homeViewModel.covid19StateWiseData.observe(viewLifecycleOwner,
            Observer { data ->
                when (data) {
                    is Loading -> {
                        pbHome.visibility = View.VISIBLE
                    }
                    is Success<*> -> {
                        pbHome.visibility = View.GONE
                        adapterStateWise.updateData(data.data as List<Statewise>)
                    }
                    is Error -> {
                        pbHome.visibility = View.GONE
                    }
                }
            })
    }
}
