package Delegation

import kotlin.reflect.KProperty

// A delegate that logs every read and write
class LoggingDelegate<T>(private var value: T) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("[READ]  ${property.name} → $value")
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
        println("[WRITE] ${property.name}: $value → $newValue")
        value = newValue
    }
}

class Settings {
    var theme: String by LoggingDelegate("light")
    var fontSize: Int by LoggingDelegate(14)
}

fun main() {
    val s = Settings()

    val t = s.theme         // [READ]  theme → light
    s.theme = "dark"        // [WRITE] theme: light → dark
    val t2 = s.theme        // [READ]  theme → dark

    s.fontSize = 18         // [WRITE] fontSize: 14 → 18
    val f = s.fontSize      // [READ]  fontSize → 18
}
