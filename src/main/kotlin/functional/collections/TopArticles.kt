package functional.collections

class TopArticlesGenerator(
    private val articles: List<ArticleStatistics>,
) {
    fun topArticles(n: Int): List<ArticleStatistics> = TODO()
}

data class ArticleStatistics(
    val title: String,
    val views: Long,
)

fun main() {
    val generator = TopArticlesGenerator(
        listOf(
            ArticleStatistics("Article 1", 400),
            ArticleStatistics("Article 2", 100),
            ArticleStatistics("Article 3", 200),
            ArticleStatistics("Article 4", 300),
            ArticleStatistics("Article 5", 500),
            ArticleStatistics("Article 6", 0),
        )
    )
    val topArticles = generator.topArticles(3)
    topArticles.onEach { println(it) }
    // ArticleStatistics(title=Article 1, views=400)
    // ArticleStatistics(title=Article 4, views=300)
    // ArticleStatistics(title=Article 5, views=500)
}
