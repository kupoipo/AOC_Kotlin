package _2018.d23

import util.*
import java.util.PriorityQueue
import kotlin.math.max
import kotlin.system.measureNanoTime

class NanoBot(val p: Point3DLong, val range: Long)

class Day23(override val input : String) : Day<Long>(input) {
     val robots = input.split("\n").map { line -> line.allLong().let { NanoBot(Point3DLong(it[0], it[1], it[2]), it[3]) } }

    fun nbRobotInRange(robot: NanoBot): Long {
        return robots.filter { robot.p.manhattan(it.p) <= robot.range }.size.toLong()
    }

    override fun solve1(): Long {
        val max = robots.maxBy { it.range }
        return nbRobotInRange(max)
    }
    override fun solve2(): Long {
        val queue = PriorityQueue<Pair<Long, Int>> { p1, p2 ->
            p1.first.compareTo(p2.first)
        }
        for (robot in robots) {
            val distance = robot.p.manhattan(Point3DLong(0,0,0))
            queue.add(max(0, distance - robot.range) to 1)
            queue.add( distance + robot.range + 1 to -1)
        }

        var count = 0L
        var max = 0L
        var result = 0L

        while (queue.isNotEmpty()) {
            val data = queue.poll()
            count += data.second

            if (count > max) {
                result = data.first
                max = count
            }
        }

        return result
    }
}

fun main() {
    val day = Day23(readFullText("_2018/d23/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1/1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2/1e9}s")

    println()
    println()

    val dayTest = Day23(readFullText("_2018/d23/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test/1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test/1e9}s")
}