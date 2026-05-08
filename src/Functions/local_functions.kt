package Functions

data class User(
    val username: String,
    val email: String,
    val password: String,
    val age: Int
)

fun registerUser(user: User) {
    // Local function — only visible inside registerUser
    fun validate(field: String, value: String) {
        if (value.isBlank()) {
            throw IllegalArgumentException("Value cannot be blank")
        }
    }

    fun validateAge(age: Int) {
        if (age < 18) {
            throw IllegalArgumentException("Age cannot be less than 18")
        }
    }

    fun validateEmail(email: String) {
        if (!email.contains("@")) {
            throw IllegalArgumentException("Invalid email")
        }
    }

    fun validatePassword(password: String) {
        if (password.length < 6) {
            throw IllegalArgumentException("Password cannot be less than 6")
        }
    }

    // --- Run all validations ---

    validate("Username", user.username)
    validate("Email", user.email)
    validate("Password", user.password)
    validateAge(user.age)
    validateEmail(user.email)
    validatePassword(user.password)

    // If we reach here — everything is valid ✅
    println("User ${user.username} registered successfully!")
}

fun main(args: Array<String>) {

    val admin = User("admin", "admin@gmail.com", "admin123", 18)
    registerUser(admin)

    val guest = User("guest", "", "guest123", 18)
    registerUser(guest)
    

}