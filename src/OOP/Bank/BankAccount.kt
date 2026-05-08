package OOP.Bank

open class BankAccount(initialBalance: Double) {

    // private — only inside BankAccount class
    private var balance = initialBalance

    // protected — BankAccount + subclasses only
    protected open val accountType = "STANDARD"

    // internal — visible anywhere in the same module
    internal val transactionLog = mutableListOf<String>()

    // public — visible everywhere (default)
    fun deposit(amount: Double) {
        balance += amount
        transactionLog.add("Deposited $$amount")
        println("[$accountType] Deposited $$amount")
    }

    fun withdraw(amount: Double) {
        if (amount > balance) {
            println("[$accountType] Insufficient funds!")
            return
        }
        balance -= amount
        transactionLog.add("Withdrew $$amount")
        println("[$accountType] Withdrew $$amount")
    }

    fun showBalance() {
        println("[$accountType] Balance: $$balance")
        //                                ^^^^^^^
        //  ✅ private — accessible inside BankAccount
    }
}