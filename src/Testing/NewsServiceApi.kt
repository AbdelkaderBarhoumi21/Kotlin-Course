package Testing

interface NewsApi {
    suspend fun fetchHeadlines(): List<String>
    suspend fun fetchArticle(id: Int): String
}

class NewsService(private val api: NewsApi) {

    suspend fun getTopHeadline(): String {
        val headlines = api.fetchHeadlines()
        return headlines.firstOrNull() ?: "No news today"
    }

    suspend fun getSummary(id: Int): String {
        val article = api.fetchArticle(id)
        return article.take(100)    // first 100 characters
    }
}