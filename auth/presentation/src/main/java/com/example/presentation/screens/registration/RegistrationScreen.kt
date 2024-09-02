package com.example.presentation.screens.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
import com.example.presentation.model.ScreenState
import com.example.presentation.theme.AppTheme
import com.example.presentation.views.AlertDialog

@Preview()
@Composable
fun RegistrationScreenPreview() {
}

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    args: String?,
) {
    val viewModel: RegistrationViewModel = hiltViewModel()

    val screenState by viewModel.screenState.collectAsState()
    val errorText by viewModel.errorText.collectAsState()

    Scaffold(
        modifier = Modifier.padding(16.dp),
        containerColor = AppTheme.colors.systemBackgroundPrimary,
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (screenState) {
                ScreenState.SUCCESS -> {
                    RegistrationContent(phone = args!!) { phone, userName, login ->
                        viewModel.registration(phone, userName, login) {
                            navController.navigate("chat_screen")
                        }
                    }
                }
                ScreenState.ERROR -> {
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
                ScreenState.LOADING -> {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = AppTheme.colors.systemBackgroundSecondary,
                        trackColor = AppTheme.colors.systemGraphOnPrimary,
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationContent(
    phone: String,
    sendRegistrationData: (String, String, String) -> Unit,
) {
    val userName = rememberSaveable { mutableStateOf("") }
    val userLogin = rememberSaveable { mutableStateOf("") }
    Text(text = stringResource(id = R.string.user_name), style = AppTheme.typography.h3)
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        modifier =
            Modifier
                .padding()
                .fillMaxWidth(),
        maxLines = 1,
        value = userName.value,
        shape = RoundedCornerShape(30),
        onValueChange = {
            if (it.length < 27) {
                userName.value = it
            }
        },
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(text = stringResource(id = R.string.user_login), style = AppTheme.typography.h3)
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        modifier =
            Modifier
                .padding()
                .fillMaxWidth(),
        value = userLogin.value,
        maxLines = 1,
        shape = RoundedCornerShape(30),
        onValueChange = {
            val regex = Regex("[^0-9A-Za-z_-]")
            if (it.length < 27) {
                userLogin.value = regex.replace(it, "")
            }
        },
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            sendRegistrationData(phone, userName.value, userLogin.value)
        },
        enabled = userName.value.isNotEmpty() && userLogin.value.isNotEmpty(),
        modifier =
            Modifier
                .fillMaxWidth(0.8f),
    ) {
        Text(text = stringResource(id = R.string.create_user))
    }
}
