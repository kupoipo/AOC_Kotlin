package _2023.d1

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureTimeMillis

class Day1(override val input: String) : Day<Int>(input) {

    private val numbers = mapOf(
        "on" to 1,
        "tw" to 2,
        "thre" to 3,
        "four" to 4,
        "fiv" to 5,
        "six" to 6,
        "seven" to 7,
        "eigh" to 8,
        "nin" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "0" to 0
    )

    override fun solve1() =
        input.split("\n").map { it.allInts() }.sumOf {
            (it.first().toString().first() + "" + it.last().toString().last()).toInt()
        }

    override fun solve2() = input.split("\n").map {
        Regex("""[0-9]|(on(?=e)|tw(?=o)|thre(?=e)|four|fiv(?=e)|six|seven|eigh(?=t)|nin(?=e)|zer(?=o))""").findAll(it)
    }.map { matches ->
        buildList {
            matches.forEach { m -> numbers[m.value]?.let { add(it) } }
        }
    }.sumOf { (it.first().toString() + "" + it.last().toString()).toInt() }
}

fun main() {
    //var day = Day1(readFullText("_2023/d1/test"))
    var day = Day1(readFullText("_2023/d1/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day1(readFullText("_2023/d1/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}