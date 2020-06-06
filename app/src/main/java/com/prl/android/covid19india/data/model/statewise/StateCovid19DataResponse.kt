package com.prl.android.covid19india.data.model.statewise

import com.google.gson.annotations.SerializedName

class StateCovid19DataResponse : ArrayList<StateCovid19DataResponseItem>()

data class StateCovid19DataResponseItem(
    val districtData: List<DistrictData>,
    val state: String
)

data class DistrictData(
    val confirmed: Int,
    val delta: Delta,
    val district: String,
    @SerializedName("lastupdatedtime")
    val lastUpdatedTime: String
)

data class Delta(
    val confirmed: Int
)