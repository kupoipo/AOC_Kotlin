package _2015.d16

import util.Day
import util.listOfMatch
import util.readFullText
import kotlin.system.measureTimeMillis

class Day16(override val input: String) : Day<Long>(input) {
    val SUE = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    override fun solve1(): Long {
        val line = input.split("\n").first {
            Regex("""\w+: \d+""").listOfMatch(it).all { sueCarac ->
                sueCarac.split(":").let { (carac, nb) ->
                    return@all SUE[carac] == nb.trim().toInt()
                }
            }
        }

        return line.substring(4, line.indexOf(":")).toLong()
    }

    override fun solve2(): Long {
        val line = input.split("\n").first {
            Regex("""\w+: \d+""").listOfMatch(it).all { sueCarac ->
                sueCarac.split(":").let { (carac, nb) ->
                    when (carac) {
                        "cats", "tree" -> return@all SUE[carac]!! < nb.trim().toInt()
                        "pomeranians", "goldfish" -> return@all SUE[carac]!! > nb.trim().toInt()
                        else -> {
                            return@all SUE[carac]!! == nb.trim().toInt()
                        }
                    }
                }
            }
        }
        return line.substring(4, line.indexOf(":")).toLong()
    }
}

fun main() {
    val day = Day16(readFullText("_2015/d16/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day16(readFullText("_2015/d16/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}