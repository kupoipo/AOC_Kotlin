package _2020.d8

import util.Day
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime

class Day8(override val input: String) : Day<Long>(input) {
    private val instructions = input.split("\n").map { it.substring(0 until it.indexOf(" ")).trim() to it.firstInt() }.toMutableList()

    fun simulate() : Long {
        var acc = 0L
        var index = 0
        val instructionsSeen = mutableSetOf<Int>()

        while (index < instructions.size) {
            val instr = instructions[index]

            if (index in instructionsSeen)
                return acc

            instructionsSeen.add(index)

            when (instr.first) {
                "nop" -> index++
                "acc" -> {
                    acc += instr.second
                    index++
                }
                else -> index += instr.second
            }
        }

        println(acc)
        return -1
    }
    override fun solve1(): Long = simulate()

    fun swap(index : Int) {
        if (instructions[index].first == "nop") {
            instructions[index] = "jmp" to instructions[index].second
        } else {
            instructions[index] = "nop" to instructions[index].second
        }
    }

    override fun solve2(): Long {
        var indexSwap = 0

        while (simulate() != -1L) {
            if (indexSwap != 0) {
                swap(indexSwap-1)
            }
            while (instructions[indexSwap].first == "acc") {
                indexSwap++
            }
            swap(indexSwap)
            indexSwap++
        }

        return 0
    }
}

fun main() {
    val day = Day8(readFullText("_2020/d8/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day8(readFullText("_2020/d8/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}