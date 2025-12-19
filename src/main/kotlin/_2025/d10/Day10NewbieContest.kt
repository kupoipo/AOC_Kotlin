package _2025.d10

import util.*
import kotlin.system.measureNanoTime


class Day10NewbieContest(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val machinesP1 = mutableListOf<MachineP1>()

    init {
        input.split("\n").forEach { line ->
            val goal = line.findAllMatch("""\[(#|\.)+\]""").first().drop(1).dropLast(1)
            val buttons = line.findAllMatch("""\((\d+,|\d+)+\)""").map { it.allInts() }

            val goalToInt = goal.replace("#", "1").replace(".", "0").toInt(2)
            val buttonsToInt = buttons.map {
                val binary = goal.map { '0' }.toMutableList()
                it.forEach { i -> binary[i] = '1' }
                binary.joinToString("").toInt(2)
            }

            machinesP1.add(MachineP1(0, buttonsToInt, goalToInt))
        }
    }


    override fun solve1(): Long {
        println(machinesP1.first().solve())
        return -1
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    var day: Day10NewbieContest
    println("Temps construction : ${measureNanoTime { day = Day10NewbieContest(false, readFullText("_2025/d10/newbiecontest")) } / 1e9}s")
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")}