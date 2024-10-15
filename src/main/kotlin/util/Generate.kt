package util

import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.writeText
import kotlin.system.measureTimeMillis

fun main() {
    val startDay = 7
    val endDay = 25
    val year = 2019
    try {
        Files.createDirectory(Paths.get("./src/main/kotlin/_$year"))
    } catch (_: Exception) {

    }

    for (i in startDay..endDay) {
        Files.createDirectory(Paths.get("./src/main/kotlin/_$year/d$i"))
        Files.createFile(Paths.get("./src/main/kotlin/_$year/d$i/test1.txt"))
        Files.createFile(Paths.get("./src/main/kotlin/_$year/d$i/input.txt"))
        var f = Files.createFile(Paths.get("./src/main/kotlin/_$year/d$i/Day$i.kt"))

        var str =
            """
package _$year.d$i

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day$i(private val isTest: Boolean, override val input : String) : Day<Long>(input) {
    override fun solve1(): Long {
        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day$i(false, readFullText("_$year/d$i/input"))
    println("Temps partie 1 : """+"$"+"{measureNanoTime { println(\"Part 1 : \" + day.solve1()) } / 1e9}s\")"+"""
    println("Temps partie 2 : """+"$"+"{measureNanoTime { println(\"Part 2 : \" + day.solve2()) } / 1e9}s\")"+"""

    println()

    val dayTest = Day$i(true, readFullText("_$year/d$i/test"))
    println("Temps partie 1 : """+"$"+"{measureNanoTime { println(\"Part 1 : \" + dayTest.solve1()) } / 1e9}s\")"+"""
    println("Temps partie 2 : """+"$"+"{measureNanoTime { println(\"Part 2 : \" + dayTest.solve2()) } / 1e9}s\")"+"""
}"""

        f.writeText(str)
    }

}