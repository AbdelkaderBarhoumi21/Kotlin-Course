package  Functions

// vararg : number of variables arguments
fun sum(vararg number: Int): Int = number.sum()
fun main(args: Array<String>) {

    val sumResult = sum(1, 2, 3)
    println("Sum Result: $sumResult")

    // spread operators (*)
    val sumParams = intArrayOf(1, 2, 4)
    val sumResultWithSpreadOperator = sum(*sumParams)
    println("Sum Result (*): $sumResultWithSpreadOperator")

    infix fun Int.power(exponent: Int): Long {
        var result = 1L;
        // this the int which we call the functions 2.power(3) == Int.power(exponent) => 2(Int) = this Int
        repeat(exponent) { result *= this }
        return result
    }

    val result = 2 power 3
    println(result)


}