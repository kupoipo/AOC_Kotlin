package _2019.d5

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day5(override val input : String) : Day<Long>(input) {
    private val tab = input.split(",").map { s -> s.toInt() }.toMutableList()

    override fun solve1(): Long {
        var i = 0
        val input = 0

        while (i < tab.size) {
            val instruction = String.format("%05d", tab[i])

            when (instruction.takeLast(2)) {
                "03" -> {
                    tab[tab[i+1]] = input
                    i += 2
                }

                "04" -> {

                    return (tab[tab[i+1]]).toLong()
                }

                "99" -> {
                    println()
                    println("break")
                    break
                }

                else -> {

                    val param1 = if ( instruction[2] == '0' ) tab[tab[i+1]] else tab[i+1]
                    val param2 = if ( instruction[1] == '0' ) tab[tab[i+2]] else tab[i+2]


                    when (instruction.last()) {
                        '1' -> {
                            tab[tab[i + 3]] = param1 + param2
                            i+=4
                        }

                        '2' -> {
                            tab[tab[i + 3]] = param1 * param2
                            i+=4
                        }

                        '5' -> {
                            if (param1 != 0) {
                                i = param2
                            } else {
                                i += 3
                            }
                        }

                        '6' -> {
                            if (param1 == 0) {
                                i = param2
                            } else {
                                i += 3
                            }
                        }

                        '7' -> {
                            tab[tab[i + 3]] = if (param1 < param2) 1 else 0
                            i += 4
                        }

                        '8' -> {
                            tab[tab[i + 3]] = if (param1 == param2) 1 else 0
                            i += 4
                        }

                    }
                }
            }

        }

        return 0
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day5(readFullText("_2019/d5/test"))
    var day = Day5(readFullText("_2019/d5/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day5(readFullText("_2019/d5/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}