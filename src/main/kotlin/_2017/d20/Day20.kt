package _2017.d20

import util.*
import java.lang.Math.abs
import java.util.*
import kotlin.math.sign
import kotlin.system.measureNanoTime
import util.pairs

class Day20(override val input: String) : Day<Long>(input) {
    val particles = input.split("\n").map {
        it.allLong()
            .let { Particle(Vector(it[0], it[1], it[2]), Vector(it[3], it[4], it[5]), Vector(it[6], it[7], it[8])) }
    }

    override fun solve1(): Long {
        return particles.indexOf(particles.minBy { it.acceleration.manhattanLength }).toLong()
    }

    override fun solve2(): Long {
        data class ParticlePair(val p1: Particle, val p2: Particle, var lastDistance: Long = Long.MAX_VALUE) {
            override fun equals(other: Any?) = other is ParticlePair && p1 == other.p1 && p2 == other.p2
            override fun hashCode() = Objects.hash(p1, p2)
        }

        val pairs = particles.pairs()
            .map { (p1, p2) -> ParticlePair(p1, p2) }

        val tracked = pairs.toHashSet()
        val alive = pairs.flatMap { listOf(it.p1, it.p2) }.toHashSet()
        val dead = hashSetOf<Particle>()

        while (tracked.isNotEmpty()) {
            for (pair in tracked.toList()) {
                val (p1, p2) = pair
                val newDistance = p1 distanceTo p2
                if (newDistance == 0L) {
                    dead += setOf(p1, p2)
                } else if (newDistance > pair.lastDistance && !p1.isTurning && !p2.isTurning) {
                    tracked.remove(pair)
                }
                pair.lastDistance = newDistance
            }
            alive.removeIf { it in dead }
            tracked.removeIf { (p1, p2) -> p1 in dead || p2 in dead }
            alive.forEach { it.tick() }
        }
        return alive.size.toLong()
    }



    data class Vector(val x: Long, val y: Long, val z: Long) {

        val manhattanLength get() = setOf(x, y, z).map(::abs).sum()
        infix fun distanceTo(other: Vector) = (this - other).manhattanLength

        operator fun plus(other: Vector) = Vector(this.x + other.x, this.y + other.y, this.z + other.z)
        operator fun minus(other: Vector) = Vector(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    class Particle(var position: Vector, var velocity: Vector, val acceleration: Vector) {

        val isTurning
            get() = setOf(
                velocity.x to acceleration.x,
                velocity.y to acceleration.y,
                velocity.z to acceleration.z
            ).any { (v, a) -> a != 0L && v.sign != a.sign }

        fun tick() {
            this.velocity += this.acceleration
            this.position += this.velocity
        }

        infix fun distanceTo(other: Particle) = this.position distanceTo other.position
    }
}


fun main() {
    val day = Day20(readFullText("_2017/d20/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day20(readFullText("_2017/d20/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}