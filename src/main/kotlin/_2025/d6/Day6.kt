package _2025.d6

import util.*
import kotlin.system.measureNanoTime

class Day6(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val data = input.split("\n").dropLast(1).toMutableList()
    private val operators = mutableListOf<String>()
    private val columns = mutableListOf<MutableList<String>>()

    init {
        val tempOperators = input.split("\n").last().split("").drop(1).dropLast(1).toMutableList()
        while (tempOperators.isNotEmpty()) {
            this.operators.add(
                buildString {
                    append(tempOperators.removeFirst())
                    while (tempOperators.isNotEmpty() && tempOperators.first() == " ") {
                        append(tempOperators.removeFirst())
                    }
                }
            )
        }

        for (operator in operators) {
            val col = mutableListOf<String>()

            for (i in data.indices) {
                val line = data[i]
                col.add(line.take(operator.length - 1))
                data[i] = line.drop(operator.length)
            }
            columns.add(col)
        }
    }

    override fun solve1(): Long = operators.indices.sumOf { i ->
        val col = columns[i].map { it.firstInt().toLong() }

        if (operators[i].contains("+"))
            col.sum() else col.reduce(Long::times)
    }

    override fun solve2(): Long = operators.indices.sumOf { i ->
        val col = columns[i]
        val newCol = mutableListOf<Long>()
        
        for (j in col.first().lastIndex downTo 0)
        {
            var longCol = 0L
            
            for (n in col) {
                val unity = n[j]
                if (unity != ' ' ) {
                    longCol += unity.digitToInt().toLong()
                    longCol *= 10
                }
            }
            
            longCol /= 10
            newCol.add(longCol)
        }
        
        if (operators[i].contains("+"))
            newCol.sum() else newCol.reduce(Long::times)
    }
}

fun main() {
    val day = Day6(false, readFullText("_2025/d6/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day6(true, readFullText("_2025/d6/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}