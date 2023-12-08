package _2015.d20

import util.Day
import util.readFullText
import kotlin.system.measureNanoTime
class Day20(override val input : String) : Day<Long>(input) {
    val total = 29000000 // 887040

    fun diviseurs(i : Int) : Int = (1..i/2).sumOf { if (i%it == 0) it else 0 }
    override fun solve1(): Long {
        (887040..887040).first { diviseurs(it) >= total/10 }.toLong()

        var dicho = 100_000
        var test = dicho

        while (dicho != 1) {
            test += dicho

            if (diviseurs(test) > total) {
                test -= dicho
                dicho /= 10
            }
        }

        return (test..test+10).first { diviseurs(it) > total }.toLong()
    }
    override fun solve2(): Long {

        return 0
    }
}

fun main() {
    val day = Day20(readFullText("_2015/d20/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day20(readFullText("_2015/d20/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}