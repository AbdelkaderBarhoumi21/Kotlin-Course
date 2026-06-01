package test

// src/test/kotlin/UserServiceTest.kt

import Testing.*
import io.mockk.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class UserServiceTest {

    // mockk<T>() creates a fake object that records all calls made to it
    private val repository = mockk<UserRepository>()
    private val service = UserService(repository)

    // ─── getUser — happy path ───────────────────────────────────────────────

    @Test
    fun `getUser returns user when found`() {
        // ARRANGE — set the rule for the mock
        val expectedUser = User(1, "Ahmed", "ahmed@example.com")
        every { repository.findById(1) } returns expectedUser

        // ACT — run the real code (this triggers the mock internally)
        val result = service.getUser(1)

        // ASSERT — check the returned value
        assertEquals(expectedUser, result)

        // ASSERT — verify the repository was actually called
        verify { repository.findById(1) }
    }

    // ─── getUser — user not found ───────────────────────────────────────────

    @Test
    fun `getUser throws NoSuchElementException when user not found`() {
        // ARRANGE — return null to simulate "not in database"
        every { repository.findById(99) } returns null

        // ACT + ASSERT
        val exception = assertThrows<NoSuchElementException> {
            service.getUser(99)
        }
        assertEquals("User 99 not found", exception.message)
    }

    // ─── createUser — happy path ────────────────────────────────────────────

    @Test
    fun `createUser saves and returns new user`() {
        // ARRANGE
        val savedUser = User(42, "Sara", "sara@example.com")
        every { repository.existsByEmail("sara@example.com") } returns false
        every { repository.save(any()) } returns savedUser
        // any() = match any argument passed to save()

        // ACT
        val result = service.createUser("Sara", "sara@example.com")

        // ASSERT
        assertEquals("Sara", result.name)
        assertEquals(42, result.id)

        // Verify save was called exactly once
        verify(exactly = 1) { repository.save(any()) }
    }

    // ─── createUser — email already exists ─────────────────────────────────

    @Test
    fun `createUser throws when email already in use`() {
        // ARRANGE
        every { repository.existsByEmail("taken@example.com") } returns true

        // ACT + ASSERT
        assertThrows<IllegalArgumentException> {
            service.createUser("Bob", "taken@example.com")
        }

        // Verify save was NEVER called — we stopped before reaching it
        verify(exactly = 0) { repository.save(any()) }
    }

    // ─── createUser — invalid input ─────────────────────────────────────────

    @Test
    fun `createUser throws when name is blank`() {
        // No mock setup needed — exception is thrown before repository is called
        assertThrows<IllegalArgumentException> {
            service.createUser("", "valid@example.com")
        }

        // The repository should never have been touched at all
        verify { repository wasNot called }
    }
}