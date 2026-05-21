package Exceptions

// Custom exception — holds extra data about what went wrong
class InsufficientFundsException(
    val accountId: String,
    val requested: Double,
    val available: Double
) : RuntimeException("Account $accountId: requested $requested, only $available available")

// Account data class
data class Account(
    val id: String,
    var balance: Double
)

// Withdraw function — throws if not enough funds
fun withdraw(account: Account, amount: Double) {
    if (amount > account.balance) {
        throw InsufficientFundsException(account.id, amount, account.balance)
    }
    account.balance -= amount
}

fun main() {
    val myAccount = Account(id = "ACC001", balance = 50.0)

    // ── Case 1: not enough funds ──
    try {
        withdraw(myAccount, 1000.0)
    } catch (e: InsufficientFundsException) {
        println("Could not withdraw ${e.requested} from ${e.accountId}")
        println("Available: ${e.available}")
        println("Full message: ${e.message}")
    }

    // ── Case 2: enough funds ──
    val richAccount = Account(id = "ACC002", balance = 5000.0)
    withdraw(richAccount, 1000.0)
    println("Withdrawal successful — new balance: ${richAccount.balance}")
}