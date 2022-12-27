package _2021.d14

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

class Rule(val rule : String, val add : Char) {
    var nb = 0L
    var nbNextRound = 0L

    fun nextRules() : List<String> {
        return listOf(rule[0].toString() + add, add.toString() + rule[1])
    }
}

class Day14(override val input : String) : Day<Long>(input) {
    private val lines = input.split("\n")
    var rules = mutableMapOf<String, Rule>()
    var apparition = mutableMapOf<Char, Long>()
    var letters : String = ""
    val sentence : String = lines[0]

    init {
        lines.drop(2).forEach {
            it.split(" -> ").let { rules[it[0]] = Rule(it[0], it[1][0]) }
        }

        for (i in 0 until sentence.length - 1) {
            val key = sentence.substring(i, i+2)
                rules[key]!!.nb += 1
        }

        rules.keys.forEach { it.forEach { c -> if (c !in letters) letters += c } }
    }

    fun round(times : Int) {

        repeat (times) {
            rules.forEach { (name, rule) ->
                rule.nextRules().forEach {
                    rules[it]!!.nbNextRound += rule.nb
                }

                rules[name]!!.nb = 0
            }

            rules.forEach {
                it.value.nb = it.value.nbNextRound
                it.value.nbNextRound = 0
            }
        }

        updateLetters()
    }

    private fun updateLetters() {
        apparition = mutableMapOf()
        letters.forEach { apparition[it] = count(it) }
        apparition[sentence.last()] = apparition[sentence.last()]!! + 1
    }

    private fun count(c: Char) : Long {
        return rules.filter { it.value.rule[0] == c }.map { it.value.nb }.sum()
    }

    override fun solve1(): Long {
        round(10)
        return apparition.maxOf { it.value }.toLong() - apparition.minOf { it.value }.toLong()
    }

    override fun solve2(): Long {
        round(30)
        return apparition.maxOf { it.value }.toLong() - apparition.minOf { it.value }.toLong()
    }
}

fun main() {
    //var day = Day14(readFullText("_2021/d14/test"))
    var day = Day14(readFullText("_2021/d14/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}