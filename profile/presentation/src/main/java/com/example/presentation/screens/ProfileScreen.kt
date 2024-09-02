package com.example.presentation.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.models.ProfileInfo
import com.example.presentation.model.ScreenState
import com.example.presentation.theme.AppTheme
import com.example.presentation.views.AlertDialog
import com.example.presentation.views.AnswerAlertDialog
import com.example.presentation.views.BackPressHandler
import com.example.profile.presentation.R
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview()
@Composable
fun ProfileScreenPreview() {
}

@Composable
fun ProfileScreen() {
    val viewModel: ProfileScreenViewModel = hiltViewModel()

    val screenState by viewModel.screenState.collectAsState()
    val errorText by viewModel.errorText.collectAsState()
    val userInfo by viewModel.userInfo.collectAsState()
    val canEditState by viewModel.canEditState.collectAsState()
    val datePickerDialog = remember { mutableStateOf(false) }
    val confirmDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.padding(16.dp),
        topBar = {
            if (screenState == ScreenState.SUCCESS) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    if (canEditState) {
                        Icon(painterResource(id = R.drawable.ic_save), contentDescription = "edit")
                    } else {
                        IconButton(onClick = viewModel::changeEdit) {
                            Icon(Icons.Default.Edit, contentDescription = "edit")
                        }
                    }
                }
            }
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            if (canEditState) {
                BackPressHandler(
                    onBackPressed = { confirmDialog.value = true },
                )
            }
            if (datePickerDialog.value) {
                DatePickerModal(
                    userInfo = userInfo!!,
                    onDateSelected = viewModel::changeUserInfo,
                    onDismiss = { datePickerDialog.value = false },
                )
            }
            if (confirmDialog.value) {
                Dialog(
                    onDismissRequest = {},
                    content = {
                        AnswerAlertDialog(
                            title = "WARNING!",
                            message = "Save new profile data?",
                            closeDialog = {
                                viewModel.getProfileInfo()
                                viewModel.changeEdit()
                                confirmDialog.value = false
                            },
                            confirm = {
                                viewModel.updateProfile()
                                viewModel.changeEdit()
                                confirmDialog.value = false
                            },
                        )
                    },
                )
            }
            when (screenState) {
                ScreenState.SUCCESS -> {
                    ProfileContent(
                        userInfo,
                        canEditState,
                        changeDatePickerState = { datePickerDialog.value = true },
                        viewModel::changeUserInfo,
                        showConfirmDialog = { confirmDialog.value = true },
                    )
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
fun ProfileContent(
    userInfo: ProfileInfo?,
    canEditState: Boolean,
    changeDatePickerState: () -> Unit,
    changeProfile: (ProfileInfo) -> Unit,
    showConfirmDialog: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Image(
                painterResource(id = R.drawable.ic_profile_photo),
                contentDescription = "profile_photo",
                modifier = Modifier.size(64.dp),
            )
        }
        Column {
            Text(text = "username:${userInfo?.username}")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "phone:${userInfo?.phone}")
        }
    }
    Spacer(modifier = Modifier.height(20.dp))

    TextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    if (canEditState) {
                        changeDatePickerState()
                    }
                }.clip(RoundedCornerShape(8)),
        value =
            if (userInfo?.birthday == null) {
                ""
            } else {
                userInfo.birthday!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            },
        onValueChange = { },
        readOnly = true,
        textStyle = AppTheme.typography.body1,
        trailingIcon = {
            Icon(
                modifier =
                    Modifier.clickable {
                        if (canEditState) {
                            changeDatePickerState()
                        }
                    },
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = "",
            )
        },
        label = {
            Text(
                text = "birthday",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.systemTextSecondary,
            )
        },
        enabled = false,
    )
    Spacer(modifier = Modifier.height(12.dp))
    TextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    if (canEditState) {
                        changeDatePickerState()
                    }
                }.clip(RoundedCornerShape(8)),
        value = userInfo?.city ?: "",
        onValueChange = { changeProfile(userInfo!!.copy(city = it)) },
        textStyle = AppTheme.typography.body1,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_city),
                contentDescription = "",
            )
        },
        label = {
            Text(
                text = "city",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.systemTextSecondary,
            )
        },
        enabled = canEditState,
    )
    Spacer(modifier = Modifier.height(12.dp))
    TextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    if (canEditState) {
                        changeDatePickerState()
                    }
                }.clip(RoundedCornerShape(8)),
        value = userInfo?.instagram ?: "",
        onValueChange = { changeProfile(userInfo!!.copy(instagram = it)) },
        textStyle = AppTheme.typography.body1,
        trailingIcon = {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_instagram),
                contentDescription = "",
            )
        },
        label = {
            Text(
                text = "instagram",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.systemTextSecondary,
            )
        },
        enabled = canEditState,
    )

    Spacer(modifier = Modifier.height(12.dp))
    TextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    if (canEditState) {
                        changeDatePickerState()
                    }
                }.clip(RoundedCornerShape(8)),
        value = userInfo?.vk ?: "",
        onValueChange = { changeProfile(userInfo!!.copy(vk = it)) },
        textStyle = AppTheme.typography.body1,
        trailingIcon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_vk),
                contentDescription = "",
            )
        },
        label = {
            Text(
                text = "vk",
                style = AppTheme.typography.body1,
                color = AppTheme.colors.systemTextSecondary,
            )
        },
        enabled = canEditState,
    )
    if (canEditState) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    showConfirmDialog()
                },
                modifier =
                    Modifier
                        .fillMaxWidth(0.8f),
            ) {
                Text(text = stringResource(id = R.string.update_profile))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    userInfo: ProfileInfo,
    onDateSelected: (ProfileInfo) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let {
                    onDateSelected(
                        userInfo.copy(
                            birthday =
                                LocalDate.ofEpochDay(Duration.ofMillis(datePickerState.selectedDateMillis!!).toDays()),
                        ),
                    )
                }

                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}
