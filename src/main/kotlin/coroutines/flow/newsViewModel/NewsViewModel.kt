package coroutines.flow.newsViewModel

import coroutines.flow.newsViewModelFlow.BaseViewModel
import coroutines.flow.newsViewModelFlow.News
import coroutines.flow.newsViewModelFlow.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class NewsViewModel(
    newsRepository: NewsRepository,
) : BaseViewModel() {
    private val _progressVisible = MutableStateFlow(false)
    val progressVisible = _progressVisible.asStateFlow()

    private val _newsToShow = MutableStateFlow(emptyList<News>())
    val newsToShow = _newsToShow.asStateFlow()

    private val _errors = Channel<Throwable>()
    val errors = _errors.receiveAsFlow()

    init {
        // TODO
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

data class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val url: String,
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
