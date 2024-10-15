
package _2019.d9

import _2019.IntCode
import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day9(private val isTest: Boolean, override val input : String) : Day<Long>(input) {
    override fun solve1(): Long = IntCode(input, 1) .execute()
    override fun solve2(): Long = IntCode(input, 2) .execute()
}

fun main() {
    val day = Day9(false, readFullText("_2019/d9/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day9(true, readFullText("_2019/d9/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}