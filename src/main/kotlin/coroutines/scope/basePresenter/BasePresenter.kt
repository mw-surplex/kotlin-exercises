package coroutines.scope.basePresenter

import kotlinx.coroutines.*
import java.util.*

abstract class BasePresenter(
    private val onError: (Throwable) -> Unit = {}
) {
    val scope: CoroutineScope = TODO()

    fun onDestroy() {}
}

class MainPresenter(
    private val view: MainView,
    private val userRepo: UserRepository,
    private val newsRepo: NewsRepository
) : BasePresenter(view::onError) {

    fun onCreate() {
        scope.launch {
            val user = userRepo.getUser()
            view.showUserData(user)
        }
        scope.launch {
            val news = newsRepo.getNews()
                .sortedByDescending { it.date }
            view.showNews(news)
        }
    }
}

interface MainView {
    fun onError(throwable: Throwable): Unit
    fun showUserData(user: UserData)
    fun showNews(news: List<News>)
}

interface UserRepository {
    suspend fun getUser(): UserData
}

interface NewsRepository {
    suspend fun getNews(): List<News>
}

data class UserData(val name: String)
data class News(val date: Date)
