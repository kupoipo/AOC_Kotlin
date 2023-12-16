package _2023.d16

import util.*
import kotlin.system.measureNanoTime

enum class Mirror(label: Char) {
    HORIZONTAL('-') {
        override fun beamsFrom(position: Point, d: Direction): Collection<Beam> {
            return if (d == Direction.LEFT || d == Direction.RIGHT) listOf(Beam(position + d, d))
            else {
                listOf(
                    Beam(position + Direction.RIGHT, Direction.RIGHT),
                    Beam(position + Direction.LEFT, Direction.LEFT)
                )
            }
        }
    },
    VERTICAL('|') {
        override fun beamsFrom(position: Point, d: Direction): Collection<Beam> {
            return if (d == Direction.UP || d == Direction.DOWN) listOf(Beam(position + d, d))
            else {
                listOf(Beam(position + Direction.UP, Direction.UP), Beam(position + Direction.DOWN, Direction.DOWN))
            }
        }
    },
    SLASH('/') {
        override fun beamsFrom(position: Point, d: Direction): Collection<Beam> {
            return when (d) {
                Direction.UP -> listOf(Beam(position + Direction.RIGHT, Direction.RIGHT))
                Direction.DOWN -> listOf(Beam(position + Direction.LEFT, Direction.LEFT))
                Direction.RIGHT -> listOf(Beam(position + Direction.UP, Direction.UP))
                else -> listOf(Beam(position + Direction.DOWN, Direction.DOWN))
            }
        }
    },
    BACKSLASH('\\') {
        override fun beamsFrom(position: Point, d: Direction): Collection<Beam> {
            return when (d) {
                Direction.UP -> listOf(Beam(position + Direction.LEFT, Direction.LEFT))
                Direction.DOWN -> listOf(Beam(position + Direction.RIGHT, Direction.RIGHT))
                Direction.RIGHT -> listOf(Beam(position + Direction.DOWN, Direction.DOWN))
                else -> listOf(Beam(position + Direction.UP, Direction.UP))
            }
        }
    };

    abstract fun beamsFrom(position: Point, d: Direction): Collection<Beam>

    companion object {
        fun mirrorFromChar(label: Char): Mirror = when (label) {
            '-' -> HORIZONTAL
            '|' -> VERTICAL
            '\\' -> BACKSLASH
            else -> SLASH
        }
    }
}

class Beam(val p: Point, val d: Direction) {
    val positionCross = mutableSetOf<Point>()

    fun shot(): MutableList<Beam> {
        var position = Point(p)

        while (position.inMap(map) && map[position] == '.') {
            positionCross.add(position)
            position += d
        }

        if (position.outOfMap(map))
            return mutableListOf()

        positionCross.add(position)

        return Mirror.mirrorFromChar(map[position]).beamsFrom(position, d).toMutableList()
    }

    companion object {
        lateinit var map: Matrix<Char>

        fun init(input: String) {
            map = matrixFromString(input, '.') { it }
        }
    }

    override fun hashCode(): Int {
        var start = 17

        start = start * 31 + p.hashCode()
        start = start * 31 + d.hashCode()

        return start
    }

    override fun equals(other: Any?): Boolean {
        if (other is Beam)
            return other.p == p && d == other.d
        return super.equals(other)
    }
}

class Day16(override val input: String) : Day<Long>(input) {
    init {
        Beam.init(input)
    }

    fun solve(b: Beam) : Long {
        val beams = mutableSetOf(b)
        val queue = mutableListOf(beams.first())

        while (queue.isNotEmpty()) {
            val beam = queue.removeFirst()

            for (newBeam in beam.shot()) {
                if (newBeam !in beams) {
                    beams.add(newBeam)
                    queue.add(newBeam)
                }
            }
        }

        return buildSet { beams.forEach { b -> b.positionCross.forEach { p -> this.add(p) } } }.size.toLong()
    }

    override fun solve1(): Long = solve(Beam(Point(0, 0), Direction.RIGHT))

    override fun solve2(): Long {
        val originBeams = mutableListOf<Beam>()

        repeat (Beam.map.size) {
            originBeams.add(Beam(Point(it, 0), Direction.DOWN))
            originBeams.add(Beam(Point(it, Beam.map.lastIndex), Direction.UP))
        }

        repeat (Beam.map[0].size) {
            originBeams.add(Beam(Point(0, it), Direction.RIGHT))
            originBeams.add(Beam(Point(Beam.map[0].lastIndex, it), Direction.LEFT))
        }

        return originBeams.maxOf { solve(it) }
    }
}

fun main() {
    val day = Day16(readFullText("_2023/d16/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day16(readFullText("_2023/d16/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}