package com.android.calculator.feature.calculatormain.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.calculator.feature.calculatormain.domain.model.Calculation

@Database(entities = [Calculation::class], version = 1, exportSchema = false)
abstract class CalculatorDatabase : RoomDatabase() {
    abstract val calculationDao: CalculationDao

    companion object {
        const val DATABASE_NAME = "calculator_database"
    }
}