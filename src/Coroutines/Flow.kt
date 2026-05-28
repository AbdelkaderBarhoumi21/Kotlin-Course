package Coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// ══════════════════════════════════════════
// One-shot events  (SharedFlow)
// ══════════════════════════════════════════
sealed class GameEvent {
    data class LevelReached(val level: Int) : GameEvent()
    data class Error(val message: String) : GameEvent()
    data object GameOver : GameEvent()
}

// ══════════════════════════════════════════
// Game state  (StateFlow)
// ══════════════════════════════════════════
data class GameState(
    val score: Int = 0,
    val level: Int = 1,
    val isRunning: Boolean = true
)

// ══════════════════════════════════════════
// Game engine
// ══════════════════════════════════════════
class Game {

    // MutableStateFlow — holds the CURRENT state at all times
    // anyone can read .value directly without collecting
    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()
    //         ↑ read-only for the outside world
    //           like ctrl.stream in Dart

    // MutableSharedFlow — one-shot events
    // buffer = 10 so no event is lost if the collector is slow
    private val _events = MutableSharedFlow<GameEvent>(
        extraBufferCapacity = 10
    )
    val events: SharedFlow<GameEvent> = _events.asSharedFlow()
    //          ↑ read-only for the outside world

    // Cold Flow — produces points one by one
    // nothing runs until someone calls collect on it
    fun producePoints(): Flow<Int> = flow {
        val pointsList = listOf(10, 25, 5, 50, 15, 30)
        for (points in pointsList) {
            delay(300)       // simulate a game tick
            emit(points)     // push one value downstream
        }
    }

    // Play — collects the cold Flow, updates StateFlow + SharedFlow
    suspend fun play() {

        producePoints().collect { pointsEarned ->

            // 1. Atomically update the StateFlow
            //    .update { } is NOT suspend — just overwrites the value in memory
            _state.update { current ->
                val newScore = current.score + pointsEarned
                val newLevel = newScore / 50 + 1
                current.copy(
                    score = newScore,
                    level = newLevel
                )
            }

            // 2. Emit a one-shot event if the level changed
            //    .emit() IS suspend — waits if the buffer is full
            val current = _state.value
            if (current.score % 50 < pointsEarned) {
                _events.emit(GameEvent.LevelReached(current.level))
            }
        }

        // Game finished — update state + fire final event
        _state.update { it.copy(isRunning = false) }
        _events.emit(GameEvent.GameOver)
    }
}

// ══════════════════════════════════════════
// main — wire everything together
// ══════════════════════════════════════════
fun main() = runBlocking {

    val game = Game()

    // ── Collector A : watches the StateFlow ──────────────────
    // receives the CURRENT value immediately on subscription,
    // then every subsequent change
    val jobState = launch {
        game.state.collect { state ->
            println("STATE  → score=${state.score}  level=${state.level}  running=${state.isRunning}")
        }
    }

    // ── Collector B : watches the SharedFlow ─────────────────
    // only receives NEW events — nothing replayed from the past
    val jobEvents = launch {
        game.events.collect { event ->
            when (event) {
                is GameEvent.LevelReached ->
                    println("EVENT  → Level ${event.level} reached!")

                is GameEvent.Error ->
                    println("EVENT  → Error: ${event.message}")

                GameEvent.GameOver ->
                    println("EVENT  → Game over!")
            }
        }
    }

    // let both collectors start before the game produces anything
    delay(100)
    game.play()

    // read the final score directly via .value — no collect needed
    // StateFlow always holds the current value in memory
    // SharedFlow cannot do this
    println("\nFinal score via .value : ${game.state.value.score}")

    jobState.cancel()
    jobEvents.cancel()
}