package _2016.d4

import util.Day
import util.allInts
import util.readFullText
import util.rotateCesar
import java.lang.Math.abs
import kotlin.system.measureNanoTime

typealias Room = Pair<String, String>

class Day4(override val input: String) : Day<Long>(input) {
    val instructions = input.split("\n").map { it.substring(0, it.length - 7) to it.takeLast(6).dropLast(1) }

    fun isRealRoom(r: Room): Boolean {
        val counts = r.first.groupingBy { it }.eachCount()

        val sortedLetters = counts.entries.sortedWith(compareBy({ -it.value }, { it.key }))

        val calculatedChecksum = sortedLetters.take(5).map { it.key }.joinToString("")

        return calculatedChecksum == r.second
    }

    override fun solve1(): Long = instructions.sumOf {
        val str = it.first.substring(0, it.first.lastIndexOf("-")).replace("-", "")

        if (isRealRoom(str to it.second)) {
            kotlin.math.abs(it.first.allInts().last())
        } else {
            0
        }
    }.toLong()

    override fun solve2(): Long {
        instructions.forEach {
            val id = kotlin.math.abs(it.first.allInts().first())
            val cesar = id % 26
            val str = it.first.rotateCesar(cesar)

            if (str.contains("northpole")) {
                return id.toLong()
            }
        }

        return 0L
    }
}

fun main() {
    val day = Day4(readFullText("_2016/d4/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day4(readFullText("_2016/d4/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}