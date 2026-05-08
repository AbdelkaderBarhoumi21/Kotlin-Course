package OOP.Bank

// Subclass of BankAccount
class PremiumAccount(initialBalance: Double) : BankAccount(initialBalance) {

    // protected — can override because parent is "protected open"
    override val accountType = "PREMIUM"

    fun showAccountType() {
        println("Account type: $accountType")
        //                      ^^^^^^^^^^^
        //  ✅ protected — accessible in subclass
    }

    fun tryAccessBalance() {
        // balance  ❌ — private, not accessible here
        // balance += 100  ← this would be an error
    }
}