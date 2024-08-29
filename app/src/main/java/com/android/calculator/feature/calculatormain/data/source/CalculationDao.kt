package com.android.calculator.feature.calculatormain.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalculation(calculations: Calculation)

    @Query("SELECT * FROM calculations ORDER BY date DESC")
    fun getAllCalculations(): Flow<List<Calculation>>

    @Query("DELETE FROM calculations WHERE id IN (:calculationIds)")
    suspend fun deleteCalculations(calculationIds: List<Int>)
}
