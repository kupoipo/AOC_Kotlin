package _2023.d6

import util.Day
import util.allLong
import util.readFullText
import kotlin.system.measureTimeMillis

class Course(val time: Long, val distance: Long) {
    fun beatRecord(t: Long): Boolean {
        return t * (time - t) > distance
    }

    fun nbBeatRecord(): Long {
        return time - (2 * (0L..time).first { beatRecord(it) }.toLong()) + 1
    }
}

class Day6(override val input: String) : Day<Long>(input) {
    val courses = mutableListOf<Course>()

    init {
        input.split("\n").map {
            it.allLong()
        }.let { (t, d) ->
            t.zip(d).forEach { (time, distance) ->
                courses.add(Course(time, distance))
            }
        }
    }

    override fun solve1(): Long {
        return courses.map { it.nbBeatRecord() }.reduce { i, j -> i * j }.toLong()
    }

    override fun solve2(): Long {
        return courses.fold(Course(0, 0)) { acc, course ->
            Course(
                "${acc.time}${course.time}".toLong(),
                "${acc.distance}${course.distance}".toLong()
            )
        }.nbBeatRecord()
    }
}

fun main() {
    val day = Day6(readFullText("_2023/d6/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")

    println()
    println()

    val dayTest = Day6(readFullText("_2023/d6/test"))
    val t1Test = measureTimeMillis { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : {$t1Test}")

    val t2Test = measureTimeMillis { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : {$t2Test}")
}