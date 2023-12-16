package _2016.d18

import util.*
import kotlin.system.measureNanoTime

class Day18(override val input: String) : Day<Long>(input) {
    val map = matrixFromString(input, SAFE) { it }

    fun createRow() {
        val newRow = MutableList(map[0].size) { SAFE }

        for (r in newRow.indices) {
            val traps = (-1..1).count {
                val p = Point(r + it, map.lastIndex)

                p.inMap(map) && map[p] == TRAP
            }

            newRow[r] = if (traps == 3 || traps == 0 || (traps == 1 && map[map.lastIndex][r] == TRAP) || (traps == 2 && map[map.lastIndex][r] == SAFE)) SAFE else TRAP
        }

        map.add(newRow)
    }

    override fun solve1(): Long {
        repeat(39) {
            createRow()
        }

        return map.sumOf { line -> line.count { tile -> tile == SAFE } }.toLong()
    }

    override fun solve2(): Long {
        repeat(400000 - 40) {
            createRow()
        }

        return map.sumOf { line -> line.count { tile -> tile == SAFE } }.toLong()
    }

    companion object {
        const val TRAP = '^'
        const val SAFE = '.'
    }
}

fun main() {
    val day = Day18(readFullText("_2016/d18/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day18(readFullText("_2016/d18/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}