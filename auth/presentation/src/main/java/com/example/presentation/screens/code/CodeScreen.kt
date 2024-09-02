package com.example.presentation.screens.code

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.auth.presentation.R
import com.example.presentation.AuthScreens
import com.example.presentation.model.ScreenState
import com.example.presentation.screens.phone.PhoneScreenViewModel
import com.example.presentation.theme.AppTheme
import com.example.presentation.views.AlertDialog
import com.example.presentation.views.CodeTextField

@Preview()
@Composable
fun CodeScreenPreview() {
}

@Composable
fun CodeScreen(
    navController: NavHostController,
    args: String?,
) {
    val viewModel: CodeScreenViewModel = hiltViewModel()

    val screenState by viewModel.screenState.collectAsState()
    val errorText by viewModel.errorText.collectAsState()
val code = rememberSaveable { mutableStateOf("") }

    Scaffold(modifier = Modifier.padding(16.dp)) { padding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            when (screenState) {
                ScreenState.ERROR->{
                    Dialog(
                        onDismissRequest = {},
                        content = {
                            AlertDialog(
                                title = "ERROR!",
                                message = errorText,
                                closeDialog = viewModel::closeErrorAlert,
                            )
                        },
                    )
                }
                ScreenState.LOADING->{
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = AppTheme.colors.systemBackgroundSecondary,
                        trackColor = AppTheme.colors.systemGraphOnPrimary,
                    )
                }
                ScreenState.SUCCESS->{
                    Text(text = stringResource(id = R.string.code_screen_totle), style = AppTheme.typography.h3)
                    Spacer(modifier = Modifier.height(20.dp))
                    CodeTextField(otpText = code.value,
                        onOtpTextChange = {vaue,otpInput->
                            val regex = Regex("[^0-9]")
                            if (code.value.length < 6){
                                code.value = regex.replace(vaue,"")
                            }
                        })
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            viewModel.sendCode(
                                phone = args!!,
                                code = code.value
                            ){registerUser->
                                if (registerUser){
                                    navController.navigate("chat_screen")
                                }else{
                                    navController.navigate(AuthScreens.RegistrationScreen.route + "/$args")
                                }

                            }
                        },
                        enabled =code.value.length == 6,
                        modifier =
                        Modifier
                            .fillMaxWidth(0.8f),
                    ) {
                        Text(text = stringResource(id = R.string.send_code))
                    }
                }
            }

        }
    }
}
