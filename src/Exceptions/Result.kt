package Exceptions

/*
  // Use in suspension contexts too
  suspend fun fetchUser(id: Int): Result<User> = runCatching {
    api.getUser(id)
}
 */

// runCatching — wraps a block, returning Result<T>
fun main(array: Array<String>) {

    val result: Result<Int> = runCatching {
        "42".toInt()
    }

    result.onSuccess { value -> println("Success:$value") }
    result.onFailure { error -> println("Failure:$error") }

    // Get the value safely
    val number = result.getOrNull()                          // 42 or null
    val withDefault = result.getOrDefault(0)    // 42 or 0
    val orThrow = result.getOrThrow()                      // 42 or throws

    // Chain transformations
    val processed: Result<String> = runCatching {
        "42".toInt()
    }
        .map { value -> value * 2 }   // Result<Int> → Result<Int>
        .map { value -> "Number is $value" }  // Result<Int> → Result<String>
        .recover { "Default value" }  // Provides fallback on error
    println(processed)


}