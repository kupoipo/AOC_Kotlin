package _2021.d8

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis

val uniqueSize = listOf(2,3,4,7)



class Day8(override val input : String) : Day<Int>(input) {
    val lines = input.split("\n")

    val signal = lines.map { it.split("|")[0] }
    val output = lines.map { it.split("|")[1] }

    override fun solve1(): Int {
        return output.sumOf { it.split(" ").sumOf { if (it.length in uniqueSize) 1L else 0 } }.toInt()

    }
    override fun solve2(): Int {
        return output.map {
            val temp = buildString {
                it.split(" ").forEach {
                    when (it.length) {
                        2 -> append("1")
                        3 -> append("7")
                        4 -> append("4")
                        7 -> append("8")
                        else -> {
                            if (it.length == 5) {
                                if ("e" in it) {
                                    append("2")
                                } else {
                                    if ("b" in it) append("5")
                                    else append("3")
                                }
                            } else {
                                if ("d" in it && "c" in it) append("9")
                                else {
                                    if ("d" in it) append("6")
                                    else append("0")
                                }
                            }
                        }
                    }
                }
            }
            println(temp)

            temp.toInt()
        }.sum()
    }
}

fun main() {
    //var day = Day8(readFullText("_2021/d8/test"))
    var day = Day8(readFullText("_2021/d8/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}