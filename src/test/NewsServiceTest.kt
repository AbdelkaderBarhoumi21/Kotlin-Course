package test

import Testing.*
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


class NewsServiceTest {

    private val api = mockk<NewsApi>()
    private val service = NewsService(api)

    @Test
    fun `getTopHeadline returns first headline`() = runTest {
        // ARRANGE
        // coEvery = "coroutine every" — use this for suspend functions
        coEvery { api.fetchHeadlines() } returns listOf("Breaking News", "Sports Update")

        // ACT
        val result = service.getTopHeadline()

        // ASSERT
        assertEquals("Breaking News", result)

        // coVerify = "coroutine verify" — use this for suspend functions
        coVerify { api.fetchHeadlines() }
    }

    @Test
    fun `getTopHeadline returns fallback when list is empty`() = runTest {
        // ARRANGE
        coEvery { api.fetchHeadlines() } returns emptyList()

        // ACT
        val result = service.getTopHeadline()

        // ASSERT
        assertEquals("No news today", result)
    }

    @Test
    fun `getSummary truncates long articles`() = runTest {
        // ARRANGE
        val longArticle = "A".repeat(200)    // 200 characters
        coEvery { api.fetchArticle(5) } returns longArticle

        // ACT
        val result = service.getSummary(5)

        // ASSERT
        assertEquals(100, result.length)
        assertEquals("A".repeat(100), result)
    }
}