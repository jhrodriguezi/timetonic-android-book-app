package com.timetonic.booklistapp.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timetonic.booklistapp.ui.theme.BookListAppTheme

@Composable
fun ColumnScope.BannerErrorMessage(
    visibility: Boolean = false, message: String? = null, onClose: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = visibility, enter = slideInVertically(), exit = shrinkVertically()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BookListAppTheme.colorScheme.errorContainer),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = BookListAppTheme.colorScheme.onErrorContainer,
                modifier = Modifier
                    .padding(
                        start = BookListAppTheme.dimens.paddingTooSmall,
                        top = BookListAppTheme.dimens.paddingTooSmall
                    )
                    .minimumInteractiveComponentSize()
                    .weight(1f)
            )
            message?.let {
                Box(modifier = Modifier.weight(6f)){
                val expanded = remember { mutableStateOf(false) }

                Text(text = it,
                    color = BookListAppTheme.colorScheme.onErrorContainer,
                    maxLines = if (expanded.value) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { expanded.value = !expanded.value })
                }
            }
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .weight(1f)
                    .padding(
                        end = BookListAppTheme.dimens.paddingTooSmall,
                        top = BookListAppTheme.dimens.paddingTooSmall
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = BookListAppTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Preview
@Composable
fun BannerPreview() {
    Column {
        BannerErrorMessage(visibility = true, message = "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?\"")
    }
}