package com.timetonic.booklistapp.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.timetonic.booklistapp.R
import com.timetonic.booklistapp.util.SessionDataStore
import com.timetonic.booklistapp.data.remote.TimetonicApiRepository
import com.timetonic.booklistapp.ui.common.BannerErrorMessage
import com.timetonic.booklistapp.ui.theme.BookListAppTheme

/**
 * Composable function for displaying a list of books fetched from the repository.
 *
 * @param timetonicApiRepository The repository for fetching book data.
 * @param viewModel The view model for managing the state of the book list screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    timetonicApiRepository: TimetonicApiRepository = TimetonicApiRepository(
        sessionManager = SessionDataStore(LocalContext.current)
    ), viewModel: BookListViewModel = viewModel(
        factory = BookListViewModelFactory(
            timetonicApiRepository
        )
    )
) {
    val bookState by viewModel.bookState.collectAsState()
    val pullState = rememberPullToRefreshState()
    if (pullState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.onEvent(BookListUiEvent.OnLoadBooks)
            pullState.endRefresh()
        }
    }
    Scaffold(modifier = Modifier.nestedScroll(pullState.nestedScrollConnection), topBar = {
        TopAppBar(title = { Text("Books") }, actions = {
            IconButton(onClick = { pullState.startRefresh() }) {
                Icon(Icons.Filled.Refresh, "Trigger Refresh")
            }
        })
    }) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (pullState.isRefreshing || bookState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { pullState.progress })
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                BannerErrorMessage(visibility = bookState.isError,
                    message = bookState.errorMessage,
                    onClose = { viewModel.onEvent(BookListUiEvent.CloseBannerErrorMessage) })
                Spacer(modifier = Modifier.size(BookListAppTheme.dimens.paddingSmall))
                LazyColumn(Modifier.fillMaxSize()) {
                    if (!pullState.isRefreshing && bookState.books.isNotEmpty()) {
                        itemsIndexed(bookState.books) { index, book ->
                            ListItem({
                                BookItem(
                                    itemIndex = index, title = book.title, imageUrl = book.imageUrl
                                )
                            })
                        }
                    }
                }
            }
            if (bookState.books.isEmpty() && !(pullState.isRefreshing || bookState.isLoading)) {
                Text(
                    text = "No books seem to be available", modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
    }
}

/**
 * Composable function for displaying an individual book item.
 *
 * @param itemIndex The index of the book item.
 * @param title The title of the book.
 * @param imageUrl The URL of the book image.
 * @param modifier The modifier for styling the book item.
 */
@Composable
fun BookItem(
    itemIndex: Int, title: String, imageUrl: String?, modifier: Modifier = Modifier
) {
    Card(
        modifier
            .padding(10.dp)
            .wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = BookListAppTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            if (imageUrl == null) Image(
                painter = painterResource(id = R.drawable.no_image),
                contentDescription = "Book Image",
                modifier = modifier.size(140.dp)
            )
            else AsyncImage(
                model = imageUrl,
                contentDescription = "Book Image",
                modifier = modifier.size(140.dp)
            )
            Column(modifier.padding(12.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = BookListAppTheme.typography.titleMedium.fontSize,
                    color = BookListAppTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}