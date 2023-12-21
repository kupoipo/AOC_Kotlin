package _2023.d9

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day9(override val input: String) : Day<Long>(input) {
    fun historic(sequence: MutableList<Int>): List<MutableList<Int>> {
        var current = sequence
        val historic = mutableListOf(sequence)

        do {
            val next = mutableListOf<Int>()

            for (i in 1 until current.size) {
                next.add(current[i] - current[i - 1])
            }

            historic.add(next)
            current = next
        } while (!next.all { it == 0 })

        println(historic)

        return historic.reversed()
    }

    fun nextValue(sequence: MutableList<Int>): Int = historic(sequence).fold(0) { acc, l -> acc + l.last() }

    fun previousValue(sequence: MutableList<Int>): Int = historic(sequence).fold(0) { acc, l -> l.first() - acc }

    override fun solve1(): Long = input.split("\n").sumOf { nextValue(it.allInts()) }.toLong()

    override fun solve2(): Long = input.split("\n").sumOf { previousValue(it.allInts()) }.toLong()

    fun solve() {
        val init = input.allInts()
        repeat( 5) {
            val next = nextValue(init)

            init.add(next)
        }

        print(init.last())
    }
}

fun main() {
    val day = Day9(readFullText("_2023/d9/input"))

   /* val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()*/

    val dayTest = Day9(readFullText("_2023/d9/test"))
    /*val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")*/

    dayTest.solve()
}