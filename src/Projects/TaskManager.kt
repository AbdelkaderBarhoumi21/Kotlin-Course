package Projects

/*

tasks = [
    Task(1, HIGH,   TODO),
    Task(2, MEDIUM, DONE),
    Task(3, HIGH,   IN_PROGRESS),
    Task(4, LOW,    DONE)
]

groupingBy { it.priority }
        │
        ▼
HIGH   → [Task1, Task3]
MEDIUM → [Task2]
LOW    → [Task4]
        │
        ▼ eachCount()
HIGH   → 2
MEDIUM → 1
LOW    → 1
 */

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class Status {
    TODO, IN_PROGRESS, DONE
}


data class Task(
    val id: Int,
    val title: String,
    val priority: Priority,
    val status: Status,
    val dayDue: Int
)

// Generic reusable repository
class Repository<T> {
    private val items = mutableListOf<T>()
    fun add(item: T) {
        items.add(item)
    }

    fun findAll(): List<T> = items.toList()
    fun findWhere(predicate: (T) -> Boolean): List<T> = items.filter(predicate)
}

class TaskAnalytics(private val tasks: List<Task>) {
    fun groupByStatus(): Map<Status, List<Task>> = tasks.groupBy { task -> task.status }
    fun countByPriority(): Map<Priority, Int> = tasks.groupingBy { task -> task.priority }.eachCount()
    fun overDue(today: Int): List<Task> =
        tasks.filter { t -> t.status != Status.DONE && t.dayDue < today }.sortedBy { t -> t.dayDue }

    fun completionRate(): Double {
        if (tasks.isEmpty()) return 0.0
        val done = tasks.count { task -> task.status == Status.DONE }
        return done.toDouble() / tasks.size * 100
    }


}

fun main(args: Array<String>) {

    val repo = Repository<Task>()
    repo.add(Task(1, "Write report", Priority.HIGH, Status.TODO, 5))
    repo.add(Task(2, "Email client", Priority.MEDIUM, Status.DONE, 2))
    repo.add(Task(3, "Fix bug", Priority.HIGH, Status.IN_PROGRESS, 1))
    repo.add(Task(4, "Tidy desk", Priority.LOW, Status.DONE, 10))
    val analytics = TaskAnalytics(repo.findAll()) // don't create analytics before adding tasks

    println("By status: ${analytics.groupByStatus().keys}")
    println("Counts:    ${analytics.countByPriority()}")
    println("Overdue:   ${analytics.overDue(3)}")              // task 3
    println("Done: ${"%.1f".format(analytics.completionRate())}%")  // 50.0%

    val highPriority = repo.findWhere { t -> t.priority == Priority.HIGH }
    println("High priority: ${highPriority.size}")

    // Bonus: the (passing, failing) partition idiom

    val (done, pending) = repo.findAll().partition { t -> t.status == Status.DONE }
    println("Done: ${done.size}, pending: ${pending.size}")


}