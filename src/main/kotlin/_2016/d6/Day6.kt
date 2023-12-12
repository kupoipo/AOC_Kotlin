package _2016.d6

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day6(override val input: String) : Day<String>(input) {
    private val columns = MutableList(input.indexOf("\n")) { mutableListOf<Char>() }

    init {
        input.split("\n").forEach { line -> line.forEachIndexed { index, c -> columns[index] += c } }
    }

    override fun solve1(): String = buildString {
        columns.forEach {
            this.append(it.groupingBy { it }.eachCount().maxBy { it.value }.key)
        }
    }

    override fun solve2(): String = buildString {
        columns.forEach {
            this.append(it.groupingBy { it }.eachCount().minBy { it.value }.key)
        }
    }
}

fun main() {
    val day = Day6(readFullText("_2016/d6/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day6(readFullText("_2016/d6/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}