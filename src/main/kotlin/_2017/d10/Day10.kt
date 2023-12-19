package _2017.d10

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class KnotHash {
    companion object {
        var skipSize = 0
        var index = 0
        fun runKnot(instruction: List<Int>, knots: MutableList<Long>) {
            instruction.forEach { subListLength ->
                val subList = buildList {
                    repeat(subListLength) { i ->
                        this.add(knots[(index + i) % 256])
                    }
                }.reversed()

                repeat(subListLength) { i ->
                    knots[(index + i) % 256] = subList[i]
                }

                index = (index + skipSize + subListLength) % 256
                skipSize++
            }
        }
        fun getKnotHashesFrom(str: String) : String {
            val knots = MutableList(256) { it.toLong() }

            skipSize = 0
            index = 0

            repeat(64) {
                runKnot((str.map { "${it.code}," }.joinToString("").trim() + "17,31,73,47,23").allInts(), knots)
            }

            val sparseHash = knots.windowed(16, 16).map { it.fold(0) { acc, n -> acc xor n.toInt() }  }

            return sparseHash.joinToString("") { Integer.toHexString(it).padStart(2, '0') }
        }
    }
}

class Day10(override val input : String) : Day<String>(input) {
    val knots = MutableList(256) { it.toLong() }
    override fun solve1(): String {
        KnotHash.runKnot(input.allInts(), knots)

        return (knots.first() * knots[1]).toString()
    }
    override fun solve2(): String = KnotHash.getKnotHashesFrom(input)
}


fun main() {

    val day = Day10(readFullText("_2017/d10/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day10(readFullText("_2017/d10/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}