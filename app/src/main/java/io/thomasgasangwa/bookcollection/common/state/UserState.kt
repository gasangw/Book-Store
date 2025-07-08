package io.thomasgasangwa.bookcollection.common.state

import io.thomasgasangwa.bookcollection.domain.model.User

data class UserState(
    val isLoggedIn: Boolean = false,
    val user: User? = null
)