package com.example.presentation.screens.chat_detail

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.chat.presentation.R
import com.example.domain.models.Chat
import com.example.domain.models.Message
import com.example.presentation.model.ScreenState
import com.example.presentation.theme.AppTheme
import com.example.presentation.views.AlertDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.time.format.DateTimeFormatter

@Composable
fun ChatDetailScreen(args: Long?) {
    val viewModel: ChatDetailViewModel = hiltViewModel()

    val screenState by viewModel.screenState.collectAsState()
    val chat by viewModel.chat.collectAsState()
    args?.let { id ->
        viewModel.getChatDetail(id)
    }

    when (screenState) {
        ScreenState.ERROR -> {
            Dialog(
                onDismissRequest = {},
                content = {
                    AlertDialog(
                        title = "ERROR!",
                        message = "OOoops! Oo",
                        closeDialog = {},
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
        ScreenState.SUCCESS -> {
            ChatDetailContent(chat!!)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChatDetailContent(chat: Chat) {
    val message =
        remember {
            mutableStateOf("")
        }

    Scaffold(
        containerColor = AppTheme.colors.systemBackgroundPrimary,
        topBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(AppTheme.colors.colorBackgroundIndigo)
                        .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GlideImage(
                    modifier =
                        Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                    model = chat.avatarUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(text = chat.name, style = AppTheme.typography.h2, color = AppTheme.colors.systemTextOnPrimary)
            }
        },
        bottomBar = {
            ChatBottomBar(message = message.value, callback = { message.value = it })
        },
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(chat.message.sortedBy { it.date }) { message ->
                if (message.selfMessage) {
                    SelfMessage(message = message)
                } else {
                    SomeoneElseMessage(message = message)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun ChatBottomBar(
    modifier: Modifier = Modifier,
    message: String,
    callback: (String) -> Unit,
) {
    val pickPictureLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent(),
        ) { imageUri ->
            if (imageUri != null) {
            }
        }

    val multiplePermissionsState =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO,
                ),
            )
        } else {
            rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ),
            )
        }

    Row(
        modifier.height(56.dp).background(AppTheme.colors.systemBackgroundSecondary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {
                if (multiplePermissionsState.allPermissionsGranted) {
                    pickPictureLauncher.launch("*/*")
                } else {
                    multiplePermissionsState.launchMultiplePermissionRequest()
                }
            },
            Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
        ) {
            Icon(
                painterResource(R.drawable.ic_attach),
                "Attach file",
                tint = AppTheme.colors.colorGraphAqua,
            )
        }

        BasicTextField(
            message.capitalize(),
            { callback(it) },
            Modifier.weight(1f),
            textStyle = AppTheme.typography.body1,
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Send,
                ),
            keyboardActions =
                KeyboardActions(
                    onSend = { },
                ),
            singleLine = true,
        ) { innerTextField ->
            Box(
                Modifier.fillMaxHeight(),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (message.isEmpty()) {
                    Text(
                        stringResource(R.string.enter_message),
                        style = AppTheme.typography.body1,
                        color = AppTheme.colors.systemTextSecondary,
                    )
                }
                innerTextField()
            }
        }

        IconButton(
            { },
            Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
        ) {
            Icon(
                painterResource(R.drawable.ic_send),
                "Send",
                tint = AppTheme.colors.colorGraphAqua,
            )
        }
    }
}

@Composable
fun SelfMessage(message: Message) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Card(
            modifier =
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 20.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                ),
            colors =
                CardDefaults.cardColors(
                    containerColor = AppTheme.colors.colorBackgroundIndigo.copy(alpha = 0.8f),
                ),
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.End) {
                Column {
                    Text(text = message.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), style = AppTheme.typography.h3)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = message.message, style = AppTheme.typography.body1)
                }
            }
        }
    }
}

@Composable
fun SomeoneElseMessage(message: Message) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Card(
            modifier =
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 0.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                ),
            colors =
                CardDefaults.cardColors(
                    containerColor = AppTheme.colors.colorBackgroundIndigo.copy(alpha = 0.8f),
                ),
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.Start) {
                Column {
                    Text(text = message.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), style = AppTheme.typography.h3)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = message.message, style = AppTheme.typography.body1)
                }
            }
        }
    }
}
