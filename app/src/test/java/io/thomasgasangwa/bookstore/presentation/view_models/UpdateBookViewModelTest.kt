package io.thomasgasangwa.bookstore.presentation.view_models

import io.mockk.impl.annotations.MockK
import io.thomasgasangwa.bookstore.domain.repository.LocalRepository

class UpdateBookViewModelTest {
    @MockK
    lateinit var fakeLocalRepository: LocalRepository
}