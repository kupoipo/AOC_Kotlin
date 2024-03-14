package _2018.d14

import util.Day
import util.readFullText
import kotlin.math.sign
import kotlin.system.measureNanoTime

class Day14(override val input: String) : Day<Long>(input) {
    private val nbRecipe = input.toInt() + 10
    private val recipes = mutableListOf(3, 7)
    private var firstElf = 0
    private var secondElf = 1

    fun step() {
        (recipes[firstElf] + recipes[secondElf]).let {
            if (it >= 10) {
                recipes.add(1)
                recipes.add(it % 10)
            } else {
                recipes.add(it)
            }
        }

        firstElf = (firstElf + recipes[firstElf] + 1) % recipes.size
        secondElf = (secondElf + recipes[secondElf] + 1) % recipes.size
    }

    private fun scores(i: Int): Long {
        var score = 0L
        val index = i - 10
        repeat(10) {
            score += recipes[index + it]
            score *= 10
        }

        return score / 10
    }

    override fun solve1(): Long {
        while (recipes.size < nbRecipe) {
            step()
        }
        return scores(nbRecipe)
    }

    override fun solve2(): Long {
        while (recipes.takeLast(7).dropLast(1).fold("") { acc, i -> "$acc$i" } != input) {
            step()
        }
        return recipes.size - 6L
    }
}

fun main() {
    val day = Day14(readFullText("_2018/d14/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day14(readFullText("_2018/d14/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}