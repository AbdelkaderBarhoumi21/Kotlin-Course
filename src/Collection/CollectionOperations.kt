package Collection

data class Student(val name: String, val age: Int, val gpa: Double, val major: String)

fun main(args: Array<String>) {
    val students = listOf(
        Student("Alice", 22, 16.5, "Computer Science"),
        Student("Bob", 20, 12.0, "Mathematics"),
        Student("Charlie", 23, 14.5, "Computer Science"),
        Student("Diana", 21, 18.0, "Physics"),
        Student("Eve", 22, 11.5, "Computer Science")
    )

    // 1. filter — keep only elements where condition is true
    // val highScores = students.filter { student -> student.gpa >= 14 }
    val highScores =
        students.filter { it.gpa >= 14 } //it(element) the default one returned by kotlin else you can use student -> student.gpa >= 14
    println("High Scores: $highScores")
    // 2. map — transform each element into something else
    val names = students.map { student -> student.name }
    println("Names: $names")


    // 3. sortedBy / sortedByDescending — sort the list by a criterion
    val byGpa = students.sortedByDescending { student -> student.gpa }
    println("By Gpa: $byGpa") // → [Diana(18.0), Alice(16.5), Charlie(14.5), Bob(12.0), Eve(11.5)]

    // 4. groupBy — group elements by a shared criterion
    val byMajor = students.groupBy { student -> student.major }
    println("By Major: $byMajor")
    // → {
    //     "Computer Science" → [Alice, Charlie, Eve],
    //     "Mathematics"      → [Bob],
    //     "Physics"          → [Diana]
    //   }


    // 5. associate — manually build a Map, you define both key and value
    val nameToGpa = students.associate { student -> student.name to student.gpa }
    println("NameToGpa: $nameToGpa") // → {"Alice"=16.5, "Bob"=12.0, ...}

    // 6. associateBy  → key = criterion,     value = full element
    //    associateWith → key = full element,  value = criterion

    val byName = students.associateBy { student -> student.name }  // Map<String, Student>
    println("Associated By Name: $byName")

    val ages = students.associateWith { student -> student.age }  // Map<Student, Int>
    println("Associated By Ages: $ages")

    // 7. partition — split into two lists based on a condition
    val (passing, failing) = students.partition { student -> student.gpa >= 12 }
    println("Passing: $passing")
    println("Failing: $failing")

    // 8. flatMap — like map, but flattens nested lists into one single list
    // flatMap pass with 2 steps :
    // step 1 map each element == listOf("Hello", "World").map { it.toList() }  => Result = [ ['H','e','l','l','o'], ['W','o','r','l','d'] ]
    // step 2 flat all the list in one list =>  [ ['H','e','l','l','o'], ['W','o','r','l','d'] ].flatten() => result = ['H','e','l','l','o','W','o','r','l','d']
    // Long version
    val step1 = listOf("Hello", "World").map { it.toList() }
    val step2 = step1.flatten()
    println("Step1: $step1")
    println("Step2: $step2")

    val allLetters = listOf<String>("Hello", "World")
    val flatedLettres = allLetters.flatMap { letter -> letter.toList() }
    println("All letters: $flatedLettres")

    // 9. reduce — aggregate into one value, starts with the first element
    // acc = accumulator (running result)
    // steps: 16.5 → 28.5 → 43.0 → 61.0 → 72.5
    val totalGpa = students.map { student -> student.gpa }.reduce { acc, element -> acc + element }
    println("Total Gpa: $totalGpa")

    val averageGpa = students.map { it.gpa }.average() // → 14.5
    println("Average Gpa: $averageGpa")

    val totalAge = students.fold(0) { acc, element -> acc + element.age }
    println("Total Age: $totalAge")

    // 10. first / firstOrNull / maxByOrNull — search for a specific element
    //     firstOrNull is the safe version → returns null instead of crashing

    val firstCs = students.first { student -> student.major == "Computer Science" }
    println("First Cs: $firstCs")

    val firstCsOrNull = students.firstOrNull { st -> st.major == "Computer" }
    println("First CsOrNull: $firstCsOrNull")

    val maxOrNull = students.maxByOrNull { student -> student.gpa }
    println("Max OrNull: $maxOrNull")

    // 11. all  — true if ALL elements match the condition
    //     any  — true if AT LEAST ONE element matches
    //     none — true if NO element matches

    val allPassing = students.all { student -> student.gpa >= 10 }
    println("All Passing: $allPassing")
    val atLeastOneExcellent = students.any { student -> student.gpa >= 18 }
    println("At LeastOneExcellent: $atLeastOneExcellent")

    val nobodyFailing = students.none { student -> student.gpa < 10 }
    println("Nobody: $nobodyFailing")

    // 12. count / sumOf — counting and summing with a condition

    val csCount = students.count { student -> student.major == "Computer Science" }
    println("CsCount $csCount")

    val totalAges = students.sumOf { student -> student.age }
    println("Total Ages: $totalAges")

    // 13. distinct   — remove duplicate values from a list
    //     distinctBy — keep only the first element per unique criterion

    val uniqueMajors = students.map { it.major }.distinct()
    println("Unique Majors: $uniqueMajors")

    val oneSamplePerMajor = students.distinctBy { it.major }
    println("OneSample Per Major: $oneSamplePerMajor")

    // 14. take      — keep the first N elements
    //     drop      — skip the first N elements
    //     takeWhile — keep elements while condition is true, stop at first false

    val firstThree = students.take(3)
    println("First Three: $firstThree")

    val skipFirstTwo = students.drop(2)
    println("Skip First Two: $skipFirstTwo")

    val takeWhile = students.takeWhile { it.gpa > 15 }
    println("Take While: $takeWhile")

    // 15. chunked  — split into fixed-size groups (no overlap)
    //     windowed — sliding window of fixed size (moves one step at a time)
    val pairs = students.chunked(2) // [[Alice, Bob], [Charlie, Diana], [Eve]]
    println("Pairs: $pairs")

    val window = (1..5).windowed(3) // [[1,2,3], [2,3,4], [3,4,5]]
    println("Window: $window")

    // 16. zip — combine two lists by position into a list of Pairs
    //     if lists have different sizes, the shorter one wins

    val names1 = listOf<String>("Ahmed", "Ali")
    val ages1 = listOf<Int>(10, 12, 20, 25)
    val zip1 = names1.zip(ages1) // → [(Alice, 22), (Bob, 20)]
    println("Zip1 shorter win : $zip1")
    val names2 = listOf<String>("Alice", "Bob", "Charlie")
    val ages2 = listOf<Int>(22, 33, 44)
    val zip2 = names2.zip(ages2)
    println("Zip2 : $zip2")

    // 17. Chaining operations — combine multiple functions in sequence
    // map{} return List<T>
    // filter{} return List<T>
    // sortedBy{} return List<T>
 
    val topCsNames =
        students.filter { it.major == "Computer Science" }.filter { it.gpa >= 15 }.sortedByDescending { it.gpa }
            .map { it.name }
    println("Top Cs Names: $topCsNames")


}