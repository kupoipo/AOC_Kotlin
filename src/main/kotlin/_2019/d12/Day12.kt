package _2019.d12

import _2021.d18.split
import util.*
import java.time.Year
import kotlin.math.abs
import kotlin.system.measureNanoTime

class Day12(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    class Moon(var position: MutableList<Long>) {
        var velocity: MutableList<Long> = mutableListOf(0, 0, 0)
        fun updateVelocity(other: Moon) {
            repeat(3) {
                if (other.position[it] != position[it]) {
                    if (other.position[it] > position[it]) {
                        other.velocity[it]--
                        velocity[it]++
                    } else {
                        other.velocity[it]++
                        velocity[it]--
                    }
                }
            }
        }

        fun move() {
            position = position.mapIndexed { index, i -> i + velocity[index] }.toMutableList()
        }

        fun pot(): Long = position.sumOf { abs(it) }.toLong()

        fun kin(): Long = velocity.sumOf { abs(it) }.toLong()

        override fun toString(): String {
            return "pos=$position vel=$velocity"
        }
    }

    private var moons = input.split("\n").map { line -> Moon(line.allLong()) }

    fun step() {
        moons.pairs().forEach { (m1, m2) ->
            m1.updateVelocity(m2)
        }
        moons.forEach { m -> m.move() }
    }

    override fun solve1(): Long =
        repeat(1000) { step() }.let { moons.sumOf { it.kin() * it.pot() } }

    data class StateAxis(val pos: List<Long>, val velocity: List<Long>)

    private fun snapShot(axis: Int): StateAxis = StateAxis(moons.map { it.position[axis] }, moons.map { it.velocity[axis] })

    override fun solve2(): Long {
        moons = input.split("\n").map { line -> Moon(line.allLong()) }

        val axis = List(3) { mutableSetOf<StateAxis>() }
        val cycles = MutableList(3) { 0L }
        var index = 0L

        while (cycles.any { it == 0L }) {
            step()

            repeat(3) {
                val snap = snapShot(it)
                if (snap in axis[it] && cycles[it] == 0L) cycles[it] = index
                axis[it].add(snap)
            }
            index++
        }

        return lcmOf(cycles)
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