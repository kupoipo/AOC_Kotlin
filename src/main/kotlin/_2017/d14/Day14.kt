package _2017.d14

import _2017.d10.KnotHash
import util.*
import kotlin.system.measureNanoTime

class Day14(override val input: String) : Day<Long>(input) {
    val list : List<String> =(0..127).map { KnotHash.getKnotHashesFrom("$input-$it") }.map { line ->
        buildString {
            for (c in line) {
                append(Integer.toBinaryString(c.toString().toInt(16)).padStart(4, '0'))
            }
        }
    }

    val map = matrixFromString(list.joinToString("\n"), 0) { if (it.digitToInt() == 1) -1 else 0 }

    override fun solve1(): Long = list.sumOf { line -> line.count { it == '1' } }.toLong()

    fun launchPropagation(value: Int, position: Point) {
        if (position.outOfMap(map)) return
        if (map[position] != -1) return
        map[position] = value

        position.adjacent(false).forEach { launchPropagation(value, it) }

    }
    override fun solve2(): Long {
        var index = 1

        map.points().forEach {
            if (map[it] == -1) {
                launchPropagation(index, it)
                index++
            }
        }

        return index.toLong()
    }
}

fun main() {
    val day = Day14(readFullText("_2017/d14/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day14(readFullText("_2017/d14/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}