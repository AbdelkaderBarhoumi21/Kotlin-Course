package Projects.OrderSystem.Repository

import Projects.OrderSystem.Domain.OrderDto
import Projects.OrderSystem.Domain.OrderModel
import java.io.File
class FileOrderRepository(private val path: String) : OrderRepository {
    private val file get() = File(path)

    override fun save(order: OrderModel) {
        val existing = loadAllRaw()
        val updated = existing + order.toDto().toJson()

        val lines = mutableListOf<String>()
        lines.add("[")
        updated.forEachIndexed { index, json ->
            lines.add("  $json")
            if (index < updated.size - 1) lines[lines.lastIndex] += ","
        }
        lines.add("]")

        file.writeText(lines.joinToString("\n"))
    }

    override fun loadAll(): List<OrderDto> {
        // Simplified — no full parser
        return emptyList()
    }

    private fun loadAllRaw(): List<String> {
        if (!file.exists()) return emptyList()
        return try {
            val text = file.readText().trim()
            if (text.isEmpty() || text == "[]") return emptyList()

            val content = text.removePrefix("[").removeSuffix("]").trim()
            if (content.isEmpty()) return emptyList()

            val objects = mutableListOf<String>()
            var braceCount = 0
            var current = StringBuilder()

            for (char in content) {
                when (char) {
                    '{' -> {
                        braceCount++
                        current.append(char)
                    }
                    '}' -> {
                        braceCount--
                        current.append(char)
                        if (braceCount == 0) {
                            objects.add(current.toString().trim())
                            current = StringBuilder()
                        }
                    }
                    else -> {
                        if (braceCount > 0) current.append(char)
                    }
                }
            }
            objects
        } catch (_: Exception) {
            emptyList()
        }
    }
}