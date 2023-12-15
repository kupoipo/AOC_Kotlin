package _2023.d15

import util.Day
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime
class Day15(override val input : String) : Day<Long>(input) {
    val map = mutableMapOf<String, Int>()
    var box = MutableList(256) { MutableList(0) { "" to 0} }
    fun strToHash(str: String): Long = str.fold(0) { acc, c -> ( (acc + c.code) * 17 ) % 256 }.toLong()
    override fun solve1(): Long = input.split(",").sumOf { strToHash(it) }
    override fun solve2(): Long {
        input.split(",").forEach { str ->
                val key = str.substring(0, str.indexOfFirst { it in "=-" })
                val valueMap = str.firstInt()
                val numBox = strToHash(key).toInt()

                if (valueMap == -1) {
                    box[numBox].removeIf { pair -> pair.first == key}
                } else {
                    val i = box[numBox].indices.firstOrNull { box[numBox][it].first == key}

                    box[numBox].apply {
                        if (i == null) this.add(key to valueMap)
                        else this[i] = key to valueMap
                    }
                }
        }

        return box.indices.sumOf { i -> box[i].indices.sumOf { j -> (i+1) * (j+1) * box[i][j].second } }.toLong()
    }
}

fun main() {
    val day = Day15(readFullText("_2023/d15/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day15(readFullText("_2023/d15/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}