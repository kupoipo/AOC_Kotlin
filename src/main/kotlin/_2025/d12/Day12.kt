
package _2025.d12

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day12(private val isTest: Boolean, override val input : String) : Day<Long>(input) {
    override fun solve1(): Long {
        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    var day: Day12
    println("Temps construction : ${measureNanoTime { day = Day12(false, readFullText("_2025/d12/input")) } / 1e9}s")
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            var dayTest: Day12
            println("Test $i")
            println("Temps construction : ${measureNanoTime { dayTest = Day12(false, readFullText("_2025/d12/test$i")) } / 1e9}s")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}