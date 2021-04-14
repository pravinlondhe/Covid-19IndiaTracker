package com.prl.android.covid19india.data.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prl.android.covid19india.data.model.country.DailyCases

@Dao
interface DailyCasesDao {

    @Query("SELECT * from DailyCases")
    fun getAllDailyCases(): List<DailyCases>

    @Query("SELECT * from DailyCases where date=:input")
    fun getDailyCasesForDate(input: String): DailyCases

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDailyCases(dailyCases: List<DailyCases>)

    @Query("SELECT * from DailyCases ORDER BY dailyConfirmed DESC")
    fun descendingConfirmedCases(): List<DailyCases>
}