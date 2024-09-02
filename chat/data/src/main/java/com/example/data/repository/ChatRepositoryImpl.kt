package com.example.data.repository

import com.example.domain.models.Chat
import com.example.domain.models.Message
import com.example.domain.repository.ChatRepository
import java.time.LocalDateTime

class ChatRepositoryImpl : ChatRepository {
    private val chatData =
        listOf(
            Chat(
                avatarUrl = "https://avatars.mds.yandex.net/i?id=ff6dc935503537acb726dfeed49bdfde6ff2366c-13082451-images-thumbs&n=13",
                name = "First",
                description = " First chat",
                id = 12,
                message =
                    listOf(
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(5),
                            message = "adsfsdgsdfgdshgdfgh",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(5).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh dffgd",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(4),
                            message = "adsfsdgsdfgdshgdfgh wefgwfge",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(4).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh qfsdf",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(3),
                            message = "adsfsdgsdfgdshgdfgh egwe",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(3).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh hoh ohiuvig iuviu",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(2),
                            message = "adsfsdgsdfgdshgdfgh igvigv iuviuv ohbio",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(2).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh iuviugv",
                        ),
                    ),
            ),
            Chat(
                avatarUrl = "https://avatars.mds.yandex.net/i?id=01d8a39ce4011525aef873bb253416b078118d55-4827783-images-thumbs&n=13",
                name = "First aawa",
                description = " First chat",
                id = 124,
                message =
                    listOf(
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(5),
                            message = "adsfsdgsdfgdshgdfgh",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(5).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh dffgd",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(4),
                            message = "adsfsdgsdfgdshgdfgh wefgwfge",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(4).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh qfsdf",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(3),
                            message = "adsfsdgsdfgdshgdfgh egwe",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(3).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh hoh ohiuvig iuviu",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(2),
                            message = "adsfsdgsdfgdshgdfgh igvigv iuviuv ohbio",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(2).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh iuviugv",
                        ),
                    ),
            ),
            Chat(
                avatarUrl = "https://avatars.mds.yandex.net/i?id=9c371eab7c86c2ed417ba0698d637fb3a78dd972-9678500-images-thumbs&n=13",
                name = "First sfhfg",
                description = " First chat",
                id = 120,
                message =
                    listOf(
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(5),
                            message = "adsfsdgsdfgdshgdfgh",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(5).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh dffgd",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(4),
                            message = "adsfsdgsdfgdshgdfgh wefgwfge",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(4).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh qfsdf",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(3),
                            message = "adsfsdgsdfgdshgdfgh egwe",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(3).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh hoh ohiuvig iuviu",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(2),
                            message = "adsfsdgsdfgdshgdfgh igvigv iuviuv ohbio",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(2).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh iuviugv",
                        ),
                    ),
            ),
            Chat(
                avatarUrl = "https://avatars.mds.yandex.net/i?id=e287cc170e797a4a4affccc187906384ec753e9a-10780278-images-thumbs&n=13",
                name = "First 3457",
                description = " First chat",
                id = 126,
                message =
                    listOf(
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(5),
                            message = "adsfsdgsdfgdshgdfgh",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(5).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh dffgd",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(4),
                            message = "adsfsdgsdfgdshgdfgh wefgwfge",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(4).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh qfsdf",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(3),
                            message = "adsfsdgsdfgdshgdfgh egwe",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(3).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh hoh ohiuvig iuviu",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(2),
                            message = "adsfsdgsdfgdshgdfgh igvigv iuviuv ohbio",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(2).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh iuviugv",
                        ),
                    ),
            ),
            Chat(
                avatarUrl = "https://avatars.mds.yandex.net/i?id=422f338d8ac2a79e668f620ae55d261676328e72-8325116-images-thumbs&n=13",
                name = "First 898765",
                description = " First chat",
                id = 1299,
                message =
                    listOf(
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(5),
                            message = "adsfsdgsdfgdshgdfgh",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(5).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh dffgd",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(4),
                            message = "adsfsdgsdfgdshgdfgh wefgwfge",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(4).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh qfsdf",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(3),
                            message = "adsfsdgsdfgdshgdfgh egwe",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(3).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh hoh ohiuvig iuviu",
                        ),
                        Message(
                            selfMessage = true,
                            date = LocalDateTime.now().minusDays(2),
                            message = "adsfsdgsdfgdshgdfgh igvigv iuviuv ohbio",
                        ),
                        Message(
                            selfMessage = false,
                            date = LocalDateTime.now().minusDays(2).plusHours(2),
                            message = "adsfsdgsdfgdshgdfgh iuviugv",
                        ),
                    ),
            ),
        )

    override fun getChatList(): List<Chat> = chatData

    override fun getChatDetail(id: Long): Chat = chatData.first { it.id == id }
}
