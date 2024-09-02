package com.example.presentation.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.domain.models.Chat
import com.example.presentation.ChatScreens
import com.example.presentation.model.ScreenState
import com.example.presentation.theme.AppTheme
import com.example.presentation.views.AlertDialog

@Preview()
@Composable
fun ChatScreenPreview() {
    val chat =
        Chat(
            avatarUrl = "https://avatars.mds.yandex.net/i?id=a187c288b94c643a42e1c161c8f839f708b832f094cf8978-10869513-images-thumbs&n=13",
            name = "First",
            description = " First chat",
            id = 12,
            message = emptyList(),
        )

    ChatListItem(chat,{})
}

@Composable
fun ChatScreen(navController: NavHostController) {
    val viewModel: ChatScreenViewModel = hiltViewModel()

    val screenState by viewModel.screenState.collectAsState()
    val chatList by viewModel.chatList.collectAsState()

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
            ChatScreenContent(chatList=chatList, toChatDetail = {
                navController.navigate(ChatScreens.ChatDeteilScreen.route+"/$it")
            })
        }
    }
}

@Composable
fun ChatScreenContent(chatList: List<Chat>, toChatDetail:(Long)->Unit) {
    Scaffold(
        modifier = Modifier.padding(16.dp),
        containerColor = AppTheme.colors.systemBackgroundPrimary
        ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(chatList) { chat ->
                ChatListItem(chat = chat, toChatDetail)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChatListItem(chat: Chat, toChatDetail:(Long)->Unit) {
    Card(
        onClick = {toChatDetail(chat.id)},
        modifier =
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
        colors =
            CardDefaults.cardColors(
                containerColor = AppTheme.colors.colorBackgroundIndigo,
            ),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
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
            Column {
                Text(text = chat.name, style = AppTheme.typography.h2, color = AppTheme.colors.systemTextOnPrimary)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = chat.description, style = AppTheme.typography.body1, color = AppTheme.colors.systemTextPrimary)
            }
        }
    }
}
