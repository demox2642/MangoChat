package com.example.presentation.screens.phone

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.auth.presentation.R
import com.example.presentation.AuthScreens
import com.example.presentation.model.ScreenState
import com.example.presentation.theme.AppTheme
import com.example.presentation.views.AlertDialog
import com.simon.xmaterialccp.component.MaterialCountryCodePicker
import com.simon.xmaterialccp.data.ccpDefaultColors
import com.simon.xmaterialccp.data.utils.checkPhoneNumber
import com.simon.xmaterialccp.data.utils.getDefaultLangCode
import com.simon.xmaterialccp.data.utils.getDefaultPhoneCode
import com.simon.xmaterialccp.data.utils.getFlags
import com.simon.xmaterialccp.data.utils.getLibCountries
import com.simon.xmaterialccp.data.utils.setLocale

@Preview()
@Composable
fun PhoneScreenPreview() {
}

@Composable
fun PhoneScreen(navController: NavHostController) {
    val viewModel: PhoneScreenViewModel = hiltViewModel()

    val screenState by viewModel.screenState.collectAsState()
    val errorText by viewModel.errorText.collectAsState()

    Scaffold(
        modifier = Modifier.padding(16.dp),
        containerColor = AppTheme.colors.systemBackgroundPrimary) { padding ->
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
                    PhoneContent { phone ->
                        viewModel.sendPhone(phone = phone) {
                            navController.navigate(AuthScreens.CodeScreen.route + "/$phone")
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
fun PhoneContent(sendPhone: (String) -> Unit) {
    val context = LocalContext.current
    var phoneCode by remember { mutableStateOf(getDefaultPhoneCode(context)) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode(context)) }
    var isValidPhone by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        setLocale(context, "en")
    }

    MaterialCountryCodePicker(
        pickedCountry = {
            phoneCode = it.countryPhoneCode
            defaultLang = it.countryCode
        },
        defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
        error = !isValidPhone,
        text = phoneNumber.value,
        onValueChange = { phoneNumber.value = it },
        searchFieldPlaceHolderTextStyle = AppTheme.typography.body1,
        searchFieldTextStyle = AppTheme.typography.body1,
        phonenumbertextstyle = AppTheme.typography.body1,
        countrytextstyle = AppTheme.typography.body1,
        countrycodetextstyle = AppTheme.typography.body1,
        showErrorText = true,
        showCountryCodeInDIalog = true,
        showDropDownAfterFlag = true,
        textFieldShapeCornerRadiusInPercentage = 40,
        searchFieldShapeCornerRadiusInPercentage = 40,
        appbartitleStyle = AppTheme.typography.caption,
        countryItemBgShape = RoundedCornerShape(5.dp),
        showCountryFlag = true,
        showCountryCode = true,
        showClearIcon = true,
        flagShape = RoundedCornerShape(10f),
        isEnabled = true,
        showErrorIcon = true,
        colors =
            ccpDefaultColors(
                primaryColor = AppTheme.colors.systemGraphOnPrimary,
                errorColor = AppTheme.colors.colorBackgroundAlert,
                backgroundColor = AppTheme.colors.systemBackgroundSecondary,
                surfaceColor = MaterialTheme.colorScheme.surface,
                outlineColor = MaterialTheme.colorScheme.outline,
                disabledOutlineColor = MaterialTheme.colorScheme.outline.copy(0.1f),
                unfocusedOutlineColor = MaterialTheme.colorScheme.onBackground.copy(0.3f),
                textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                cursorColor = MaterialTheme.colorScheme.primary,
                topAppBarColor = MaterialTheme.colorScheme.surface,
                countryItemBgColor = MaterialTheme.colorScheme.surface,
                searchFieldBgColor = MaterialTheme.colorScheme.surface,
                dialogNavIconColor = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                dropDownIconTint = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            ),
        errorTextStyle = MaterialTheme.typography.bodySmall,
        errorModifier = Modifier.padding(top = 3.dp, start = 10.dp),
        dialogItemBuilder = { country, onclick ->

            Row(
                Modifier
                    .padding(16.dp, 12.dp)
                    .fillMaxWidth()
                    .clickable {
                        onclick()
                    },
            ) {
                Image(
                    painterResource(
                        id =
                            getFlags(
                                country.countryCode,
                            ),
                    ),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = country.cNames)
            }
        },
    )

    val fullPhoneNumber = "$phoneCode${phoneNumber.value}"
    val checkPhoneNumber =
        checkPhoneNumber(
            phone = phoneNumber.value,
            fullPhoneNumber = fullPhoneNumber,
            countryCode = defaultLang,
        )
    Spacer(modifier = Modifier.height(20.dp))
    Button(
        onClick = {
            isValidPhone = checkPhoneNumber
            Log.e("PhoneScreen", "valid = $isValidPhone  ${phoneNumber.value}")
            if (isValidPhone) {
                sendPhone(phoneNumber.value)
            }
        },
        modifier =
            Modifier
                .fillMaxWidth(0.8f),
    ) {
        Text(text = stringResource(id = R.string.send_phone))
    }
}
