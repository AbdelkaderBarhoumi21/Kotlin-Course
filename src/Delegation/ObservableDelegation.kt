package Delegation

import kotlin.properties.Delegates

class User {
    var name: String by Delegates.observable("Uknown") { property, oldValue, newValue ->
        println("'${property.name}' changed: '$oldValue' → '$newValue'")
    }
}

fun main(args: Array<String>) {
    val user = User()
    user.name = "Abdelkader"
    println(user.name)
    user.name = "Barhoumi"
    println(user.name)
}