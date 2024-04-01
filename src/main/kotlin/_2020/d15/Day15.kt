package _2020.d15

import util.Day
import util.allInts
import util.allLong
import util.readFullText
import java.util.LinkedList
import kotlin.system.measureNanoTime

class Day15(override val input: String) : Day<Long>(input) {

    private fun getRound(rounds: Long): Long {
        val list = MutableList(rounds.toInt()) { 0L }
        input.allInts().let { init ->
            init.dropLast(1).forEachIndexed { index, l -> list[l] = index.toLong() + 1L }
            var lastValue = init.last()
            var newVal : Long

            for (i in init.size..rounds) {
                newVal = if (list[lastValue] == 0L) {
                    0L
                } else {
                    i - list[lastValue]
                }
                list[lastValue] = i
                lastValue = newVal.toInt()
            }
        }
        return list.indexOf(rounds).toLong()
    }

    override fun solve1(): Long = getRound(2020)

    override fun solve2(): Long = getRound(30000000)
}

fun main() {
    val day = Day15(readFullText("_2020/d15/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day15(readFullText("_2020/d15/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}