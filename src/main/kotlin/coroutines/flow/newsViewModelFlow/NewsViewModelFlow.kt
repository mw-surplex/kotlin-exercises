package coroutines.flow.newsViewModelFlow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class NewsViewModelFlow(
    newsRepository: NewsRepository
) : BaseViewModel() {
    private val _progressVisible = MutableStateFlow(false)
    val progressVisible = _progressVisible.asStateFlow()

    private val _newsToShow = MutableSharedFlow<News>()
    val newsToShow = _newsToShow.asSharedFlow()

    private val _errors = MutableSharedFlow<Throwable>()
    val errors = _errors.asSharedFlow()

    init {
        TODO()
    }
}

class ApiException : Exception()

interface NewsRepository {
    fun fetchNews(): Flow<News>
}

abstract class BaseViewModel {
    protected val viewModelScope = CoroutineScope(
        Dispatchers.Main.immediate + SupervisorJob()
    )
}

class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val url: String
)

class FakeNewsRepository : NewsRepository {
    val newsList = List(100) { News("Title $it", "Description $it", "ImageUrl $it", "Url $it") }
    var fetchNewsStartDelay = 0L
    var fetchNewsDelay = 0L
    val failWith = mutableListOf<Throwable>()

    override fun fetchNews(): Flow<News> = flow {
        delay(fetchNewsStartDelay)
        failWith.removeFirstOrNull()?.let { throw it }
        newsList.forEach {
            delay(fetchNewsDelay)
            emit(it)
        }
    }
}
