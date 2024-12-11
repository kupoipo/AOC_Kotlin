package _2024.d11

import util.Day
import util.readFullText
import java.math.BigInteger
import kotlin.system.measureNanoTime

class Day11(private val isTest: Boolean, override val input: String) : Day<BigInteger>(input) {
    private var stones = input.split(" ").map { BigInteger.valueOf(it.toLong()) }
    private var cache = mutableMapOf<Pair<BigInteger, Int>, BigInteger>()

    private fun solve(stone: BigInteger, blink: Int): BigInteger {
        if (blink == 0) return BigInteger.ONE
        return cache.getOrPut(stone to blink) {
            if (stone == BigInteger.ZERO) solve(BigInteger.ONE, blink - 1)
            else {
                val length = stone.toString().length
                if (length % 2 == 0) {
                    solve(BigInteger.valueOf(stone.toString().take(length / 2).toLong()), blink - 1) +
                            solve(BigInteger.valueOf(stone.toString().drop(length / 2).toLong()), blink - 1)
                } else {
                    solve(stone.times(BigInteger.valueOf(2024)), blink - 1)
                }
            }
        }
    }

    override fun solve1(): BigInteger = stones.map { solve(it, 25) }.sumOf { it }
    override fun solve2(): BigInteger = stones.map { solve(it, 75) }.sumOf { it }
}

fun main() {
    val day = Day11(false, readFullText("_2024/d11/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day11(true, readFullText("_2024/d11/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}