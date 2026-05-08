package OOP

class Employee(
    val name: String,
    val department: String,
    val salary: Double,
    var email: String = "",
    var isActive: Boolean = true
) {
    init {
        require(name.isNotBlank()) { "Name cannot be empty" }
        require(salary > 0) { "Salary must be positive" }
        require(department.isNotBlank()) { "Department cannot be empty" }
        println("Employee $name hired in $department")
    }

    fun promote(raise: Double) {
        val newSalary = salary + raise
        println("$name promoted! New salary: $$newSalary")
    }

    fun deactivate() {
        isActive = false
        println("$name has left the company")
    }

    val status: String
        get() = if (isActive) "Active" else "Inactive"

    override fun toString() =
        "$name | $department | $$salary | $status"
}

fun main(args: Array<String>) {
    // Minimum info
    val emp1 = Employee(
        name = "Alice",
        department = "Engineering",
        salary = 5000.0
    )
// Output: Employee Alice hired in Engineering

// Full info
    val emp2 = Employee(
        name = "Bob",
        department = "Marketing",
        salary = 4000.0,
        email = "bob@company.com",
        isActive = true
    )
// Output: Employee Bob hired in Marketing

    emp1.promote(500.0)
// Output: Alice promoted! New salary: $5500.0

    println(emp1.status)   // Active

    emp2.deactivate()
// Output: Bob has left the company

    println(emp2.status)   // Inactive

    println(emp1)
// Output: Alice | Engineering | $5000.0 | Active

    println(emp2)
// Output: Bob | Marketing | $4000.0 | Inactive


// ❌ Invalid cases
    val emp3 = Employee("", "HR", 3000.0)
// IllegalArgumentException: Name cannot be empty

    val emp4 = Employee("Dave", "HR", -500.0)
// IllegalArgumentException: Salary must be positive
}