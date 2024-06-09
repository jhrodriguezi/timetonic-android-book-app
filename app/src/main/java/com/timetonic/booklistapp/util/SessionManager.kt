package com.timetonic.booklistapp.util

import kotlinx.coroutines.flow.Flow

/**
 * Interface defining methods for managing session-related data in the application.
 */
interface SessionManager {
    val appKey: Flow<String>
    val oauthKey: Flow<String>
    val sessKey: Flow<String>
    val oauthUserId: Flow<String>
    suspend fun saveAppKey(appKey: String)
    suspend fun saveOauthKey(oauthKey: String)
    suspend fun saveSessKey(sessKey: String)
    suspend fun saveOauthUserId(oauthUserId: String)
}