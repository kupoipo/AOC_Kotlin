package _2015.d20

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime


class Day20(override val input: String) : Day<Long>(input) {
    val total = 29000000

    val houses = MutableList(total + 1) { 0 }
    override fun solve1(): Long {
        (1..total / 10).forEach { elf ->
            for (house in elf..total / 10 step elf) {
                houses[house] += elf * 10
            }
        }

        return houses.indices.first { houses[it] > total }.toLong()
    }

    override fun solve2(): Long {
        (1..total / 10).forEach { elf ->
            for (house in elf..(if (elf * 50 > total) total else elf * 50) step elf) {
                houses[house] += elf * 11

            }
        }

        return houses.indices.first { houses[it] > total }.toLong()
    }
}

fun main() {
    val day = Day20(readFullText("_2015/d20/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day20(readFullText("_2015/d20/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}