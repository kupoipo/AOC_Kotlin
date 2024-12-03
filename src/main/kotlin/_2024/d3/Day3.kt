package _2024.d3

import util.Day
import util.allLong
import util.listOfMatch
import util.readFullText
import kotlin.system.measureNanoTime

class Day3(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private  val regex = Regex("""mul\(-?\d+,-?\d+\)""")

    fun sumOfMul(line: String) = regex.listOfMatch(line).sumOf { match -> match.allLong().let { it.first() * it.last() } }

    override fun solve1(): Long = sumOfMul(input)

    override fun solve2(): Long {
        val toDos = input.split("do()").map {
            try {
                it.take(it.indexOf("don't()"))
            } catch (e: Exception) {
                it
            }
        }

        return toDos.sumOf(::sumOfMul)
    }
}

fun main() {
    val day = Day3(false, readFullText("_2024/d3/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day3(true, readFullText("_2024/d3/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}