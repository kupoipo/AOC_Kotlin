package _2021.d22

import _2021.d18.split
import util.*
import kotlin.system.measureNanoTime

class Day22(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    override fun solve1(): Long {
        return -1
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day22(false, readFullText("_2021/d22/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day22(true, readFullText("_2021/d22/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}