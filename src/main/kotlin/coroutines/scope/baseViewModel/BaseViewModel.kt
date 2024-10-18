package coroutines.scope.baseViewModel

import coroutines.scope.basePresenter.News
import coroutines.scope.basePresenter.NewsRepository
import coroutines.scope.basePresenter.UserData
import coroutines.scope.basePresenter.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.*

abstract class BaseViewModel : ViewModel() {
    private val _exceptions = Channel<Throwable>(Channel.UNLIMITED)
    val exceptions: Flow<Throwable> = _exceptions.receiveAsFlow()

    val scope: CoroutineScope = TODO()
}

class MainViewModel(
    private val userRepo: UserRepository,
    private val newsRepo: NewsRepository
) : BaseViewModel() {
    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData

    private val _news = MutableStateFlow(emptyList<News>())
    val news: StateFlow<List<News>> = _news

    init {
        scope.launch {
            _userData.value = userRepo.getUser()
        }
        scope.launch {
            _news.value = newsRepo.getNews()
                .sortedByDescending { it.date }
        }
    }
}

abstract class ViewModel {
    open fun onCleared() {}
}

interface UserRepository {
    suspend fun getUser(): UserData
}

interface NewsRepository {
    suspend fun getNews(): List<News>
}

data class UserData(val name: String)
data class News(val date: Date)
