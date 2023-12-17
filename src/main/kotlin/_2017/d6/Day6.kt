package _2017.d6

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime
class Day6(override val input : String) : Day<Long>(input) {
    val banks = input.allInts()
    val situationSeen = mutableSetOf<List<Int>>(banks.toMutableList())

    override fun solve1(): Long {
        while (true) {
            val toDistribute = banks.max()
            val from = banks.indexOfFirst { it == toDistribute }
            banks[from] = 0

            repeat(toDistribute) {
                banks[(from + it + 1)%banks.size]++
            }

            if (banks in situationSeen)
                return situationSeen.size.toLong()

            situationSeen.add(banks.toMutableList())
        }
    }
    override fun solve2(): Long {
        return situationSeen.size.toLong() - situationSeen.indexOfFirst { it == banks }
    }
}

fun main() {
    val day = Day6(readFullText("_2017/d6/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day6(readFullText("_2017/d6/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}