package com.prl.android.covid19india.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.prl.android.covid19india.R
import com.prl.android.covid19india.data.localdatabase.Covid19IndiaDb
import com.prl.android.covid19india.data.model.country.Statewise
import com.prl.android.covid19india.data.network.Loading
import com.prl.android.covid19india.data.network.Success
import com.prl.android.covid19india.databinding.FragmentHomeBinding
import com.prl.android.covid19india.ui.home.component.StateWiseAdapter
import com.prl.android.covid19india.ui.home.component.TotalCountAdapter
import com.prl.android.covid19india.ui.home.details.StateDetailFragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel

    class HomeViewModelFactory(private val context: Context) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val db =
                Room.databaseBuilder(context, Covid19IndiaDb::class.java, "covid19IndiaDb").build()
            return HomeViewModel.createAndAttach(db) as T
        }
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = HomeViewModelFactory(requireContext()).create(HomeViewModel::class.java)
        with(binding.rvTotalCount) {
//            layoutManager = GridLayoutManager(activity, 4)
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = adapterTotalCount
        }
        with(binding.rvStateWise) {
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
                        binding.pbHome.visibility = View.VISIBLE
                    }
                    is Success<*> -> {
                        binding.pbHome.visibility = View.GONE
                        adapterTotalCount.updateData(data.data as Statewise)
                        binding.tvLastUpdated.text = getString(
                            R.string.last_updated_time,
                            data.data.lastUpdatedTime
                        )
                    }
                    is Error -> {
                        binding.pbHome.visibility = View.GONE
                    }
                    else -> {
                        //Nothing to do
                    }
                }
            })
        //Statewise data
        homeViewModel.covid19StateWiseData.observe(viewLifecycleOwner,
            Observer { data ->
                when (data) {
                    is Loading -> {
                        binding.pbHome.visibility = View.VISIBLE
                    }
                    is Success<*> -> {
                        binding.pbHome.visibility = View.GONE
                        adapterStateWise.updateData(data.data as List<Statewise>)
                    }
                    is Error -> {
                        binding.pbHome.visibility = View.GONE
                    }
                }
            })
    }
}
