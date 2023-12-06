package _2015.d17

import util.Day
import util.readFullText
import kotlin.math.pow
import kotlin.math.sign
import kotlin.system.measureTimeMillis
class Day17(override val input : String) : Day<Long>(input) {
    val containers = input.split("\n").map { it.toInt() }
    val LITER = 150

    override fun solve1(): Long =
        (0 until 2.0.pow(containers.size).toInt()).count {
            Integer.toBinaryString(it).padStart(containers.size, '0').let { binaryInt ->
                return@count binaryInt.mapIndexed { i, binary -> containers[i] * binary.digitToInt() }.sum() == LITER
            }
        }.toLong()

    override fun solve2(): Long {
        val min = (0 until 2.0.pow(containers.size).toInt()).minOf {
            Integer.toBinaryString(it).padStart(containers.size, '0').let { binaryInt ->
                if (binaryInt.mapIndexed { i, binary -> containers[i] * binary.digitToInt() }.sum() == LITER) {
                    return@minOf binaryInt.count{ it == '1' }
                }
                return@minOf containers.size
            }
        }

        return   (0 until 2.0.pow(containers.size).toInt()).count {
            Integer.toBinaryString(it).padStart(containers.size, '0').let { binaryInt ->
                return@count binaryInt.mapIndexed { i, binary -> containers[i] * binary.digitToInt() }.sum() == LITER && binaryInt.count{ it == '1' } == min
            }
        }.toLong()
    }
}

fun main() {
    val day = Day17(readFullText("_2015/d17/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day17(readFullText("_2015/d17/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}