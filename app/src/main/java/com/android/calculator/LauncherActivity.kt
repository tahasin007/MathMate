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
import com.android.calculator.components.screen.Length
import com.android.calculator.state.viewmodel.LengthViewModel
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

                val viewModel = viewModel<LengthViewModel>()
                val state = viewModel.lengthState
                Length(
                    state = state, buttonSpacing = 10.dp, modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(bottom = 20.dp, start = 15.dp, end = 15.dp),
                    onAction = viewModel::onAction
                )
            }
        }
    }
}