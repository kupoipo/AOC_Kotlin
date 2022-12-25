package _2022.d01

import util.Day
import java.io.File

class Day01(override val input : String) : Day<Int>(input) {
    override fun solve1(): Int = input.split("\r\n\r\n").
        map { it.split("\r\n").sumOf { it.toInt() } }.sortedDescending()[0]

    override fun solve2(): Int = input.split("\r\n\r\n")
        .map { it.split("\r\n").sumOf { it.toInt() } }.sortedDescending().take(3).sum()

}

fun main() {
    println(Day01(File("src/main/kotlin/_2022.d01/input.txt").readText()).solve2())
}