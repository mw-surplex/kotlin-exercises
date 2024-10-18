package coroutines.dispatcher

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class DiscNewsRepository(
    private val discReader: DiscReader
) : NewsRepository {
    override suspend fun getNews(newsId: String): News {
        val (title, content) = discReader.read("user/$newsId")
        return News(title, content)
    }
}

interface DiscReader {
    fun read(key: String): List<String>
}

interface NewsRepository {
    suspend fun getNews(newsId: String): News
}

data class News(val title: String, val content: String)
