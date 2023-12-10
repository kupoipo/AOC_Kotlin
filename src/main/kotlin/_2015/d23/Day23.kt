package _2015.d23

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day23(override val input: String) : Day<Long>(input) {
    val instructions = input.split("\n")

    fun run(start: Int) : Long {
        var a = start
        var b = 0L
        var index = 0

        while (index < instructions.size) {
            val instruction = instructions[index]

            if (instruction.contains("hlf")) {
                a /= 2
                index++
            }

            if (instruction.contains("tpl")) {
                a *= 3
                index++
            }

            if (instruction.contains("inc")) {
                if (instruction.contains("a")) {
                    a++
                } else {
                    b++
                }
                index++
            }

            if (instruction.contains("jmp")) {
                index += instruction.allInts().first()
            }

            if (instruction.contains("jie")) {
                index += if (a%2 == 0) {
                    instruction.allInts().first()
                } else {
                    1
                }
            }

            if (instruction.contains("jio")) {
                index += if (a == 1) {
                    instruction.allInts().first()
                } else {
                    1
                }
            }
        }

        return b
    }

    override fun solve1(): Long = run(0)

    override fun solve2(): Long = run(1)
}

fun main() {
    val day = Day23(readFullText("_2015/d23/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day23(readFullText("_2015/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}