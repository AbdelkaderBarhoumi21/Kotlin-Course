package OOP

import OOP.NetworkError
import OOP.NetworkLoading
import OOP.NetworkSuccess

//In Kotlin, subclasses of a sealed class can be inside OR outside the {} block

// Inside the {}
sealed class Result {
    data class Success(val data: String, val code: Int) : Result()
    data class Error(val message: String, val code: Int) : Result()
    object Loading : Result()
}


// Outside
sealed class NetworkResult
class NetworkSuccess(val data: String, val code: Int) : NetworkResult()
class NetworkError(val message: String, val code: Int) : NetworkResult()
object NetworkLoading : NetworkResult() // object because NetworkLoading doesn't have data


// handle result

fun handleNetworkResult(result: NetworkResult) {
    when (result) {
        is NetworkSuccess ->
            println("✅ Success ${result.code}: ${result.data}")


        is NetworkError ->
            println("❌ Error ${result.code}: ${result.message}")

        is NetworkLoading ->
            println("⏳ Loading...")

        // ✅ no else needed — all cases covered
    }
}

fun handleResult(result: Result) {
    when (result) {
        is Result.Success ->
            println("✅ Success ${result.code}: ${result.data}")


        is Result.Error ->
            println("❌ Error ${result.code}: ${result.message}")

        is Result.Loading ->
            println("⏳ Loading...")

        // ✅ no else needed — all cases covered
    }
}


fun main(args: Array<String>) {
    handleNetworkResult(NetworkLoading)
    // ⏳ Loading...
    handleNetworkResult(NetworkSuccess("User data", 200))
    // ✅ Success 200: User data
    handleNetworkResult(NetworkError("Not found", 404))
    // ❌ Error 404: Not found


    handleResult(Result.Loading)
    handleResult(Result.Success("API DATA", 200))
    handleResult(Result.Error("ERROR GETTING DATA", 400))
}