package com.prl.android.covid19india.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prl.android.covid19india.data.model.country.DailyCases

@Database(
    entities = [DailyCases::class],
    version = 1
)
abstract class Covid19IndiaDb : RoomDatabase() {
    abstract fun dailyDao(): DailyCasesDao
}