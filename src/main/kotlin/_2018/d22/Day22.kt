package _2018.d22

import _2021.d15.map
import util.*
import java.util.Objects
import kotlin.system.measureNanoTime

class Day22(override val input: String) : Day<Long>(input) {

    enum class Gear {
        NEITHER {
            override fun accept(l: Long): Boolean {
                return l != ROCKY
            }
        },
        TORCH {
            override fun accept(l: Long): Boolean {
                return l != WET
            }
        },
        CLIMBING_ROPE {
            override fun accept(l: Long): Boolean {
                return l != NARROW
            }
        };

        abstract fun accept(l: Long): Boolean
    }

    class SaveState(override var parent: State?, override var time: Int, val p: Point, val g: Gear) :
        State(parent, time) {
        override fun isDeadLock(): Boolean {
            return p.outOfMap(mapModulo)
        }

        override fun nextStates(): MutableList<State> {
            val nextStates = mutableListOf<State>()

            listOf(p.right(), p.down(), p.up(), p.left()).forEach { point: Point ->
                if (point.outOfMap(mapModulo)) return@forEach

                if (g.accept(mapModulo[point])) {
                    nextStates.add(SaveState(this, time + 1, point, g))
                }
            }

            Gear.values().filter { it != g }.forEach { newGear ->
                if (listOf(
                        p.right(),
                        p.down(),
                        p.left(),
                        p.up()
                    ).any { nextPosition -> nextPosition.inMap(mapModulo) && newGear.accept(mapModulo[nextPosition]) && newGear.accept(
                        mapModulo[p]) }
                ) {
                    nextStates.add(SaveState(this, time + 7, p, newGear))
                }
            }

            return nextStates
        }

        override fun isGoal(): Boolean {
            return p == target && g == Gear.TORCH
        }

        override fun equals(other: Any?): Boolean {
            return this.p == (other as SaveState).p && this.g == other.g
        }

        override fun hashCode(): Int {
            return Objects.hash(p, g)
        }

        override fun toString(): String {
            return "[$time] - $p - $g"
        }
    }

    init {
        input.split("\n").let { lines ->
            depth = lines.first().firstInt().toLong()
            target = lines.last().allInts().let { Point(it.last(), it.first()) }
        }

        mapValue = emptyMatrixOf(target.y + EXTEND_MAP, target.x + EXTEND_MAP, ROCKY)
        mapModulo = emptyMatrixOf(target.y + EXTEND_MAP, target.x + EXTEND_MAP, ROCKY)


        mapValue.forEachPoint {
            if (it == target) mapValue[it] = depth
            else {
                if (it.x == 0L) {
                    mapValue[it] = it.y * timeY + depth
                } else if (it.y == 0L) {
                    mapValue[it] = it.x * timeX + depth
                } else {
                    mapValue[it] = mapValue[it.up()] * mapValue[it.left()] + depth
                }
            }

            mapValue[it] %= timeModulo
            mapModulo[it] = mapValue[it] % 3L
        }
    }

    override fun solve1(): Long {
        return mapModulo.take(target.y.toInt() + 1).sumOf { it.take(target.x.toInt() + 1).sum() }
    }

    override fun solve2(): Long {
        return State.shortestPastFrom(SaveState(null, 0, Point(0, 0), Gear.TORCH))!!.time.toLong()
    }

    companion object {
        const val EXTEND_MAP = 50
        const val ROCKY = 0L
        const val WET = 1L
        const val NARROW = 2L
        const val timeX = 16807L
        const val timeY = 48271L
        const val timeModulo = 20183L

        var depth: Long = 0L
        lateinit var target: Point
        lateinit var mapValue: Matrix<Long>
        lateinit var mapModulo: Matrix<Long>
    }
}

fun main() {
    val day = Day22(readFullText("_2018/d22/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day22(readFullText("_2018/d22/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}