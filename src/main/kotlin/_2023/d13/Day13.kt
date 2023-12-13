package _2023.d13

import _2023.d13.Mirror.Companion.COLUMN
import util.*
import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.seconds

typealias Reflexion = Pair<Int, Int>

class Mirror(val map: Matrix<Char>) {
    private fun getReflexionFrom(max: Int, function: (Int) -> List<Char>) : Int{
        for (line in 1 until max) {
            var index = 1
            var total = 0

            while (line + index - 1 < max && line - index >= 0) {
                val column1 = function(line + index - 1)
                val column2 = function(line - index)

                total += column1.indices.count { column1[it] != column2[it] }

                index++
            }

            if (total == maxDiff) return line
        }

        return -1
    }

    fun getReflexion(): Reflexion {
        getReflexionFrom(map.size) { map[it] }.let {
            if (it != -1) {
                return LINE to it
            }
        }

        return COLUMN to getReflexionFrom(map[0].size) { map.map { line -> line[it] } }
    }

    companion object {
        const val LINE = 1
        const val COLUMN = 0
        var maxDiff = 0
    }
}

class Day13(override val input: String) : Day<Long>(input) {
    private val mirrors = input.split("\n\n").map { Mirror(matrixFromString(it, '.') { it }) }

    override fun solve1(): Long = mirrors.map { it.getReflexion() }.sumOf {
        if (it.first == COLUMN) {
            it.second.toLong()
        } else {
            it.second * 100L
        }
    }

    override fun solve2(): Long {
        Mirror.maxDiff = 1
        return mirrors.map { it.getReflexion() }.sumOf {
            if (it.first == COLUMN) {
                it.second.toLong()
            } else {
                it.second * 100L
            }
        }
    }


}

fun main() {
    val day = Day13(readFullText("_2023/d13/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day13(readFullText("_2023/d13/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}