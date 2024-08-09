package com.android.calculator.feature.calculatormain.presentation.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.usecase.CalculationUseCases
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryViewModel(private val calculationUseCases: CalculationUseCases) : ViewModel() {
    var groupedCalculations by mutableStateOf<Map<String, List<Calculation>>>(emptyMap())
        private set

    init {
        getCalculations()
    }

    private fun getCalculations() {
        viewModelScope.launch {
            calculationUseCases.getCalculations().collect { calculations ->
                // Group by formatted date
                groupedCalculations = calculations
                    .groupBy {
                        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date(it.date))
                    }
            }
        }
    }

    fun deleteAllCalculations() {
        viewModelScope.launch {
            calculationUseCases.deleteAllCalculations()
        }
    }
}