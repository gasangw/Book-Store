package io.thomasgasangwa.bookcollection.globalstate

import io.thomasgasangwa.bookcollection.common.state.UserState
import io.thomasgasangwa.bookcollection.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserStateHolder {
    val userState: StateFlow<UserState>
    fun updateUser(user: User)
    fun clearUser()
}