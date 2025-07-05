package _2024.d13

import util.*
import kotlin.system.measureNanoTime

class Day13(private val isTest: Boolean, override val input: String) : Day<Double>(input) {
    class Machine(val clawA: Point, val clawB: Point, val prize: Point) {
        private var b = 0.0
        private var a = 0.0

        /**
         * A(x) 94x + B(x) 22y = (x) 8400
         * A(y) 34x + B(y) 67y = (y) 5400
         * @return
         */
        fun score(): Double {
            val prize = ((prize.x * clawA.y) - (prize.y * clawA.x)).toDouble()
            val nbY = ((clawB.x * clawA.y) - (clawB.y * clawA.x)).toDouble()
            b = prize / nbY
            a = (this.prize.x - (clawB.x * b)) / clawA.x

            if (b.hasDecimalPart() || a.hasDecimalPart()) {
                b = 0.0
                a = 0.0
            }

            return b + a * 3
        }
    }

    private val machines = input.split("\n\n").map { it.allInts() }.map {
        Machine(Point(it[0], it[1]), Point(it[2], it[3]), Point(it[4], it[5]))
    }

    override fun solve1(): Double = machines.sumOf(Machine::score)

    override fun solve2(): Double {
        machines.forEach { it.prize.x += 10000000000000; it.prize.y += 10000000000000 }
        return machines.sumOf(Machine::score)
    }
}

fun main() {
    val day = Day13(false, readFullText("_2024/d13/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2().toLong()) } / 1e15}s")

    println()

    for (i in 1..5) {
        try {
            val dayTest = Day13(true, readFullText("_2024/d13/test$i"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }

}