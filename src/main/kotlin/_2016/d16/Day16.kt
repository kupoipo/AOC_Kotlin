package _2016.d16

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime

class Day16(override val input: String) : Day<String>(input) {
    var length: Int
    var origin: String

    init {
        input.split("\n").let { (length, origin) ->
            this.length = length.toInt()
            this.origin = origin
        }

        while (origin.length < length) {
            this.origin = process(origin)
        }

        this.origin = origin.take(length)
    }

    fun process(str: String): String {
        return str + "0" + str.reversed().map { if (it == '1') '0' else '1' }.joinToString("")
    }

    fun calculus(input: String) = buildString {
        input.windowed(2, 2) {
            append(if (it.first() == it.last()) "1" else 0)
        }
    }

    override fun solve1(): String {
        var res = origin

        while (res.length % 2 == 0) res = calculus(res)

        return res
    }

    override fun solve2(): String {
        return "-1"
    }
}

fun main() {
    val day = Day16(readFullText("_2016/d16/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day16(readFullText("_2016/d16/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}