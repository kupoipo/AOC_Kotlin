package _2019.d16

import util.Day
import util.readFullText
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day16(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    private val pattern = listOf(0, 1, 0, -1)

    private fun transform(from: List<Int>, offset: Int): Int {
        val coeff = mutableListOf<Int>()

        var i = 0
        while (i < from.size + 1) {
            for (p in pattern) {
                for (j in 0 until offset) {
                    coeff.add(p)
                    i++
                    if (i == from.size + 1) break
                }
                if (i == from.size + 1) break
            }
        }

        coeff.removeFirst()

        return abs(coeff.mapIndexed { index, c -> from[index] * c }.sum())
    }

    private fun nextGen(from: List<Int>) = List(from.size) { index -> transform(from, index+1) % 10 }

    private fun getGeneration(gen: Int): List<Int> {
        var res = input.map { it.digitToInt() }
        for (i in 1..gen) {
            res = nextGen(res)
        }
        return res
    }

    override fun solve1(): Long = getGeneration(10).joinToString("").take(8).toLong()

    override fun solve2(): Long {
        val offset = input.take(7).toLong()
        val modulo = abs((input.length * 10_000 - offset) % input.length)
        val quotient = abs((input.length * 10_000 - offset) / input.length)
        val res = input.takeLast(modulo.toInt()).map { it.digitToInt() }.toMutableList()
        repeat(quotient.toInt()) {
            res.addAll(input.map { it.digitToInt() })
        }

        repeat(100) {
            var sum = res.sum()

            for (i in res.indices) {
                val temp = abs(sum) % 10
                sum -= res[i]
                res[i] = temp
            }
        }

        return res.take(8).joinToString("").toLong()
    }
}

fun main() {
    val day = Day16(false, readFullText("_2019/d16/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day16(true, readFullText("_2019/d16/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}