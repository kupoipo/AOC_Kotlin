package _2018.d16

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureNanoTime

class Day16(override val input: String) : Day<Long>(input) {
    private val instructions: List<List<String>>
    private val test: List<List<String>>
    private val register = mutableMapOf<String, Int>()
    private val hmCodeToFunction = mutableMapOf<Int, (String, String, String) -> Unit>()
    private val hmFunctionToCode = mutableMapOf<(String, String, String) -> Unit, MutableSet<Int>>()
    private val opcodes = listOf(
        ::addr,
        ::addi,
        ::mulr,
        ::muli,
        ::banr,
        ::bani,
        ::borr,
        ::bori,
        ::setr,
        ::seti,
        ::gtir,
        ::gtri,
        ::gtrr,
        ::eqir,
        ::eqri,
        ::eqrr
    )

    init {
        val data = input.split("\n\n\n\n")
        instructions = data.first().let { it + "\n" }.split("\n").chunked(4).map { it.dropLast(1) }
        test = data.last().split("\n").map { it.split(" ") }
    }

    private fun addr(a: String, b: String, c: String) {
        register[c] = get(a) + get(b)
    }

    private fun addi(a: String, b: String, c: String) {
        register[c] = get(a) + b.toInt()
    }

    private fun mulr(a: String, b: String, c: String) {
        register[c] = get(a) * get(b)
    }

    private fun muli(a: String, b: String, c: String) {
        register[c] = get(a) * b.toInt()
    }

    private fun banr(a: String, b: String, c: String) {
        register[c] = get(a) and get(b)
    }

    private fun bani(a: String, b: String, c: String) {
        register[c] = get(a) and b.toInt()
    }

    private fun borr(a: String, b: String, c: String) {
        register[c] = get(a) or get(b)
    }

    private fun bori(a: String, b: String, c: String) {
        register[c] = get(a) or b.toInt()
    }

    private fun setr(a: String, b: String, c: String) {
        register[c] = get(a)
    }

    private fun seti(a: String, b: String, c: String) {
        register[c] = a.toInt()
    }

    private fun gtir(a: String, b: String, c: String) {
        register[c] = if (a.toInt() > get(b)) 1 else 0
    }

    private fun gtri(a: String, b: String, c: String) {
        register[c] = if (get(a) > b.toInt()) 1 else 0
    }

    private fun gtrr(a: String, b: String, c: String) {
        register[c] = if (get(a) > get(b)) 1 else 0
    }

    private fun eqir(a: String, b: String, c: String) {
        register[c] = if (a.toInt() == get(b)) 1 else 0
    }

    private fun eqri(a: String, b: String, c: String) {
        register[c] = if (get(a) == b.toInt()) 1 else 0
    }

    private fun eqrr(a: String, b: String, c: String) {
        register[c] = if (get(a) == get(b)) 1 else 0
    }

    fun get(x: String) = register.getOrPut(x) { 0 }

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

            if (after == register) {
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

        return register["0"]!!.toLong()
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