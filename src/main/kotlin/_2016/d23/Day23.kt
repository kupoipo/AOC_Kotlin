package _2016.d23

import util.Day
import util.firstInt
import util.isInt
import util.readFullText
import java.lang.Exception
import kotlin.system.measureNanoTime

class Day23(override val input: String) : Day<Long>(input) {
    private val register = "abcd".associateWith { 0 }.toMutableMap()
    private val instructions = input.split("\n").toMutableList()

    private fun run() {
        var index = 0
        var nbRun = 0L
        while (index < instructions.size) {
            val instruction = instructions[index]
            instruction.split(" ").also {
                when (it[0]) {
                    "cpy" -> {
                        if (!it[2].isInt()) {
                            if (it[1].isInt()) register[it[2].first()] = it[1].toInt()
                            else register[it[2].first()] = register[it[1].first()]!!
                        }
                        index++
                    }

                    "jnz" -> {
                        val value = if (it[1].isInt()) it[1].toInt() else register[it[1].first()]!!
                        if (value != 0) {
                            index += if (it[2].isInt()) {
                                it[2].toInt()
                            } else {
                                register[it[2].first()]!!
                            }
                        } else {
                            index++
                        }
                    }

                    "dec" -> {
                        register[it[1].first()] = register[it[1].first()]!! - 1
                        index++
                    }

                    "inc" -> {
                        register[it[1].first()] = register[it[1].first()]!! + 1
                        index++
                    }

                    "tgl" -> {
                        val indexTargetInstruction = index + if (it[1].isInt()) it[1].toInt() else register[it[1].first()]!!

                        index++
                        if (indexTargetInstruction < instructions.size) {
                            val targetInstruction = instructions[indexTargetInstruction]

                            targetInstruction.split(" ").let { params ->
                                when (params.size) {
                                    2 -> {
                                        if (params.first() == "inc") {
                                            instructions[indexTargetInstruction] = "dec ${params.last()}"
                                        } else {
                                            instructions[indexTargetInstruction] = "inc ${params.last()}"
                                        }
                                    }

                                    3 -> {
                                        if (params.first() == "jnz") {
                                            instructions[indexTargetInstruction] = "cpy ${params[1]} ${params.last()}"
                                        } else {
                                            instructions[indexTargetInstruction] = "jnz ${params[1]} ${params.last()}"
                                        }
                                    }
                                }
                            }
                        }

                    }

                    else -> throw Exception("Instruction ${it[0]} unknown.")
                }
            }
            nbRun++
        }

        println("$nbRun operation were necessary to exit the program.")
    }

    override fun solve1(): Long {
        register['a'] = 12
        run()
        return register['a']!!.toLong()
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day23(readFullText("_2016/d23/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day23(readFullText("_2016/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}