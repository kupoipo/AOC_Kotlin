package _2016.d21

import util.*
import kotlin.system.measureNanoTime

class Day21(override val input: String) : Day<String>(input) {
    var word = "abcdefgh".toMutableList()
    val instructions = input.split("\n")

    fun apply(instruction: String) {
        if (instruction.contains("swap position")) {
            instruction.allInts().let { (i1, i2) -> word.swap(i1, i2) }
        }

        if (instruction.contains("swap letter")) {
            instruction.findAllMatch(""" \w( |$)""").let { (l1, l2) ->
                word.swap(word.indexOf(l1.trim().first()), word.indexOf(l2.trim().first()))
            }
        }

        if (instruction.matches(Regex("""rotate (right|left) \d \w+"""))) {
            val i = instruction.firstInt()

            word = if (instruction.contains("right")) {
                val before = word.takeLast(i)
                val after = word.dropLast(i)
                (before + after).toMutableList()
            } else
                (word.drop(i) + word.take(i)).toMutableList()
        }

        if (instruction.contains("rotate based")) {
            val letter = instruction.last()
            val position = word.indexOf(letter)
            val rotation = (position + 1 + if (position > 3) 1 else 0) % word.size

            word = (word.takeLast(rotation) + word.dropLast(rotation)).toMutableList()
        }

        if (instruction.contains("reverse")) {
            instruction.allInts().let { (i1, i2) ->
                val before = word.take(i1)
                val reverse = word.drop(i1).dropLast(word.size - i2 - 1).reversed()
                val after = word.takeLast(word.size - i2 - 1)

                word = (before + reverse + after).toMutableList()
            }
        }

        if (instruction.contains("move position")) {
            instruction.allInts().let { (i1, i2) ->
                val l = word.removeAt(i1)
                word.add(i2, l)
            }
        }
    }

    fun solve(wordToTest: String): String {
        word = wordToTest.toMutableList()
        for (i in instructions) {
            apply(i)
        }
        return word.joinToString("")
    }

    override fun solve1(): String = solve("abcdefgh")
    override fun solve2(): String {
        "abcdefgh".toMutableList().permutation().forEach {
            if (solve(it.joinToString("")) == "fbgdceah")
                return it.joinToString("")
        }

        return "No solution found"
    }
}

fun main() {
    val day = Day21(readFullText("_2016/d21/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day21(readFullText("_2016/d21/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}