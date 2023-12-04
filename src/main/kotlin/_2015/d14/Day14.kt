package _2015.d14

import util.Day
import util.allInts
import util.readFullText
import kotlin.system.measureTimeMillis

class Duck(val speed: Int, val time_flying: Int, val rest: Int) {
    fun flyingDistance(time: Int): Int {
        val totalTimeFlying = time / (time_flying + rest) * time_flying
        val leftTimeFlying =
            if (time % (time_flying + rest) >= time_flying) time_flying else time % (time_flying + rest)

        return totalTimeFlying * speed + leftTimeFlying * speed
    }

    companion object {
        fun duckFromList(datas: List<Int>): Duck {
            return Duck(datas[0], datas[1], datas[2])
        }
    }
}

class Day14(override val input: String) : Day<Long>(input) {
    val ducks = input.split("\n").map { Duck.duckFromList(it.allInts()) }
    override fun solve1(): Long = ducks.maxOf { it.flyingDistance(2503) }.toLong()
    override fun solve2(): Long {
        val points = mutableMapOf<Int, Int>()

        ducks.indices.forEach {
            points[it] = 0
        }

        for (t in 1..2503) {
            val dist = ducks.indices.associateWith { ducks[it].flyingDistance(t) }

            dist.filter { it.value == dist.maxBy { it.value }.value }.forEach {
                points[it.key] = points[it.key]!! + 1
            }
        }

        println(points)

        return points.values.max().toLong()
    }
}

fun main() {
    //var day = Day14(readFullText("_2015/d14/test"))
    var day = Day14(readFullText("_2015/d14/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    var dayTest = Day14(readFullText("_2015/d14/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}