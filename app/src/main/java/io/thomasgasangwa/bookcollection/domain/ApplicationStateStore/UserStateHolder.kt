package io.thomasgasangwa.bookcollection.domain.ApplicationStateStore

import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.domain.model.UserState
import kotlinx.coroutines.flow.StateFlow

interface UserStateHolder {
    val userState: StateFlow<UserState>
    fun updateUser(user: User)
    fun clearUser()
}