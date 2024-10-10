package _2020.d25

import util.Day
import util.readFullText
import java.lang.Math.pow
import kotlin.system.measureNanoTime

class Day25(override val input: String) : Day<Long>(input) {
    private var doorPublicKey: Long = 0
    private var cardPublicKey: Long = 0

    init {
        input.split("\n").let {
            doorPublicKey = it.last().toLong()
            cardPublicKey = it.first().toLong()
        }
    }

    override fun solve1(): Long {
        var value = 1L
        var loopSize = 0L

        while (value != cardPublicKey) {
            value = (value * 7) % 20201227
            loopSize++
        }

        value = 1L
        repeat(loopSize.toInt()) {
            value = (value*doorPublicKey)%20201227
        }

        return value
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day25(readFullText("_2020/d25/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day25(readFullText("_2020/d25/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}