package _2017.d16

import util.*
import kotlin.system.measureNanoTime
class Day16(override val input : String) : Day<String>(input) {
    var word = "abcdefghijklmnop".toMutableList()
    val instructions = input.split(",")
    val listWord = mutableListOf<String>()

    fun apply(instruction: String) {
        if (instruction.matches(Regex("""x\d+/\d+"""))) {
            instruction.allInts().let { (i1, i2) -> word.swap(i1, i2) }
        }

        if (instruction.matches(Regex("""p\w/\w"""))) {
            val l1 = instruction[1]
            val l2 = instruction.last()
            word.swap(word.indexOf(l1), word.indexOf(l2))
        }

        if (instruction.matches(Regex("""s\d+"""))) {
            val i = instruction.firstInt()
            val before = word.takeLast(i)
            val after = word.dropLast(i)
            word = (before + after).toMutableList()

        }
    }

    fun solve(wordToTest: String): String {
        word = wordToTest.toMutableList()
        for (i in instructions) {
            apply(i)
        }
        return word.joinToString("")
    }

    override fun solve1(): String {
        return solve("abcdefghijklmnop")
    }
    override fun solve2(): String {
        var i = 1
        var currentWord = word.joinToString("")
        listWord.add(currentWord)

        var max = 1_000_000_000

        while (i < max) {
            currentWord = solve(currentWord)

            if (currentWord in listWord) {
                val j = listWord.size - listWord.indexOfFirst { it == currentWord }

                i %= j
                max %= j
            }
            listWord.add(currentWord)
            i++
        }

        return currentWord
    }
}

fun main() {
    val day = Day16(readFullText("_2017/d16/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day16(readFullText("_2017/d16/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}