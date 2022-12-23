
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeText


import java.lang.StrictMath.abs

enum class Direction(val dx: Int, val dy : Int) {
    LEFT(-1, 0), RIGHT(1,0), UP(0,-1), DOWN (0,1)
}

fun readInput(name: String) = File("src/main/kotlin", "$name.txt")
    .readLines()

fun afficheMap(map : List<List<Any>>) {
    afficheMap(map, 0, map.size)
}

fun afficheMap(map : List<List<Any>>, from : Int, to : Int) {
    print( "   ")

    for (i in 0 until map[0].size) {
        print("%2d".format(i))
    }

    println()

    for ((index, line) in map.drop(from).take(to - from).withIndex()) {
        print("%2d".format(index))
        for (cell in line) {
            print("%2s".format(cell))
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

data class Point(var x : Int, var y : Int) {
    constructor(p : Point) : this(p.x, p.y)

    operator fun plus(other : Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(other.x - x, other.y - y)
    fun manhattan(other : Point) = abs(other.x - x) + abs(other.y - y)

    override fun toString(): String {
        return "[$y,$x]"
    }
}

infix fun IntRange.overlaps(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this

infix fun IntRange.containsRange(other: IntRange): Boolean = other.first in this && other.last in this

fun String.allInts() : List<Int> {
    return """-?\d+""".toRegex().findAll(this).map{ it.value.toInt() }.toList()
}

typealias Matrix<Char> = MutableList<MutableList<Char>>

fun <T> matrixOf(vararg rows: MutableList<T>): Matrix<T> = MutableList(rows.size) { i -> rows[i] }
fun <T> matrixOf(rows: MutableList<MutableList<T>>): Matrix<T> = MutableList(rows.size) { i -> rows[i] }
fun <T> Matrix<T>.toString(): String = this.joinToString("\n") { it.joinToString(", ") }

fun <T> Matrix<T>.rotateRight() : Unit {
    var temp = matrixOf(this)
    this.removeAll { true }

    repeat (temp[0].size) {
        this.add(MutableList<T> (temp.size) { temp[0][0] } )
    }

    for (y in 0 until temp.size) {
        for (x in 0 until temp[0].size) {
            this[x][temp.size - 1 - y] = temp[y][x]
        }
    }
}
fun Matrix<Char>.addFirstLine(element : Char?) {
    this.add(0, MutableList<Char>(this[0].size) { element?.let { element } ?: ' ' })
}

enum class DIRECTION3D(val dx : Int, val dy : Int, val dz : Int) {
    X1(1,0,0), X_MINUS_1(-1,0,0),Y1(0,-1,0), Y_MINUS_1(-0,1,0),Z1(0,0,-1), Z_MINUS_1(-0,0,1)
}
data class Point3D(val x : Int, val y : Int, val z : Int) {
    operator fun plus(other: Point3D) = Point3D(other.x + x, other.y + y, other.z + z)
    operator fun minus(other: Point3D) = Point3D(other.x - x, other.y - y, other.z -z)
    operator fun times(n: Int) = Point3D    (x * n, y * n, z * n)

    fun getNeighbors() : List<Point3D> {
        val neighbors = mutableListOf<Point3D>()
        DIRECTION3D.values().forEach {
            var newPoint = Point3D(x + it.dx, y + it.dy, z + it.dz)
            neighbors.add(newPoint)
        }
        return neighbors
    }
    override fun toString(): String = "[$x,$y,$z]"
}