package _2024.d17

import util.*
import kotlin.math.pow
import kotlin.system.measureNanoTime

const val registerA = 0
const val registerB = 1
const val registerC = 2

class Day17(private val isTest: Boolean, override val input: String) : Day<String>(input) {
    private val registers =
        input.findAllMatch("""Register \w: \d+""").map(String::allLong).map { it.first() }.toMutableList()
    private val codes = input.allInts().drop(3)
    private var currentInstruction = 0
    private var builder = StringBuilder()
    private fun runInstruction(opCode: Int, literalOperand: Int) {
        val comboOperand = if (literalOperand in 0..3) literalOperand.toLong() else registers[literalOperand - 4]
        when (opCode) {
            0 -> {
                registers[registerA] /= (2.0.pow(comboOperand.toDouble())).toLong()
                currentInstruction += 2
            }

            1 -> {
                registers[registerB] = registers[registerB] xor literalOperand.toLong()
                currentInstruction += 2
            }

            2 -> {
                registers[registerB] = comboOperand % 8
                currentInstruction += 2
            }

            3 -> {
                if (registers[registerA] == 0L) {
                    currentInstruction += 2
                } else {
                    currentInstruction = literalOperand
                }
            }

            4 -> {
                registers[registerB] = registers[registerB] xor registers[registerC]
                currentInstruction += 2

            }

            5 -> {
                builder.append("${comboOperand % 8L},")
                currentInstruction += 2
            }

            6 -> {
                registers[registerB] = registers[registerA] / (2.0.pow(comboOperand.toDouble())).toLong()
                currentInstruction += 2
            }

            7 -> {
                registers[registerC] = registers[registerA] / (2.0.pow(comboOperand.toDouble())).toLong()
                currentInstruction += 2
            }

            else -> {
                throw RuntimeException("Opcode $opCode is invalid")
            }
        }
    }

    private fun runProgram(a: Long = -1): String {
        if (a != -1L) {
            registers[registerA] = a
        }
        currentInstruction = 0
        builder = StringBuilder()
        while (currentInstruction in 0 until codes.lastIndex) {
            runInstruction(codes[currentInstruction], codes[currentInstruction + 1])
        }
        return builder.toString().dropLast(1)
    }

    override fun solve1(): String {
        return runProgram()
    }

    override fun solve2(): String {
        var a = 0L
        for (i in (0..codes.lastIndex).reversed()) {
            a = a shl 3
            println("before $a")
            while(runProgram(a) != codes.drop(i).joinToString(",")) {
                a += 1
            }
            println(runProgram(a))
        }
        return a.toString()
    }
}

fun main() {
    println(1 shl 3)
    val day = Day17(false, readFullText("_2024/d17/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day17(true, readFullText("_2024/d17/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

    for (i in 0..7) {
        print(i xor 5)
    }

}