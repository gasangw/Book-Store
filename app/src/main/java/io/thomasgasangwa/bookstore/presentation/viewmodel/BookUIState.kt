package io.thomasgasangwa.bookstore.presentation.viewmodel

//sealed class BookUiState<out T> {
//    object Loading : BookUiState<Nothing>()
//    data class Success<T>(val data: T) : BookUiState<T>()
//    data class Error(val message: String) : BookUiState<Exception>()
//}

// Suggested improvements:
// 1. Use OnConflictStrategy.REPLACE so updates on insert are handled gracefully.
// 2. Return insert IDs (Long) for potential navigation uses.
// 3. Wrap operations in try/catch and expose UIState (Loading, Success, Error).
// 4. Separate concerns: List, Detail, and Form view models.
// 5. Use StateFlow for Compose-friendly reactive state.

// ---------- UIState.kt ----------
//sealed class UiState<out T> {
//    object Loading : UiState<Nothing>()
//    data class Success<T>(val data: T) : UiState<T>()
//    data class Error(val message: String) : UiState<Nothing>()
//}
//
//// ---------- BookListViewModel.kt ----------
//class BookListViewModel(
//    private val repository: BookRepository
//) : ViewModel() {
//
//    private val _books = MutableStateFlow<UiState<List<Book>>>(UiState.Loading)
//    val books: StateFlow<UiState<List<Book>>> = _books
//
//    init {
//        viewModelScope.launch {
//            repository.getAllBooksStream()
//                .catch { e -> _books.value = UiState.Error(e.localizedMessage ?: "Unknown error") }
//                .collect { list -> _books.value = UiState.Success(list) }
//        }
//    }
//
//    fun delete(book: Book) {
//        viewModelScope.launch {
//            try {
//                repository.deleteBook(book)
//            } catch (e: Exception) {
//                // optionally show deletion error
//            }
//        }
//    }
//}
//
//// ---------- BookDetailViewModel.kt ----------
//class BookDetailViewModel(
//    private val repository: BookRepository,
//    private val bookId: Int
//) : ViewModel() {
//
//    private val _book = MutableStateFlow<UiState<Book>>(UiState.Loading)
//    val book: StateFlow<UiState<Book>> = _book
//
//    init {
//        viewModelScope.launch {
//            repository.getBookStream(bookId)
//                .catch { e -> _book.value = UiState.Error(e.localizedMessage ?: "Unknown error") }
//                .collect { b -> _book.value = UiState.Success(b) }
//        }
//    }
//}
//
//// ---------- BookFormViewModel.kt ----------
//class BookFormViewModel(
//    private val repository: BookRepository
//) : ViewModel() {
//
//    private val _operationState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
//    val operationState: StateFlow<UiState<Unit>> = _operationState
//
//    fun save(book: Book) {
//        viewModelScope.launch {
//            _operationState.value = UiState.Loading
//            try {
//                if (book.id == 0) {
//                    repository.insertBook(book)
//                } else {
//                    repository.updateBook(book)
//                }
//                _operationState.value = UiState.Success(Unit)
//            } catch (e: Exception) {
//                _operationState.value = UiState.Error(e.localizedMessage ?: "Save failed")
//            }
//        }
//    }
//}
//
//// ---------- Composables.kt ----------
//@Composable
//fun BookListScreen(
//    viewModel: BookListViewModel = getViewModel(),
//    onSelect: (Int) -> Unit,
//    onAdd: () -> Unit
//) {
//    val state by viewModel.books.collectAsState()
//
//    when (state) {
//        is UiState.Loading -> CircularProgressIndicator()
//        is UiState.Error -> Text(text = (state as UiState.Error).message)
//        is UiState.Success -> {
//            val books = (state as UiState.Success<List<Book>>).data
//            LazyColumn {
//                items(books) { book ->
//                    ListItem(
//                        text = { Text(book.title) },
//                        secondaryText = { Text(book.author) },
//                        modifier = Modifier.clickable { onSelect(book.id) },
//                        trailing = {
//                            IconButton(onClick = { viewModel.delete(book) }) {
//                                Icon(Icons.Default.Delete, contentDescription = null)
//                            }
//                        }
//                    )
//                }
//            }
//            FloatingActionButton(onClick = onAdd, modifier = Modifier.padding(16.dp)) {
//                Icon(Icons.Default.Add, contentDescription = "Add Book")
//            }
//        }
//    }
//}
//
//@Composable
//fun BookDetailScreen(
//    viewModel: BookDetailViewModel = getViewModel(),
//    onBack: () -> Unit
//) {
//    val state by viewModel.book.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Book Detail") },
//                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
//            )
//        }
//    ) { padding ->
//        Box(modifier = Modifier.padding(padding)) {
//            when (state) {
//                is UiState.Loading -> CircularProgressIndicator()
//                is UiState.Error -> Text((state as UiState.Error).message)
//                is UiState.Success -> {
//                    val book = (state as UiState.Success<Book>).data
//                    Column(Modifier.padding(16.dp)) {
//                        Text(book.title, style = MaterialTheme.typography.h5)
//                        Text("by ${book.author}", style = MaterialTheme.typography.subtitle1)
//                        Spacer(Modifier.height(8.dp))
//                        Text(book.genre)
//                        Spacer(Modifier.height(8.dp))
//                        Text(book.description)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BookFormScreen(
//    viewModel: BookFormViewModel = getViewModel(),
//    initial: Book = Book(title = "", author = "", cover = null, genre = "", description = ""),
//    onDone: () -> Unit
//) {
//    var title by rememberSaveable { mutableStateOf(initial.title) }
//    var author by rememberSaveable { mutableStateOf(initial.author) }
//    var genre by rememberSaveable { mutableStateOf(initial.genre) }
//    var description by rememberSaveable { mutableStateOf(initial.description) }
//
//    val state by viewModel.operationState.collectAsState()
//
//    when (state) {
//        is UiState.Loading -> CircularProgressIndicator()
//        is UiState.Error -> Text((state as UiState.Error).message)
//        else -> Unit
//    }
//
//    Column(Modifier.padding(16.dp)) {
//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            label = { Text("Title") }
//        )
//        OutlinedTextField(
//            value = author,
//            onValueChange = { author = it },
//            label = { Text("Author") }
//        )
//        OutlinedTextField(
//            value = genre,
//            onValueChange = { genre = it },
//            label = { Text("Genre") }
//        )
//        OutlinedTextField(
//            value = description,
//            onValueChange = { description = it },
//            label = { Text("Description") }
//        )
//        Spacer(Modifier.height(16.dp))
//        Button(onClick = {
//            viewModel.save(initial.copy(
//                title = title,
//                author = author,
//                genre = genre,
//                description = description
//            ))
//            onDone()
//        }, modifier = Modifier.fillMaxWidth()) {
//            Text("Save")
//        }
//    }
//}
