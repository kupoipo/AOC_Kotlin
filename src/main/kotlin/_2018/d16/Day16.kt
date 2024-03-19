package _2018.d16

import _2018.register.Register2018
import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day16(override val input: String) : Day<Long>(input) {
    private val instructions: List<List<String>>
    private val test: List<List<String>>
    private val hmCodeToFunction = mutableMapOf<Int, (String, String, String) -> Unit>()
    private val hmFunctionToCode = mutableMapOf<(String, String, String) -> Unit, MutableSet<Int>>()
    private val register = Register2018()
    private val opcodes = listOf(
        register::addr,
        register::addi,
        register::mulr,
        register::muli,
        register::banr,
        register::bani,
        register::borr,
        register::bori,
        register::setr,
        register::seti,
        register::gtir,
        register::gtri,
        register::gtrr,
        register::eqir,
        register::eqri,
        register::eqrr
    )

    init {
        val data = input.split("\n\n\n\n")
        instructions = data.first().let { it + "\n" }.split("\n").chunked(4).map { it.dropLast(1) }
        test = data.last().split("\n").map { it.split(" ") }
    }
    private fun getPossibleInstructions(instruction: List<String>): List<(String, String, String) -> (Unit)> {
        val resOperation = mutableListOf<(String, String, String) -> (Unit)>()
        val after = mutableMapOf<String, Int>()
        instruction.last().allInts().forEachIndexed { i, v -> after[i.toString()] = v }

        for (operation in opcodes) {
            val opCode: Int
            instruction.first().allInts().forEachIndexed { i, v -> register[i.toString()] = v }
            instruction[1].split(" ").let { abcd ->
                opCode = abcd.first().toInt()
                operation(abcd[1], abcd[2], abcd[3])
            }

            if (after == register.register) {
                hmFunctionToCode.getOrPut(operation) { mutableSetOf() }.add(opCode)
                resOperation.add(operation)
            }
        }

        return resOperation
    }

    private fun assign() {
        repeat(16) {
            val found = hmFunctionToCode.filter { it.value.size == 1 }
            for (f in found) {
                val opCode = f.value.first()

                hmCodeToFunction[opCode] = f.key
                hmFunctionToCode.forEach { it.value.remove(opCode) }
            }
        }
    }

    override fun solve1(): Long {
        var nbSuccessThree = 0

        for (it in instructions) {
            if (it.isEmpty() || !it.first().contains("Before")) continue

            if (getPossibleInstructions(it).size >= 3) {
                nbSuccessThree++
            }
        }

        return nbSuccessThree.toLong()
    }


    override fun solve2(): Long {
        assign()

        register.clear()

        for (instruction in test) {
            val opCode = instruction.first().toInt()
            hmCodeToFunction[opCode]!!(instruction[1], instruction[2], instruction[3])
        }

        return register["0"].toLong()
    }
}

fun main() {
    val day = Day16(readFullText("_2018/d16/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day16(readFullText("_2018/d16/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}