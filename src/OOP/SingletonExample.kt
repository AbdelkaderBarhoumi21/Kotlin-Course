package OOP

object AppSettings {
    private val settings = mutableMapOf<String, String>()


    // Save a setting
    fun save(key: String, value: String): Unit {
        settings[key] = value
        println("Saved: $key = $value")
    }

    // Get a setting with a default value
    fun load(key: String, default: String = ""): String {
        return settings[key] ?: default
//                           ^^
//                           si null → retourne default
    }

    // Delete a setting
    fun delete(key: String) {
        settings.remove(key)
        println("Deleted: $key")
    }

    // show all settings 
    fun showAll() {
        if (settings.isEmpty()) {
            println("Nothing to show")
            return
        }
        println("--- All Settings ---")
        for ((key, value) in settings) {
            println("$key - $value")
        }

    }


}

fun main(array: Array<String>) {
    AppSettings.save("language", "en")
    // Output: Saved: language = en

    AppSettings.save("theme", "dark")
    // Output: Saved: theme = dark

    AppSettings.save("fontSize", "16")
    // Output: Saved: fontSize = 16
    AppSettings.showAll()

    AppSettings.load("theme")           // "dark"
    AppSettings.load("unknown")         // ""      ← default value
    AppSettings.load("unknown", "N/A")  // "N/A"   ← custom default

    AppSettings.delete("theme")
    // Output: Deleted: theme

    AppSettings.showAll()
    // Output:
    // --- All Settings ---
    // language → en
    // fontSize → 16

}