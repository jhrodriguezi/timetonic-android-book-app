package com.timetonic.booklistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.timetonic.booklistapp.ui.BookList
import com.timetonic.booklistapp.ui.Login
import com.timetonic.booklistapp.ui.book.BookListScreen
import com.timetonic.booklistapp.ui.login.LoginScreen
import com.timetonic.booklistapp.ui.theme.BookListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookListAppTheme {
                MainAppNavHost()
            }
        }
    }
}

/**
 * Composable function for the main app navigation host.
 */
@Composable
fun MainAppNavHost(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(onNavigateToAuthenticatedRoute = {
                navController.navigate(BookList)
            })
        }
        composable<BookList> {
            BookListScreen()
        }
    }
}