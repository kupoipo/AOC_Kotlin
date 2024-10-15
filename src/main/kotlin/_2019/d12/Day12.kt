package _2019.d12

import _2021.d18.split
import util.*
import java.time.Year
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day12(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    class Moon(var position: Point3DLong) {
        var velocity: Point3DLong = Point3DLong(0, 0, 0)

        fun updateVelocity(other: Moon) {
            if (other.position.x != position.x) {
                if (other.position.x > position.x) {
                    other.velocity.x--
                    velocity.x++
                } else {
                    other.velocity.x++
                    velocity.x--
                }
            }

            if (other.position.y != position.y) {
                if (other.position.y > position.y) {
                    other.velocity.y--
                    velocity.y++
                } else {
                    other.velocity.y++
                    velocity.y--
                }
            }

            if (other.position.z != position.z) {
                if (other.position.z > position.z) {
                    other.velocity.z--
                    velocity.z++
                } else {
                    other.velocity.z++
                    velocity.z--
                }
            }
        }

        fun move() {
            position += velocity
        }

        fun pot(): Long {
            return abs(position.x) + abs(position.y) + abs(position.z)
        }

        fun kin(): Long {
            return abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

        }

        override fun toString(): String {
            return "pos=$position vel=$velocity"
        }
    }

    private var moons =
        input.split("\n").map { line -> line.allLong().let { Moon(Point3DLong(it.first(), it[1], it[2])) } }

    fun step() {
        moons.pairs().forEach { (m1, m2) ->
            m1.updateVelocity(m2)
        }
        moons.forEach { m -> m.move() }
    }


    override fun solve1(): Long =
        repeat(1000) { step() }.let { moons.sumOf { it.kin() * it.pot() } }

    data class StateAxis(val pos: List<Long>, val velocity: List<Long>)

    fun snapShot(axis: Int): StateAxis = when (axis) {
        0 -> StateAxis(moons.map { it.position.x }, moons.map { it.velocity.x })
        1 -> StateAxis(moons.map { it.position.y }, moons.map { it.velocity.y })
        else -> StateAxis(moons.map { it.position.z }, moons.map { it.velocity.z })
    }

    override fun solve2(): Long {
        moons = input.split("\n").map { line -> line.allLong().let { Moon(Point3DLong(it.first(), it[1], it[2])) } }
        val x = mutableSetOf(snapShot(0))
        val y = mutableSetOf(snapShot(1))
        val z = mutableSetOf(snapShot(2))

        var cycleX = 0L
        var cycleY = 0L
        var cycleZ = 0L

        repeat(1000000) {
            step()

            val xS = snapShot(0)
            val yS = snapShot(1)
            val zS = snapShot(2)

            if (xS in x && cycleX == 0L) cycleX = it.toLong()
            if (yS in y && cycleY == 0L) cycleY = it.toLong()
            if (zS in z && cycleZ == 0L) cycleZ = it.toLong()

            x.add(xS)
            y.add(yS)
            z.add(zS)
        }

        println(cycleZ)
        println(cycleY)
        println(cycleX)

        return lcmOf(cycleZ, cycleX, cycleY)
    }
}


fun main() {
    val day = Day12(false, readFullText("_2019/d12/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()

    val dayTest = Day12(true, readFullText("_2019/d12/test"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
}