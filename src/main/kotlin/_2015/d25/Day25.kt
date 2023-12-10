package _2015.d25

import util.Day
import util.emptyMatrixOf
import util.readFullText
import kotlin.system.measureNanoTime

class Day25(override val input: String) : Day<Long>(input) {
    private fun numPassword(row: Long, col: Long): Long {
        var start = 1L
        var add = 1L
        repeat((row - 1).toInt()) {
            start += add
            add++
        }

        add++

        repeat((col - 1).toInt()) {
            start += add
            add++
        }
        return start
    }

    private fun valPassword(numPassword: Long) : Long {
        var current = START

        repeat(numPassword.toInt() - 1)  {
            current = (current * MULTIPLY) % MODULO
        }

        return current
    }

    override fun solve1(): Long = valPassword(numPassword(ROW, COLUMN))

    override fun solve2(): Long {
        return -1
    }

    companion object {
        const val MULTIPLY = 252533L
        const val MODULO = 33554393L
        const val START = 20151125L
        const val ROW = 2978L
        const val COLUMN = 3083L
    }
}

fun main() {
    val day = Day25(readFullText("_2015/d25/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day25(readFullText("_2015/d25/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}