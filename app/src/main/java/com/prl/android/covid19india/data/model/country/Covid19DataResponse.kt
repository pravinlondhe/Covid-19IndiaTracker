package com.prl.android.covid19india.data.model.country

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Covid19DataResponse(
    @SerializedName("cases_time_series")
    val casesTimeSeries: List<CasesTimeSeries>,
    val statewise: List<Statewise>,
    val tested: List<Tested>
)

data class CasesTimeSeries(
    @SerializedName("dailyconfirmed")
    val dailyConfirmed: String,
    @SerializedName("dailydeceased")
    val dailyDeceased: String,
    @SerializedName("dailyrecovered")
    val dailyRecovered: String,
    val date: String,
    @SerializedName("totalconfirmed")
    val totalConfirmed: String,
    @SerializedName("totaldeceased")
    val totalDeceased: String,
    @SerializedName("totalrecovered")
    val totalRecovered: String
)

data class Statewise(
    val active: String?,
    val confirmed: String?,
    val deaths: String?,
    @SerializedName("deltaconfirmed")
    val deltaConfirmed: String?,
    @SerializedName("deltadeaths")
    val deltaDeaths: String?,
    @SerializedName("deltarecovered")
    val deltaRecovered: String?,
    @SerializedName("lastupdatedtime")
    val lastUpdatedTime: String?,
    val recovered: String?,
    val state: String?,
    @SerializedName("statecode")
    val stateCode: String?,
    @SerializedName("statenotes")
    val stateNotes: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        active = parcel.readString(),
        confirmed = parcel.readString(),
        deaths = parcel.readString(),
        deltaConfirmed = parcel.readString(),
        deltaDeaths = parcel.readString(),
        deltaRecovered = parcel.readString(),
        lastUpdatedTime = parcel.readString(),
        recovered = parcel.readString(),
        state = parcel.readString(),
        stateCode = parcel.readString(),
        stateNotes = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.let{
            with(it){
                writeString(active)
                writeString(confirmed)
                writeString(deaths)
                writeString(deltaConfirmed)
                writeString(deltaDeaths)
                writeString(deltaRecovered)
                writeString(lastUpdatedTime)
                writeString(recovered)
                writeString(state)
                writeString(stateCode)
                writeString(stateNotes)
            }
        }
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Statewise> {
        override fun createFromParcel(parcel: Parcel): Statewise {
            return Statewise(parcel)
        }

        override fun newArray(size: Int): Array<Statewise?> {
            return arrayOfNulls(size)
        }
    }
}

data class Tested(
    @SerializedName("positivecasesfromsamplesreported")
    val positiveCasesFromSamplesReported: String,
    @SerializedName("samplereportedtoday")
    val sampleReportedToday: String,
    val source: String,
    @SerializedName("testsconductedbyprivatelabs")
    val testsConductedByPrivateLabs: String,
    @SerializedName("totalindividualstested")
    val totalIndividualsTested: String,
    @SerializedName("totalpositivecases")
    val totalPositiveCases: String,
    @SerializedName("totalsamplestested")
    val totalSamplesTested: String,
    @SerializedName("updatetimestamp")
    val updateTimestamp: String
)