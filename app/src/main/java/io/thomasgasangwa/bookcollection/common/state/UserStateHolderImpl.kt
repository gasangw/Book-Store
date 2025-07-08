package io.thomasgasangwa.bookcollection.common.state

import io.thomasgasangwa.bookcollection.domain.model.User
import io.thomasgasangwa.bookcollection.globalstate.UserStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserStateHolderImpl : UserStateHolder {
    private val _userState = MutableStateFlow(UserState())
    override val userState: StateFlow<UserState> = _userState.asStateFlow()

    override fun updateUser(user: User) {
        _userState.update { current ->
            current.copy(
                isLoggedIn = true,
                user = user
            )
        }
    }

    override fun clearUser() {
        _userState.update { UserState() }
    }
}