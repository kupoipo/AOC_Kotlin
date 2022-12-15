import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeText


import java.lang.StrictMath.abs

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()

fun afficheMap(map : List<List<Any>>) {
    print( "   ")

    for (i in 0 until map[0].size) {
        print("%3d".format(i))
    }

    println()

    for ((index, line) in map.withIndex()) {
        print("%3d".format(index))
        for (cell in line) {
            print("%3s".format(cell))
        }
        println()
    }
}

fun generateDirectories() {
    for (i in 12..25) {
        Files.createDirectory(Paths.get("./src/main/kotlin/d$i"))
        Files.createFile(Paths.get("./src/main/kotlin/d$i/test.txt"))
        Files.createFile(Paths.get("./src/main/kotlin/d$i/input.txt"))
        var f = Files.createFile(Paths.get("./src/main/kotlin/d$i/Day$i.kt"))

        var str = "package d$i\n" +
                "import readInput\n\n" +
                "fun part1(content : List<String>) : String {\n" +
                "    \n" +
                "    return \"\"\n" +
                "}\n" +
                "\n" +
                "fun part2(content : List<String>) : String {\n" +
                "\n" +
                "    return \"\"\n" +
                "}\n" +
                "\n" +
                "fun main() {\n" +
                "    var content = readInput(\"d$i/input\")\n" +
                "    \n" +
                "    println(part1(content))\n" +
                "    println(part2(content))\n" +
                "}"

        f.writeText(str)
    }
}

class Point(var x : Int, var y : Int) {
    operator fun plus(other : Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(other.x - x, other.y - y)
    fun manhattan(other : Point) = abs(other.x - x) + abs(other.y - y)
}

infix fun IntRange.overlaps(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

fun String.allInts() : List<Int> {
    return """-?\d+""".toRegex().findAll(this).map{ it.value.toInt() }.toList()
}

fun main() {


}