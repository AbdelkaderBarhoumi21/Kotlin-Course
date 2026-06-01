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

}