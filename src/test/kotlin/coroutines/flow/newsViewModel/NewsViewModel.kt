package coroutines.flow.newsViewModel

import coroutines.flow.newsViewModelFlow.ApiException
import coroutines.flow.newsViewModelFlow.FakeNewsRepository
import coroutines.flow.newsViewModelFlow.News
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsViewModelTest {
    lateinit var dispatcher: TestDispatcher
    lateinit var newsRepository: FakeNewsRepository

    @Before
    fun setUp() {
        newsRepository = FakeNewsRepository()
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should show all news`() {
        val viewModel = NewsViewModel(newsRepository)
        newsRepository.fetchNewsDelay = 1000
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(newsRepository.newsList, viewModel.newsToShow.value)
        assertEquals(newsRepository.newsList.size * newsRepository.fetchNewsDelay, dispatcher.scheduler.currentTime)
    }

    @Test
    fun `should show progress bar when loading news`() {
        val viewModel = NewsViewModel(newsRepository)
        newsRepository.fetchNewsDelay = 1000
        var progressChanges = listOf<Pair<Long, Boolean>>()
        viewModel.progressVisible.onEach {
            progressChanges += dispatcher.scheduler.currentTime to it
        }.launchIn(CoroutineScope(dispatcher))

        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(
            listOf(0L to true, newsRepository.newsList.size * newsRepository.fetchNewsDelay to false),
            progressChanges
        )
    }

    @Test
    fun `should retry API exceptions`() {
        val exceptionsNum = 10
        newsRepository.failWith.addAll(List(exceptionsNum) { ApiException() })
        newsRepository.fetchNewsStartDelay = 1000
        val viewModel = NewsViewModel(newsRepository)
        var errors = listOf<Throwable>()
        viewModel.errors.onEach {
            errors += it
        }.launchIn(CoroutineScope(dispatcher))
        var newsShown = listOf<News>()
        viewModel.newsToShow.onEach {
            newsShown += it
        }.launchIn(CoroutineScope(dispatcher))
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(0, errors.size)
        assertEquals(newsRepository.newsList, newsShown)
        assertEquals(newsRepository.fetchNewsStartDelay * (exceptionsNum + 1), dispatcher.scheduler.currentTime)
    }

    @Test
    fun `should catch exceptions`() {
        val exception = Exception()
        newsRepository.failWith.add(exception)
        newsRepository.fetchNewsStartDelay = 1000
        val viewModel = NewsViewModel(newsRepository)
        var errors = listOf<Throwable>()
        viewModel.errors.onEach {
            errors += it
        }.launchIn(CoroutineScope(dispatcher))
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals(listOf(exception), errors)
        assertEquals(false, viewModel.progressVisible.value)
        assertEquals(1000, dispatcher.scheduler.currentTime)
    }
}
