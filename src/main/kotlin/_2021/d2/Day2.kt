package _2021.d2

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day2(override val input : String) : Day<Int>(input) {
    var depth = 0
    var horizontal = 0
    var aim = 0

    override fun solve1(): Int {
        input.split("\n").forEach {
            var instructions = it.split(" ")
            when (instructions[0]) {
                "down" -> depth += instructions[1].toInt()
                "up" -> depth -= instructions[1].toInt()
                else -> {
                    horizontal += instructions[1].toInt()
                }
            }
        }

        return depth * horizontal
    }
    override fun solve2(): Int {
        depth = 0; horizontal = 0

        input.split("\n").forEach {
            var instructions = it.split(" ")
            when (instructions[0]) {
                "down" -> aim += instructions[1].toInt()
                "up" -> aim -= instructions[1].toInt()
                else -> {
                    horizontal += instructions[1].toInt()
                    depth += aim * instructions[1].toInt()
                }
            }
        }

        return horizontal * depth
    }
}

fun main() {
    //var day = Day1(readFullText("_2021.d2/test"))
    var day = Day2(readFullText("_2021/d2/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 1 : " + day.solve2()) }
    println("Temps partie 1 : {$t2}")
}