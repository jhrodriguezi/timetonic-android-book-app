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

/**
 * Composable function for rendering a normal button.
 * @param modifier The modifier for the button.
 * @param text The text to display on the button.
 * @param onClick The callback to be invoked when the button is clicked.
 */
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

/**
 * Composable function for rendering a clickable row with an icon and text.
 * @param modifier The modifier for the clickable row.
 * @param iconVector The vector representing the icon.
 * @param iconContentDescription The content description for the icon.
 * @param text The text to display alongside the icon.
 * @param onClick The callback to be invoked when the row is clicked.
 */
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