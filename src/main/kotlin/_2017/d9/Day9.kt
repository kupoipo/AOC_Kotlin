package _2017.d9

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day9(override val input: String) : Day<Long>(input) {
    val list = parseGroup(0).second
    var count = 0L

    fun parseGroup(index: Int): Pair<Int, List<Any>> {
        val res = mutableListOf<Any>()
        var i = index
        var ignore = false

        while (i <= input.lastIndex) {
            when (input[i]) {
                '{' -> {
                    if (ignore) i++
                    else {
                        parseGroup(i + 1).let { (j, group) ->
                            res.add(group)
                            i = j
                        }
                    }
                }

                '}' -> {
                    if (ignore) i++
                    else return i + 1 to res
                }

                '!' -> {
                    i += 2
                    if (ignore) count--
                }

                '<' -> {
                    if (!ignore) count--
                    ignore = true

                    i++
                }

                '>' -> {
                    ignore = false
                    i++
                }

                else -> {
                    i++
                }
            }

            if (ignore)
                count++

        }

        return 0 to res
    }

    fun score(l: List<Any>, points: Int): Int {
        var total = points

        for (list in l) {
            total += score(list as List<Any>, points + 1)
        }

        return total
    }

    override fun solve1(): Long {
        return score(list, 0).toLong()
    }

    override fun solve2(): Long {
        return count
    }
}

fun main() {
    val day = Day9(readFullText("_2017/d9/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day9(readFullText("_2017/d9/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}