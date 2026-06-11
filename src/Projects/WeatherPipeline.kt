package Projects

import Coroutines.FakeApi
import kotlinx.coroutines.*

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
data class Weather(val city: String, val tempC: Double, val condition: String)

// Strategy / Dependency Inversion: depend on this, not on a concrete API client
interface WeatherSource {
    suspend fun fetch(city: String): Weather
}

// One concrete implementation (swap for a real HTTP client without touching the service)
class FakeApiSource : WeatherSource {
    override suspend fun fetch(city: String): Weather {
        delay(300)                                  // simulate network latency (non-blocking)
        if (city == "Atlantis") throw IllegalStateException("City not found")
        return Weather(city, tempC = 15.0 + city.length, condition = "Sunny")
    }
}

class WeatherService(private val source: WeatherSource) {  // ctor injection (SOLID: DIP)

    // Fan out: all cities fetched IN PARALLEL; each wrapped in Result so one failure is isolated
    suspend fun fetchAll(cities: List<String>): List<Result<Weather>> = coroutineScope {
        cities.map { city ->
            async { runCatching { source.fetch(city) } }   // Result<Weather>
        }.awaitAll()
    }

    fun cache(results: List<Result<Weather>>, path: String) {
        val ok = results.mapNotNull { it.getOrNull() }      // keep successes
        File(path).writeText(Json { prettyPrint = true }.encodeToString(ok))
    }
}

fun main() = runBlocking {
    val service = WeatherService(FakeApiSource())
    val cities = listOf("Paris", "Tokyo", "Atlantis", "Cairo")

    val results = service.fetchAll(cities)

    results.forEach { r ->
        r.onSuccess { println("OK  ${it.city}: ${it.tempC}°C ${it.condition}") }
            .onFailure { println("ERR ${it.message}") }
    }

    service.cache(results, "weather_cache.json")
    val saved = results.count { it.isSuccess }
    println("Cached $saved cities to weather_cache.json")
}