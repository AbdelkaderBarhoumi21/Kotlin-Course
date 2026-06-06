package Projects

// Custom exception — no checked exceptions in Kotlin
class InsufficientFundsException(requested: Double, available: Double) :
    RuntimeException("Requested %.2f but only %.2f available".format(requested, available)) {
    val shortfall = requested - available
}

interface Transferable {
    fun transferTo(target: Account, amount: Double)
}

// Classes are final by default → must mark `abstract`/`open`
abstract class Account(
    val id: String,
    protected var balance: Double
) : Transferable {

    init {
        require(balance >= 0) { "Initial balance < 0" }
    }

    val currentBalance: Double get() = balance       // read-only computed property

    fun deposit(amount: Double) {
        require(amount > 0) { "Amount must be positive" }
        balance += amount
    }

    abstract fun withdraw(amount: Double)

    override fun transferTo(target: Account, amount: Double) {
        withdraw(amount)
        target.deposit(amount)
    }

    override fun toString() =
        "${this::class.simpleName}[$id] balance=%.2f".format(balance)
}

class SavingsAccount(id: String, initial: Double, private val rate: Double) :
    Account(id, initial) {
    override fun withdraw(amount: Double) {
        if (amount > balance) throw InsufficientFundsException(amount, balance)
        balance -= amount
    }

    fun applyInterest() {
        balance += balance * rate
    }
}

class CheckingAccount(id: String, initial: Double, private val overdraftLimit: Double) :
    Account(id, initial) {
    override fun withdraw(amount: Double) {
        if (amount > balance + overdraftLimit)
            throw InsufficientFundsException(amount, balance + overdraftLimit)
        balance -= amount
    }
}

fun main() {
    val savings = SavingsAccount("SAV-1", 1000.0, 0.05)
    val checking = CheckingAccount("CHK-1", 200.0, 500.0)
    val accounts: List<Account> = listOf(savings, checking)   // polymorphic

    savings.applyInterest()             // 1050
    savings.transferTo(checking, 300.0) // savings 750, checking 500
    checking.withdraw(900.0)            // -400 within limit

    accounts.forEach(::println)

    try {
        savings.withdraw(99_999.0)
    } catch (e: InsufficientFundsException) {
        println("Denied. Short by ${e.shortfall}")
    }
}