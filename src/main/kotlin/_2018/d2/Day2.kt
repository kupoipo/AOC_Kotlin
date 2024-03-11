package _2018.d2

import util.Day
import util.allArrangement
import util.combination
import util.readFullText
import kotlin.system.measureNanoTime
class Day2(override val input : String) : Day<Long>(input) {
    private val lines = input.split("\n")
    private val groups = lines.map { line -> line.groupingBy { char -> char }.eachCount() }
    private val containsTwo = groups.filter { it.values.any { it == 2 } }
    private val containsThree = groups.filter { it.values.any { it == 3 } }
    override fun solve1(): Long = (containsThree.size * containsTwo.size).toLong()

    override fun solve2(): Long {
        lines.combination().forEach { combination ->
            difference(combination.first, combination.second).let {
                if (it != -1) {
                    println(combination.first.removeRange(it..it))
                }
            }
        }
        return 0L
    }

    private fun difference(l: String, l2: String) : Int {
        var cpt = -1

        for (i in l.indices) {
            if (l[i] != l2[i]) {
                if (cpt != -1) return -1
                cpt = i
            }
        }

        return cpt
    }
}

fun main() {
    val day = Day2(readFullText("_2018/d2/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day2(readFullText("_2018/d2/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}