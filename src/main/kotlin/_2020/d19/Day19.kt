package _2020.d19

import util.Day
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime

class Day19(override val input: String) : Day<Long>(input) {
    private val hmRules = mutableMapOf<Int, List<String>>()
    private val data = input.split("\n\n")
    private val rules = data.first().split("\n").associate { it.firstInt() to it.substring(it.indexOf(" ") + 1) }
    private val values = data.last().split("\n")
    private val possibilities = buildSet {
        rules.keys.forEach {
            addAll(stringsFrom(it))
        }
    }

    init {
        println(stringsFrom(0))
    }

    private fun stringsFrom(value: Int): List<String> {
        if (hmRules.containsKey(value)) return hmRules[value]!!
        if (rules[value]!!.contains("\"")) {
            hmRules[value] = listOf(rules[value]!!.replace("\"", ""))
            return hmRules[value]!!
        }

        val possibilities = rules[value]!!.split("|").map { it.trim() }

        val res = mutableListOf<String>()
        for (possibility in possibilities) {
            var tempRes = mutableListOf("")
            for (i in possibility.split(" ").map(String::toInt)) {
                val temp = mutableListOf<String>()
                for (s in stringsFrom(i)) {
                    for (s2 in tempRes) {
                        temp.add(s2 + s)
                    }
                }
                tempRes = temp
            }
            res.addAll(tempRes)
        }

        hmRules[value] = res
        return res
    }

    override fun solve1(): Long {
        val z = stringsFrom(0)
        return values.count { z.contains(it) }.toLong()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day19(readFullText("_2020/d19/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day19(readFullText("_2020/d19/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}