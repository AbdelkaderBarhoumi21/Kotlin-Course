package Testing

data class User(val id: Int, val name: String, val email: String)

interface UserRepository {
    fun findById(id: Int): User?
    fun save(user: User): User
    fun existsByEmail(email: String): Boolean
}

class UserService(private val repository: UserRepository) {

    fun getUser(id: Int): User {
        return repository.findById(id)
            ?: throw NoSuchElementException("User $id not found")
    }

    fun createUser(name: String, email: String): User {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(email.contains("@")) { "Invalid email" }

        if (repository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already in use: $email")
        }

        val user = User(id = 0, name = name, email = email)
        return repository.save(user)
    }
}