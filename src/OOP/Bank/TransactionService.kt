package OOP.Bank

// Same module as BankAccount → can access internal members
class TransactionService {

    fun printLog(account: BankAccount) {
        println("--- Transaction Log ---")
        for (log in account.transactionLog) {
            //          ^^^^^^^^^^^^^^
            //  ✅ internal — same module, accessible
            println(log)
        }
    }

    fun transfer(from: BankAccount, to: BankAccount, amount: Double) {
        println("Transferring $$amount...")
        from.withdraw(amount)
        to.deposit(amount)
        println("Transfer complete!")
    }
}