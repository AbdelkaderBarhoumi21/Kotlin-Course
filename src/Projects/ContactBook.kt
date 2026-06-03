package Projects

// data class auto-generates equals/hashCode/toString/copy. email is a real nullable type.
data class Contact(
    val name: String,
    val phone: String, val email: String? = null
)

class ConatctBook {
    private val byPhone = LinkedHashMap<String, Contact>()

    fun add(contact: Contact) {
        require(contact.phone !in byPhone) {
            "Phone already exists: ${contact.phone}"
        }
    }

    fun findByPhone(phone: String): Contact? = byPhone[phone]   // returns nullable


    fun search(query: String): List<Contact> {
        val q = query.lowercase()
        return byPhone.values.filter { it -> it.name.lowercase().contains(q) }.sortedBy { it -> it.name }
    }

    fun delete(phone: String): Boolean = byPhone.remove(phone) != null

    fun all(): List<Contact> = byPhone.values.toList()
}


fun main(args: Array<String>) {
    val book = ConatctBook()
    book.add(Contact("Alice", "111", "alice@gmail.com"))
    book.add(Contact("Bob", "222"))

    book.search("al").forEach { it -> println(it) }
    // Safe-call + Elvis: no NullPointerException possible
    val email = book.findByPhone("222")?.email ?: "No email"
    println("Bob's email: $email")                  // (no email)

    book.delete(
        "111"
    )
    println("Total: ${book.all().size}")            // 1


}