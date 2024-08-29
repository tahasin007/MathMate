package com.android.calculator.feature.calculatormain.presentation.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.calculator.feature.calculatormain.domain.model.Calculation
import com.android.calculator.feature.calculatormain.domain.usecase.CalculationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val calculationUseCases: CalculationUseCases
) : ViewModel() {

    private val _calculations = mutableStateOf<Map<String, List<Calculation>>>(emptyMap())
    val calculations: State<Map<String, List<Calculation>>> = _calculations

    private var getCalculationsJob: Job? = null

    init {
        getCalculations()
    }

    private fun getCalculations() {
        getCalculationsJob?.cancel()
        getCalculationsJob = calculationUseCases.getCalculations().onEach { calculations ->
            // Group by formatted date
            _calculations.value = calculations
                .groupBy {
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date(it.date))
                }
        }.launchIn(viewModelScope)
    }

    fun deleteSelectedCalculations(calculations: List<Calculation>) {
        viewModelScope.launch {
            calculationUseCases.deleteSelectedCalculations(calculations)
        }
    }
}