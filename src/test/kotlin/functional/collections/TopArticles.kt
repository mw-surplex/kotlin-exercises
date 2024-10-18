package functional.collections

import junit.framework.TestCase.assertEquals
import org.junit.Test

class TopArticlesTest {
    @Test
    fun `Top articles are returned in the correct order`() {
        val articles = listOf(
            ArticleStatistics("Article 1", 400),
            ArticleStatistics("Article 2", 100),
            ArticleStatistics("Article 3", 200),
            ArticleStatistics("Article 4", 300),
            ArticleStatistics("Article 5", 500),
            ArticleStatistics("Article 6", 0),

        )
        val generator = TopArticlesGenerator(articles)
        val topArticles = generator.topArticles(100)
        assertEquals(articles, topArticles)
    }

    @Test
    fun `Only n top articles are kept`() {
        val articles = listOf(
            ArticleStatistics("Article 1", 400),
            ArticleStatistics("Article 2", 100),
            ArticleStatistics("Article 3", 200),
            ArticleStatistics("Article 4", 300),
            ArticleStatistics("Article 5", 500),
            ArticleStatistics("Article 6", 0),

        )
        val generator = TopArticlesGenerator(articles)
        val topArticles = generator.topArticles(3)
        assertEquals(articles.slice(listOf(0, 3, 4)), topArticles)
    }
}
