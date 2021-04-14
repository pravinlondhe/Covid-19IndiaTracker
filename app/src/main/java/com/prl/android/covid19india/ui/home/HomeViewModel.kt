package com.prl.android.covid19india.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prl.android.covid19india.data.localdatabase.Covid19IndiaDb
import com.prl.android.covid19india.data.model.country.Statewise
import com.prl.android.covid19india.data.network.Covid19ApiService
import com.prl.android.covid19india.data.network.DataBound
import com.prl.android.covid19india.data.network.Loading
import com.prl.android.covid19india.data.network.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel private constructor(private val db: Covid19IndiaDb) : ViewModel() {

    private val covid19ApiService: Covid19ApiService = Covid19ApiService()
    var covid19TotalCountData: MutableLiveData<DataBound<Statewise>> = MutableLiveData()
        private set
    var covid19StateWiseData: MutableLiveData<DataBound<List<Statewise>>> = MutableLiveData()
        private set

    fun getCovid19AllData() {
        viewModelScope.launch(Dispatchers.IO) {
            covid19TotalCountData.postValue(Loading())
            covid19StateWiseData.postValue(Loading())
            val response = covid19ApiService.getCovid19DataAsync().await()
            db.dailyDao().addDailyCases(response.dailyCasesTimeSeries)
            covid19TotalCountData.postValue(Success<Statewise>(response.statewise.filter { it.stateCode == "TT" }[0]))
            covid19StateWiseData.postValue(Success<List<Statewise>>(response.statewise.filter { it.stateCode != "TT" }))
            val cal: Calendar = Calendar.getInstance()
            cal.set(2020, 9 - 1, 22)
            val date = cal.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.US)
            println("date:$date")
            println("from db:${db.dailyDao().getDailyCasesForDate("22 $date 2020")}")
            println("from db descending cases:${db.dailyDao().descendingConfirmedCases()}")
        }
    }

    companion object {
        fun createAndAttach(db: Covid19IndiaDb): HomeViewModel {
            return HomeViewModel(db)
        }
    }
}