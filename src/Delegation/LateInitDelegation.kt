package Delegation

import kotlin.properties.Delegates

class Game {
    var score: Int by Delegates.notNull<Int>()
    var level: Int by Delegates.notNull<Int>()
    fun start() {
        score = 0
        level = 1
    }
}

fun main(args: Array<String>) {
    val game = Game()
    // println(game.score)  → IllegalStateException: not initialized
    game.start()
    println(game.score)  // → 0
    println(game.level)  // → 1

}