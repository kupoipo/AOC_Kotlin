package _2021.d19

import util.*
import kotlin.system.measureTimeMillis

class Scanner(var pos : Point3DLong, val beacons : List<Point3DLong>) {
    var paired : Scanner? = null

    companion object {
        fun createScanner(lines : List<String>) : Scanner {
            val points = mutableListOf<Point3DLong>()

            lines.forEach { line -> points.add(line.allLong().let { Point3DLong(it[0],it[1],it[2]) } ) }

            return Scanner(Point3DLong(0,0,0), points)
        }
    }
}

class Day19(override val input : String) : Day<Long>(input) {
    val scanners = buildList{ input.split("\n\n").forEach { add(Scanner.createScanner(it.split("\n").drop(1))) } }

    fun relativePosTo(sc1 : Scanner, sc2 : Scanner) : Point3DLong {
        return Point3DLong(1,1,1);
    }

    override fun solve1(): Long {
        return -1
    }
    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    //var day = Day19(readFullText("_2021/d19/test"))
    var day = Day19(readFullText("_2021/d19/input"))

    val t1 = measureTimeMillis { println("Part 1 : " + day.solve1()); (1..1_000_000_000).forEach { it+1 } }
    println("Temps partie 1 : {$t1}")

    val t2 = measureTimeMillis { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : {$t2}")
}