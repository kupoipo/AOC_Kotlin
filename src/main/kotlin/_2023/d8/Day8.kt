package _2023.d8

import util.*
import kotlin.math.pow
import kotlin.system.measureNanoTime

class Day8(override val input: String) : Day<Long>(input) {
    val graph = mutableMapOf<String, MutableList<String>>()
    var instructions: String

    init {
        input.split("\n\n").let { (instructions, path) ->
            this.instructions = instructions.replace("L", "0").replace("R", "1")
            path.split("\n").forEach { line ->
                line.findAllMatch("""\w+""").let { (origin, left, right) ->
                    graph.getOrPut(origin) { mutableListOf(left) }.add(right)
                }
            }
        }
    }

    fun nbStepTo(start: String, end: List<String>): Long {
        var current = start
        var indexInstruction = 0 // 147
        var step = 0L

        while (current !in end) {
            current = graph[current]!![instructions[indexInstruction].digitToInt()]
            indexInstruction = (indexInstruction + 1) % instructions.length
            step++
        }

        return step
    }

    private fun PPCM(nb: List<Long>): Long {
        val m = mutableMapOf<Long, Int>()

        nb.map {
            it.toPrimeFactor().forEach { (prime, factor) ->
                m[prime] = m[prime]?.coerceAtMost(factor) ?: factor
            }
        }

        return m.map { (k, v) -> k.toDouble().pow(v) }.reduce { i, j -> i * j }.toLong()
    }

    override fun solve1(): Long = nbStepTo("AAA", listOf("ZZZ"))

    override fun solve2(): Long = PPCM(graph.keys.filter { it.last() == 'Z' }.let { ends ->
        graph.keys.filter { it.last() == 'A' }.map { start -> nbStepTo(start, ends) }
    })
}

fun main() {
    val day = Day8(readFullText("_2023/d8/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day8(readFullText("_2023/d8/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}