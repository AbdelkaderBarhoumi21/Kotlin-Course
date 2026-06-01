package test

/*
 @Test
fun addReturnsCorrectSum() {
    // ...
}
 This works perfectly fine. But when you run the test, IntelliJ shows:
✅ addReturnsCorrectSum


@Test
fun `add returns correct sum`() {
    // ...
}
When you run the test, IntelliJ shows:
✅ add returns correct sum

 */

import Testing.Calculator
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*

class CalculatorTest {
    private lateinit var calculator: Calculator

    // Runs BEFORE each test — resets state
    @BeforeEach
    fun setUp() {
        calculator = Calculator()
    }

    // ─── Basic assertions ───────────────────────────────────────────
    @Test
    fun `adds two numbers correctly`() {
        // ARRANGE — prepare everything
        val calculator = Calculator()
        val a = 5
        val b = 2
        // Act - run the real code
        val result = calculator.add(a, b)

        // Assert -verify the result
        assertEquals(a + b, result)
    }

    @Test
    fun `subtract two numbers correctly`() {
        // Arrange + act
        val result = calculator.subtract(10, 4)
        //Assert
        assertEquals(6, result)
    }

    @Test
    fun `Divide two numbers correctly`() {
        // Arrange
        val calculator = Calculator()
        val a = 5
        val b = 2
        // Act
        val result = calculator.divide(a, b)
        // Assert
        assertEquals(a / b, result)
    }
    // ─── Testing exceptions ─────────────────────────────────────────────────

    @Test
    fun `divide throws ArithmeticException when dividing by zero`() {
        // assertThrows captures the exception
        // the test passes if the exception is thrown
        //  Run this block. I expect it to throw an ArithmeticException.
        // If it throws → ✅ test passes.
        // If it does NOT throw → ❌ test fails.
        // If it throws a different exception → ❌ test fails."
        // That block is Arrange + Act + Assert all compressed into one.
        val exception = assertThrows<ArithmeticException>() {
            calculator.divide(10, 0) // this line must throw
        }
        assertEquals("Cannot divide by zero", exception.message)
    }

    @Test
    fun `divide works normally when divisor is not zero`() {
        val result = calculator.divide(10, 2)
        assertEquals(5, result)
    }

    // ─── Multiple assertions together ───────────────────────────────────────
    @Test
    fun `isEven returns correct result for various inputs`() {
        // assertAll runs ALL checks even if one fails — shows all failures at once
        assertAll(
            { assertTrue(calculator.isEven(4)) },
            { assertTrue(calculator.isEven(0)) },
            { assertFalse(calculator.isEven(7)) },
            { assertFalse(calculator.isEven(-3)) }
        )
    }

}