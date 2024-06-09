package com.timetonic.booklistapp.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.timetonic.booklistapp.ui.theme.BookListAppTheme

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = BookListAppTheme.typography.headlineLarge,
        color = BookListAppTheme.colorScheme.secondary
    )
}

@Composable
fun MediumTitleText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        style = BookListAppTheme.typography.headlineMedium,
        color = BookListAppTheme.colorScheme.primary,
        textAlign = textAlign
    )
}

@Composable
fun ErrorTextInputField(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = BookListAppTheme.typography.bodyMedium,
        color = BookListAppTheme.colorScheme.error
    )
}