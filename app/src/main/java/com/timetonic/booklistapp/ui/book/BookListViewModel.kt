package com.timetonic.booklistapp.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.timetonic.booklistapp.util.Result
import com.timetonic.booklistapp.data.local.repository.TimetonicRepository
import com.timetonic.booklistapp.data.remote.model.GetAllBooksParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and business logic of the Book List screen.
 * @param timetonicRepository The repository for fetching book data.
 */
class BookListViewModel(
    private val timetonicRepository: TimetonicRepository
) : ViewModel() {

    /**
     * State flow for the Book List UI.
     */
    private val _bookState = MutableStateFlow(BookListUiState())
    val bookState = _bookState.asStateFlow()

    /**
     * Initializes the view model by loading the books from the repository.
     */
    init {
        loadBooks()
    }

    /**
     * Handles events triggered in the UI.
     * @param event The event to handle.
     */
    fun onEvent(event: BookListUiEvent) {
        when(event) {
            is BookListUiEvent.OnLoadBooks -> loadBooks()
            is BookListUiEvent.CloseBannerErrorMessage -> closeBanner()
        }
    }

    /**
     * Closes the banner error message.
     */
    private fun closeBanner() {
        _bookState.update {
            it.copy(
                isError = false,
            )
        }
    }

    /**
     * Loads the books from the repository.
     */
    private fun loadBooks() {
        if (_bookState.value.isLoading) return
        viewModelScope.launch {
            _bookState.update {
                it.copy(
                    isLoading = true,
                    books = emptyList()
                )
            }

            val result = timetonicRepository.getAllBooks(
                GetAllBooksParams()
            )
            when(result) {
                is Result.Success -> {
                    _bookState.update {
                        it.copy(
                            books = result.data,
                            isSuccessful = true,
                            isError = false,
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _bookState.update {
                        it.copy(
                            isSuccessful = true,
                            isError = true,
                            errorMessage = result.throwable.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}

/**
 * Factory for creating instances of [BookListViewModel].
 * @param repository The repository for fetching book data.
 */
@Suppress("UNCHECKED_CAST")
class BookListViewModelFactory(private val repository: TimetonicRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            return BookListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}