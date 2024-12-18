
package _2019.d21

import _2019.IntCode
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day21(private val isTest: Boolean, override val input : String) : Day<Long>(input) {
    val intCode = IntCode(input, freeInputMode = true).execute(true)
    override fun solve1(): Long {
        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day21(false, readFullText("_2019/d21/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day21(true, readFullText("_2019/d21/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}