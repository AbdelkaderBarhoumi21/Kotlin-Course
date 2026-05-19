package Functions

// inline function example
inline fun measureTime(block: () -> Unit) {
    val time = System.currentTimeMillis()
    block()
    val end = System.currentTimeMillis()
    println("Took ${end - time} ms")
}

// var savedCallback: (() -> Unit)?

//                     ↑           ↑↑
//                     groupe     ?  on mlmabda
// noinline function
// invoke() is the method Kotlin calls internally when you execute a lambda.
// Every lambda has an invoke() method under the hood.
/*
val lambda = { println("hello") }

 lambda()         // short syntax   → Kotlin translates this to lambda.invoke()
 lambda.invoke()  // explicit call  → same result, just more verbose

 */

// ─────────────────────────────────────────────────────────────
// WHY use ?.invoke() instead of just () on a nullable lambda ?
// ─────────────────────────────────────────────────────────────

var savedCallback: (() -> Unit)? = null // global varibale

// modifier — tells the compiler "do not inline this lambda" and callback name of params
inline fun registrationAction(
    inline: () -> Unit,
    noinline callback: () -> Unit,

    ) {
    inline()
    savedCallback = callback
}


// Crossline example
inline fun runInBackground(crossinline block: () -> Unit) {
    Thread {
        block()       // executed in thread
    }.start()
}

fun main(args: Array<String>) {
    measureTime {
        Thread.sleep(500)
        println("Finished")
    }

    registrationAction(
        inline = {
            println("executed now")
        },
        callback = {
            println("executed later")
        }
    )

    println("Done")
    savedCallback?.invoke()

    runInBackground {
        Thread.sleep(100)
        println("I'm filming in the background.")
        // return  ← ❌ banned by crossinline
    }
    println("hand continues immediately")

}