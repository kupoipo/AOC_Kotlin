package _2017.d15

import util.Day
import util.allInts
import util.allLong
import util.readFullText
import kotlin.system.measureNanoTime

class Day15(override val input: String) : Day<Long>(input) {
    val generators = 16807L to 48271L
    var factors = input.allLong().let { it.first() to it.last() }

    override fun solve1(): Long {
        var total = 0L
        repeat(5_000_000) {
            factors =
                ((factors.first * generators.first) % 2147483647L) to ((factors.second * generators.second) % 2147483647L)

            while (factors.first % 4 != 0L) {
                factors = ((factors.first * generators.first) % 2147483647L) to factors.second
            }

            while (factors.second % 8 != 0L) {
                factors = factors.first to ((factors.second * generators.second) % 2147483647L)
            }


            if (factors.first.toString(2).padStart(32, '0').takeLast(16) == factors.second.toString(2)
                    .padStart(32, '0').takeLast(16)
            ) total++
        }
        return total
    }


    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day15(readFullText("_2017/d15/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day15(readFullText("_2017/d15/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}