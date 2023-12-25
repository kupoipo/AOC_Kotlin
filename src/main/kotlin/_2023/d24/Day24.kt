package _2023.d24

import _2022.d22.position
import util.*
import java.lang.Math.abs
import java.math.BigDecimal
import kotlin.system.measureNanoTime

typealias Position = Pair<BigDecimal, BigDecimal>

data class VectorStone(
    val direction: Point3DBigDecimal,
    val hailstoneDestroyed: MutableSet<HailStone>,
    var position: Point3DBigDecimal = Point3DBigDecimal(
        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
    )
)

data class HailStone(val position: Point3DBigDecimal, val velocity: Point3DBigDecimal) {
    private val positionT1 = position + velocity
    val num = ID++
    private fun det(x: Position, y: Position): BigDecimal {
        return x.first * y.second - x.second * y.first
    }

    fun posAtTime(time: Int): Point3DBigDecimal {
        return position + velocity * BigDecimal(time)
    }

    fun intersectAtTime(other: HailStone): Position {
        val xDiff = -velocity.x to -other.velocity.x
        val yDiff = -velocity.y to -other.velocity.y

        val det = det(xDiff, yDiff)

        /**
         * If collinear.
         */
        if (det == BigDecimal.ZERO)
            return BigDecimal.ZERO to BigDecimal.ZERO

        val dist =
            position.x * positionT1.y - positionT1.x * position.y to other.position.x * other.positionT1.y - other.positionT1.x * other.position.y
        val x = det(dist, xDiff) / det
        val y = det(dist, yDiff) / det
        return x to y
    }

    override fun toString(): String {
        return num.toString()
    }

    companion object {
        var ID = BigDecimal.ZERO
    }
}

class Day24(override val input: String) : Day<Long>(input) {
    private val rangeAccepted = input.split("\n").first().allLong().map { it.toBigDecimal() }.let { (a, b) -> a..b }
    private val hailstones = input.split("\n").drop(1).map { line ->
        line.allLong().map { it.toBigDecimal() }
            .let { HailStone(Point3DBigDecimal(it[0], it[1], it[2]), Point3DBigDecimal(it[3], it[4], it[5])) }
    }
    private val nbCollide = mutableMapOf<HailStone, List<BigDecimal>>()

    override fun solve1(): Long = hailstones.indices.sumOf { i ->
        (i + 1..hailstones.lastIndex).count { j ->
            val a = hailstones[i]
            val b = hailstones[j]

            a.intersectAtTime(b).let {
                if (it.first == BigDecimal.ZERO)
                    return@count false

                var dx = it.first - a.position.x
                var dy = it.second - a.position.y

                if (dx > BigDecimal.ZERO != a.velocity.x > BigDecimal.ZERO || dy > BigDecimal.ZERO != a.velocity.y > BigDecimal.ZERO) {
                    return@count false
                }
                dx = it.first - b.position.x
                dy = it.second - b.position.y

                if (dx > BigDecimal.ZERO != b.velocity.x > BigDecimal.ZERO || dy > BigDecimal.ZERO != b.velocity.y > BigDecimal.ZERO) {
                    return@count false
                }

                if (it.first in rangeAccepted && it.second in rangeAccepted) {
                    nbCollide[a] = nbCollide.getOrPut(a) { mutableListOf() } + b.num
                    return@count true
                }
            }
            false
        }
    }.toLong()


    override fun solve2(): Long {
        val h1 = hailstones[0]
        val h2 = hailstones[1]

        for (x in -300L..300L) {
            for (y in -300L..300L) {
                for (z in -300L..300L) {
                    val dx = BigDecimal(x)
                    val dy = BigDecimal(y)
                    val dz = BigDecimal(z)

                    if (dx * dy * dz == BigDecimal.ZERO)
                        continue

                    val a = h1.velocity.x - dx
                    val b = h1.velocity.y - dy
                    val c = h2.velocity.x - dx
                    val d = h2.velocity.y - dy

                    if (c == BigDecimal.ZERO || (a * d) - (b * c) == BigDecimal.ZERO) {
                        continue;
                    }

                    /**
                     * Interception time
                     */
                    val t =
                        (d * (h2.position.x - h1.position.x) - c * (h2.position.y - h1.position.y)) / ((a * d) - (b * c));

                    val startPosition = Point3DBigDecimal(
                        h1.position.x + h1.velocity.x * t - dx * t,
                        h1.position.y + h1.velocity.y * t - dy * t,
                        h1.position.z + h1.velocity.z * t - dz * t
                    )

                    /**
                     * Check if every hailstone is on the current vector
                     */
                    if (!hailstones.any { h ->
                            val u = if (h.velocity.x != dx) {
                                (startPosition.x - h.position.x) / (h.velocity.x - dx);
                            } else if (h.velocity.y != dy) {
                                (startPosition.y - h.position.y) / (h.velocity.y - dy);
                            } else {
                                (startPosition.z - h.position.z) / (h.velocity.z - dz);
                            }

                            (startPosition.x + u * dx != h.position.x + u * h.velocity.x) || (startPosition.y + u * dy != h.position.y + u * h.velocity.y) || (startPosition.z + u * dz != h.position.z + u * h.velocity.z)
                        }) {
                        return (startPosition.x + startPosition.y + startPosition.z).toLong()
                    }
                }
            }
        }
        return 0L
    }
}

fun main() {
    val day = Day24(readFullText("_2023/d24/input"))
    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day24(readFullText("_2023/d24/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}