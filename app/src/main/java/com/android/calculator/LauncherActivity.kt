package com.android.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.calculator.actions.BaseAction
import com.android.calculator.components.screen.Calculator
import com.android.calculator.components.screen.Length
import com.android.calculator.state.LengthState
import com.android.calculator.state.viewmodel.CalculatorViewModel
import com.android.calculator.ui.theme.CalculatorTheme

class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
//                val viewModel = viewModel<CalculatorViewModel>()
//                val state = viewModel.calculatorState
//                Calculator(
//                    state = state,
//                    onAction = viewModel::onAction,
//                    buttonSpacing = 10.dp,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.primary)
//                        .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
//                )
                Length(
                    state = LengthState(), buttonSpacing = 10.dp, modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(bottom = 20.dp, start = 15.dp, end = 15.dp)
                ) {

                }
            }
        }
    }
}