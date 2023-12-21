package _2023.d19

import _2017.register.Register
import _2022.d20.li
import util.Day
import util.allInts
import util.allLong
import util.readFullText
import kotlin.system.measureNanoTime

val reg = Register(listOf())

class Condition(val left: String, val operand: String, val right: String, val goal: String) {
    override fun toString(): String {
        return "$left$operand$right : $goal"
    }
}

class Rule(val final: String) {
    val conditions = mutableListOf<Condition>()

    fun next(): String {
        for (condition in conditions) {
            if (reg.compareTo(condition.left, condition.right, condition.operand)) {
                return condition.goal
            }
        }

        return final
    }

    override fun toString(): String {
        return "$conditions ->$final"
    }
}

class Day19(override val input: String) : Day<Long>(input) {
    private val rules = mutableMapOf<String, Rule>()
    private val ratings = mutableListOf<String>()

    init {
        input.split("\n\n").let { (ru, ra) ->
            ratings.addAll(ra.split("\n"))

            ru.split("\n").forEach { it ->
                val ruleName = it.take(it.indexOf("{"))
                val line = it.drop(it.indexOf("{") + 1).dropLast(1)
                val conditions = line.split(",")

                rules[ruleName] = Rule(conditions.last())

                conditions.dropLast(1).forEach { c ->
                    rules[ruleName]!!.conditions.add(
                        Condition(
                            c.first().toString(),
                            c[1].toString(),
                            c.substring(2, c.indexOf(":")),
                            c.drop(c.indexOf(":") + 1)
                        )
                    )
                }
            }
        }
    }

    private fun acceptRating(line: String): Boolean {
        line.allLong().let { (x, m, a, s) ->
            reg.register["x"] = x
            reg.register["m"] = m
            reg.register["a"] = a
            reg.register["s"] = s
        }

        var instruction = "in"

        while (instruction !in listOf("A", "R")) {
            instruction = rules[instruction]!!.next()
        }

        return instruction == "A"
    }

    override fun solve1(): Long = ratings.filter { acceptRating(it) }.sumOf { it.allLong().sum() }

    val accepted =
        mutableMapOf("x" to 1..4000L, "m" to 1..4000L, "a" to 1..4000L, "s" to 1..4000L)

    fun getAccepted(rule: String, currentAccepted: Map<String, LongRange>): Long {
        if (rule == "R") return 0
        if (rule == "A") return currentAccepted.values.fold(1L) { acc, r -> (r.last - r.first + 1) * acc }.toLong()

        val currentAccepted = currentAccepted.toMutableMap()
        var total = 0L

        rules[rule]!!.conditions.forEach { c ->
            val newAccepted = currentAccepted.toMutableMap()

            if (c.right.toInt() in currentAccepted[c.left]!!) {
                if (c.operand == ">") {
                    newAccepted[c.left] = c.right.toInt() + 1..currentAccepted[c.left]!!.last
                    currentAccepted[c.left] = currentAccepted[c.left]!!.first..c.right.toInt()
                } else {
                    newAccepted[c.left] = currentAccepted[c.left]!!.first until c.right.toInt()
                    currentAccepted[c.left] = c.right.toInt()..currentAccepted[c.left]!!.last
                }
            }

            total += getAccepted(c.goal, newAccepted)
        }

        total += getAccepted(rules[rule]!!.final, currentAccepted)

        return total
    }

    override fun solve2(): Long {
        return getAccepted("in", accepted)
    }
}

fun main() {
    val day = Day19(readFullText("_2023/d19/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day19(readFullText("_2023/d19/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}