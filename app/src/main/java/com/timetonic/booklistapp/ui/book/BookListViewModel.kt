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

class BookListViewModel(
    private val timetonicRepository: TimetonicRepository
) : ViewModel() {
    private val _bookState = MutableStateFlow(BookListUiState())
    val bookState = _bookState.asStateFlow()

    init {
        loadBooks()
    }

    fun onEvent(event: BookListUiEvent) {
        when(event) {
            is BookListUiEvent.onLoadBooks -> loadBooks()
            is BookListUiEvent.CloseBannerErrorMessage -> closeBanner()
        }
    }

    private fun closeBanner() {
        _bookState.update {
            it.copy(
                isError = false,
            )
        }
    }

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

@Suppress("UNCHECKED_CAST")
class BookListViewModelFactory(private val repository: TimetonicRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            return BookListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}