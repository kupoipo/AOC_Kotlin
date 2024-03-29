package _2015.d15

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureTimeMillis

const val MAX = 100

class Day15(override val input: String) : Day<Long>(input) {
    private val cookies = input.split("\n").map { it.allInts() }
    private var part2 = false

    private val nbCookie = Array(cookies.size) { 0 }

    fun calculus(): Long {
        val score = MutableList(4) { 0 }
        val calories = cookies.indices.sumOf { cookies[it].last() * nbCookie[it] }

        score.indices.forEach { i ->
            score[i] = nbCookie.indices.sumOf { cookies[it][i] * nbCookie[it] }
        }

        return if (score.any { it <= 0 } || (part2 && calories != 500))
            0
        else
            score.reduce { i, j -> i * j }.toLong()
    }

    private fun maxScore(i: Int): Long {
        var max = 0L

        for (j in 1..MAX) {
            nbCookie[i] = j

            if (nbCookie.sum() == MAX) {
                calculus().let { maxLocal ->
                    if (max < maxLocal)  max = maxLocal
                }
                break
            }

            if (i != nbCookie.size - 1) {
                maxScore(i + 1).let {maxLocal ->
                    if (max < maxLocal) max = maxLocal
                }
            }
        }

        nbCookie[i] = 0
        return max
    }

    override fun solve1(): Long = maxScore(0)
    override fun solve2(): Long {
        part2 = true
        return maxScore(0)
    }
}

fun main() {
    //var day = Day15(readFullText("_2015/d15/test"))
    var day = Day15(readFullText("_2015/d15/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day15(readFullText("_2015/d15/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}