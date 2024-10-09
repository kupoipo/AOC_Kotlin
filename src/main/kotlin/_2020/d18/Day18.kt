package _2020.d18

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day18(override val input: String) : Day<Long>(input) {
    private fun evaluate(line: String, part2: Boolean = false): Long {
        if (line.contains("(")) {
            val bracketRanges = mutableListOf<IntRange>()
            var nbBracket = 0
            var start = 0

            for (i in line.indices) {
                if (line[i] == '(') {
                    if (nbBracket == 0) start = i
                    nbBracket++

                }
                if (line[i] == ')') {
                    nbBracket--
                    if (nbBracket == 0) bracketRanges.add(start..i)
                }
            }

            var newLine = line

            for (range in bracketRanges.reversed()) {
                val tempLine = newLine.substring(0 until range.first)
                val res = evaluate(newLine.substring(range.first + 1 until range.last), part2)
                newLine = tempLine + res + line.substring(range.last + 1)
            }

            return evaluate(newLine, part2)

        }

        if (part2 && line.contains("+")) {
            val lineSplit = line.split(" ").toMutableList()
            val i = lineSplit.indexOf("+")
            val newVal = lineSplit[i - 1].toLong() + lineSplit[i + 1].toLong()

            repeat(3) { lineSplit.removeAt(i - 1) }
            lineSplit.add(i - 1, newVal.toString())

            return evaluate(lineSplit.joinToString(" "), true)
        }

        val lineBlank = line.split(" ").toMutableList()
        var start = lineBlank.removeFirst().toLong()

        for (i in lineBlank.indices step 2) {
            if (lineBlank[i] == "+") start += lineBlank[i + 1].toLong()
            else start *= lineBlank[i + 1].toLong()
        }

        return start
    }

    override fun solve1(): Long = input.split("\n").sumOf { evaluate(it) }

    override fun solve2(): Long = input.split("\n").sumOf { evaluate(it, true) }
}

fun main() {
    val day = Day18(readFullText("_2020/d18/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day18(readFullText("_2020/d18/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}