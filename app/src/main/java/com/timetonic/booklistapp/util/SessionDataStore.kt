package com.timetonic.booklistapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Class implementing the SessionManager interface using Android's DataStore for persistent storage of session-related data.
 * @param context The context used for accessing DataStore.
 */
class SessionDataStore(
    private val context: Context,
) : SessionManager {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userSession")
        private val APP_KEY = stringPreferencesKey("app_key")
        private val OAUTH_KEY = stringPreferencesKey("oauth_key")
        private val SESS_KEY = stringPreferencesKey("sess_key")
        private val OAUTH_USER_ID = stringPreferencesKey("oauth_user_id")
    }

    override val appKey: Flow<String>
        get() = context.dataStore.data.map {
            it[APP_KEY] ?: ""
        }
    override val oauthKey: Flow<String>
        get() = context.dataStore.data.map {
            it[OAUTH_KEY] ?: ""
        }
    override val sessKey: Flow<String>
        get() = context.dataStore.data.map {
            it[SESS_KEY] ?: ""
        }
    override val oauthUserId: Flow<String>
        get() = context.dataStore.data.map {
            it[OAUTH_USER_ID] ?: ""
        }

    override suspend fun saveAppKey(appKey: String) {
        context.dataStore.edit {
            it[APP_KEY] = appKey
        }
    }

    override suspend fun saveOauthKey(oauthKey: String) {
        context.dataStore.edit {
            it[OAUTH_KEY] = oauthKey
        }
    }

    override suspend fun saveSessKey(sessKey: String) {
        context.dataStore.edit {
            it[SESS_KEY] = sessKey
        }
    }

    override suspend fun saveOauthUserId(oauthUserId: String) {
        context.dataStore.edit {
            it[OAUTH_USER_ID] = oauthUserId
        }
    }
}