package io.thomasgasangwa.bookcollection.domain.model

data class UserState(
    val isLoggedIn: Boolean = false,
    val user: User? = null
)
