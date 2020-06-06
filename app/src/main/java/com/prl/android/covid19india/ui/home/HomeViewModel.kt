package com.prl.android.covid19india.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prl.android.covid19india.data.Covid19ApiService
import com.prl.android.covid19india.data.model.country.Statewise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val covid19ApiService: Covid19ApiService = Covid19ApiService()
    var covid19TotalCountData : MutableLiveData<Statewise> = MutableLiveData()
     private set
    var covid19StateWiseData : MutableLiveData<List<Statewise>> = MutableLiveData()
        private set

    fun getData(){
        GlobalScope.launch(Dispatchers.IO){
           val response = covid19ApiService.getCovid19Data().await()
            covid19TotalCountData.postValue(response.statewise.filter { it.stateCode == "TT" }[0])
            covid19StateWiseData.postValue(response.statewise.filter { it.stateCode != "TT" })
        }
    }
}