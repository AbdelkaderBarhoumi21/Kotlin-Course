package Delegation

import kotlin.properties.Delegates

// property = KProperty — the name, type of the property
// old      = the current value before the change
// new      = the value being assigned
// return Boolean → true = accept, false = reject
class BankAccount {
    var balance: Int by Delegates.vetoable(0) { property, old, new ->
        if (new < 0) {
            println("Rejected! Cannot set balance to $new")
            false  // reject — old value is kept
        } else {
            println("Accepted: $old → $new")
            true
        }
    }
}

fun main(args: Array<String>) {
    val account = BankAccount()
    account.balance = 100    // Accepted: 0 → 100
    account.balance = -20    // Rejected! Cannot set balance to -20
    println(account.balance) // → 100 (unchanged)
}