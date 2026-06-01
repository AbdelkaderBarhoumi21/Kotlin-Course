package test
/*
  When you want to test the same logic with many different inputs,
  don't write 10 separate tests — use `@ParameterizedTest`.
 */


/*
  JUnit reads @ValueSource and sees 5 values → it creates 5 separate test runs:
  The function has exactly one parameter number: Int — JUnit matches it by position. First value from @ValueSource goes into the first parameter. That's it.

Run 1 → number = 1    → assertFalse(calculator.isEven(1))   ✅
Run 2 → number = 3    → assertFalse(calculator.isEven(3))   ✅
Run 3 → number = 7    → assertFalse(calculator.isEven(7))   ✅
Run 4 → number = 99   → assertFalse(calculator.isEven(99))  ✅
Run 5 → number = -5   → assertFalse(calculator.isEven(-5))  ✅
 */
import Testing.Calculator
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class ParameterizedCalculatorTest {
    private lateinit var calculator: Calculator

    @BeforeEach
    fun setUp() {
        calculator = Calculator()
    }

    // Single value — runs once per value in the list

    @ParameterizedTest
    @ValueSource(ints = [2, 4, -10, 8])
    fun `isEven returns true for even numbers`(number: Int) {
        assertTrue { calculator.isEven(number) }
    }


    @ParameterizedTest
    @ValueSource(ints = [1, 3, 7, 99, -5])
    fun `isEven returns false for odd numbers`(number: Int) {
        assertFalse(calculator.isEven(number))
    }

    // Multiple columns: input1, input2, expected result
    // Each row = one separate test run
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource(
        "1,  1,  2",
        "5,  7,  12",
        "0,  0,  0",
        "-3, 3,  0",
        "10, -4, 6",
        "2 ,4 ,6"
    )
    fun `add produces correct sum`(a: Int, b: Int, expected: Int) {
        assertEquals(expected, calculator.add(a, b))
    }

}