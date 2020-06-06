package com.prl.android.covid19india.ui.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prl.android.covid19india.data.Covid19ApiService
import com.prl.android.covid19india.data.model.statewise.StateCovid19DataResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StateDetailViewModel : ViewModel() {
    var stateDetail : MutableLiveData<StateCovid19DataResponseItem> = MutableLiveData()
        private set
    private lateinit var covid19StateDetail : List<StateCovid19DataResponseItem>

    fun setState(state: String){
        GlobalScope.launch(Dispatchers.IO) {
            covid19StateDetail = Covid19ApiService().getStateWiseData().await()
            val states = covid19StateDetail.filter {
                it.state.equals(state, true)
            }
            if(states.isNotEmpty()) {
                stateDetail.postValue(states[0])
            }
        }
    }
}
