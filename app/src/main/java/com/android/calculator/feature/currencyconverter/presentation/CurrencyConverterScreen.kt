package com.android.calculator.feature.currencyconverter.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.calculator.CalculatorApplication
import com.android.calculator.feature.settings.domain.model.SettingsState
import com.android.calculator.ui.shared.components.AppBar
import com.android.calculator.ui.shared.components.CalculatorGridSimple
import com.android.calculator.ui.shared.factory.ButtonFactory
import com.android.calculator.utils.CurrencyDefaults
import com.android.calculator.utils.ScreenType

@Composable
fun CurrencyConverterScreen(
    app: CalculatorApplication,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    configuration: SettingsState
) {
    val viewModel = app.currencyConverterViewModel
    var expanded by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        AppBar(screen = ScreenType.Currency.screen) {
            navController.navigate(ScreenType.CalculatorMain.route)
        }
    }) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            BoxWithConstraints(
                modifier = modifier.fillMaxSize()
            ) {
                val totalHeight = maxHeight
                val firstColumnHeight = totalHeight * 0.5f
                val secondColumnHeight = totalHeight * 0.50f
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .height(firstColumnHeight)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = .25f))
                                .padding(all = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(modifier = Modifier.weight(0.75f)) {
                                        Image(
                                            painter = painterResource(
                                                id = CurrencyDefaults.getFlagDrawable(
                                                    "USD"
                                                )
                                            ),
                                            contentDescription = "USD",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(5.dp))
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Column {
                                            Text(
                                                text = "From",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.W500,
                                                color = MaterialTheme.colorScheme.onSecondary.copy(
                                                    alpha = .75f
                                                )
                                            )
                                            Text(
                                                text = "USD - United States Dollar",
                                                fontSize = 16.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                    Row(modifier = Modifier.weight(0.25f)) {
                                        TextButton(onClick = { }) {
                                            Text(
                                                text = "Change",
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.5.dp))
                                Text(
                                    text = "1 USD = 120 BDT",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "3000",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 30.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(25.dp))

                            // Box to overlay the icon on top of the divider
                            Box(modifier = Modifier.fillMaxWidth()) {
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                )
                                Icon(
                                    painter = painterResource(id = CurrencyDefaults.getFlagDrawable("USD")),
                                    contentDescription = "Icon",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center)
                                        .offset(y = (-20).dp) // Move icon up by half of its height
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                            }

                            Spacer(modifier = Modifier.height(25.dp))

                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(modifier = Modifier.weight(0.75f)) {
                                        Image(
                                            painter = painterResource(
                                                id = CurrencyDefaults.getFlagDrawable(
                                                    "USD"
                                                )
                                            ),
                                            contentDescription = "USD",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(5.dp))
                                        )
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Column {
                                            Text(
                                                text = "From",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.W500,
                                                color = MaterialTheme.colorScheme.onSecondary.copy(
                                                    alpha = .75f
                                                )
                                            )
                                            Text(
                                                text = "USD - United States Dollar",
                                                fontSize = 16.sp,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                    Row(modifier = Modifier.weight(0.25f)) {
                                        TextButton(onClick = { }) {
                                            Text(
                                                text = "Change",
                                                color = MaterialTheme.colorScheme.onSecondary
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.5.dp))
                                Text(
                                    text = "1 USD = 120 BDT",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "3000",
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 30.sp
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .height(secondColumnHeight)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        val buttons = ButtonFactory()
                        CalculatorGridSimple(
                            buttons = buttons.getButtons(ScreenType.Currency),
                            onAction = viewModel::onAction,
                            configuration = configuration
                        )
                    }
                }
            }
        }
    }
}