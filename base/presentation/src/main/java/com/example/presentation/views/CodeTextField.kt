package com.example.presentation.views

import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.presentation.theme.AppTheme

@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it, it.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.focusable()) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
) {
    val isFocused = text.length == index
    val char =
        when {
            index == text.length -> "0"
            index > text.length -> ""
            else -> text[index].toString()
        }
    Text(
        modifier =
            Modifier
                .width(40.dp)
                .border(
                    1.dp,
                    when {
                        isFocused -> AppTheme.colors.controlGraphBlue
                        else -> AppTheme.colors.systemBackgroundTertiary
                    },
                    RoundedCornerShape(8.dp),
                ).padding(2.dp),
        text = char,
        style = MaterialTheme.typography.h4,
        color =
            if (isFocused) {
                AppTheme.colors.systemTextPrimary
            } else {
                AppTheme.colors.systemTextTertiary
            },
        textAlign = TextAlign.Center,
    )
}
