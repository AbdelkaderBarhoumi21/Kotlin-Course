package OOP.Bank

fun main() {

    val standard = BankAccount(1000.0)
    val premium = PremiumAccount(5000.0)
    val service = TransactionService()

    // ✅ public — accessible everywhere
    standard.deposit(500.0)
    // Output: [STANDARD] Deposited $500.0

    standard.withdraw(200.0)
    // Output: [STANDARD] Withdrew $200.0

    standard.showBalance()
    // Output: [STANDARD] Balance: $1300.0

    // ✅ internal — same module
    println(standard.transactionLog)
    // Output: [Deposited $500.0, Withdrew $200.0]

    // ✅ premium uses protected accountType
    premium.deposit(1000.0)
    // Output: [PREMIUM] Deposited $1000.0

    premium.showAccountType()
    // Output: Account type: PREMIUM

    // ✅ TransactionService — same module, accesses internal
    service.printLog(standard)
    // Output:
    // --- Transaction Log ---
    // Deposited $500.0
    // Withdrew $200.0

    service.transfer(premium, standard, 500.0)
    // Output:
    // Transferring $500.0...
    // [PREMIUM] Withdrew $500.0
    // [STANDARD] Deposited $500.0
    // Transfer complete!

    // ❌ private — not accessible outside class
    // standard.balance = 9999.0  ← ERROR

    // ❌ protected — not accessible outside class/subclass
    // standard.accountType       ← ERROR
}