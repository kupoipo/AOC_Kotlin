package _2016.d9

import util.*
import kotlin.system.measureNanoTime
class Day9(override val input : String) : Day<Long>(input) {
    fun nextString(str: String) : String {
        var generate = ""
        var index = 0
        var match = Regex("""\(\d+x\d+\)""").find(str, index)

        while (match != null) {
            val length = match.value.firstInt()
            val repeat = match.value.lastInt()

            if (match.range.first > index) {
                generate += str.substring(index, match.range.first)
            }

            generate += str.substring(match.range.last + 1 .. match.range.last + length) * repeat

            index = match.range.last + length + 1
            match = Regex("""\(\d+x\d+\)""").find(str, index)
        }

        generate += str.substring(index)

        return generate
    }

    fun weightOfString(str: String) : Long {
        val weights = MutableList(input.length) { 1L }
        var total = 0L
        var index = 0

        var match = Regex("""\(\d+x\d+\)""").find(str, index)

        while (match != null) {
            val length = match.value.firstInt()
            val repeat = match.value.lastInt()

            if (match.range.first > index) {
                for (i in index until match.range.first) {
                    total += weights[i]
                }
            }

            for (i in match.range.last .. match.range.last + length) {
                weights[i] = weights[i] * repeat
            }

            index = match.range.last + 1

            match = Regex("""\(\d+x\d+\)""").find(str, index)
        }



        for (i in index until weights.size) {

            total += weights[i]
        }

        return total
    }

    override fun solve1(): Long = nextString(input).length.toLong()

    override fun solve2(): Long = weightOfString(input)
}

fun main() {
    val day = Day9(readFullText("_2016/d9/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day9(readFullText("_2016/d9/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}