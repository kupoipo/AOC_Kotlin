package _2016.d15

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day15(override val input: String) : Day<Long>(input) {
    val gears: MutableList<Pair<Int, Int>> =
        input.split("\n").mapIndexed { index, gear -> gear.allInts().let { it[1] to (it.last() + index) % it[1] } }.toMutableList()

    fun solve() : Long {
        repeat(10_000_000) { r ->
            if (gears.all { (it.second + r) % it.first == it.first - 1 }) {
                return r.toLong()
            }
        }
        return -1
    }

    override fun solve1(): Long = solve()

    override fun solve2(): Long {
        gears.add(11 to gears.lastIndex + 1)
        return solve()
    }
}

fun main() {
    val day = Day15(readFullText("_2016/d15/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day15(readFullText("_2016/d15/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}