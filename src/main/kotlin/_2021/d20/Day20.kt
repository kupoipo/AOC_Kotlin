package _2021.d20

import util.Day
import util.readFullText
import kotlin.system.measureTimeMillis
class Day20(override val input : String) : Day<Long>(input) {
    val pointures = input.split("\n").map { it.split(" ") }

    override fun solve1(): Long {
        pointures[0].indices.forEach {
            println("INSERT INTO SHOE_SIZE (SIZE_CM, SIZE_FR, SIZE_UK, SIZE_US, SIZE_IT) VALUES (${pointures[0][it].replace(",", ".")},${pointures[1][it].replace(",", ".")},${pointures[2][it].replace(",", ".")},${pointures[3][it].replace(",", ".")},${pointures[4][it].replace(",", ".")});")
        }
        return 0L
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day20(readFullText("_2021/d20/test"))
    var day = Day20(readFullText("_2021/d20/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}