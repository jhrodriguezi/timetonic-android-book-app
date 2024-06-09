package com.timetonic.booklistapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.timetonic.booklistapp.ui.theme.BookListAppTheme

@Composable
fun NormalButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(BookListAppTheme.dimens.normalButtonHeight)
            .requiredWidth(BookListAppTheme.dimens.minButtonWidth),
        onClick = onClick
    ) {
        Text(text = text, style = BookListAppTheme.typography.titleMedium)
    }
}

@Composable
fun SmallClickableWithIconAndText(
    modifier: Modifier = Modifier,
    iconVector: ImageVector = Icons.Outlined.QuestionMark,
    iconContentDescription: String = "",
    text: String = "",
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable {
            onClick.invoke()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = iconContentDescription,
            tint = BookListAppTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(start = BookListAppTheme.dimens.paddingSmall),
            text = text,
            style = BookListAppTheme.typography.titleSmall,
            color = BookListAppTheme.colorScheme.primary
        )
    }
}