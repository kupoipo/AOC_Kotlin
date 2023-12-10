package _2023.d10

import util.Direction
import util.Point
import java.lang.Exception

enum class Pipe(val label: Char, val startingDirection: Direction) {
    NONE('.', Direction.NONE) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return Direction.NONE to Point(-1, -1)
        }

        override fun directionsAccept(): List<Direction> {
            return listOf()
        }
    },
    VERTICAL('|', Direction.UP) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return when (d) {
                Direction.DOWN -> Direction.DOWN to p + Direction.UP
                Direction.UP -> Direction.UP to p + Direction.DOWN
                else -> throw Exception("Direction $d incorrect for $this pipe in $p")
            }
        }

        override fun directionsAccept(): List<Direction> {
            return listOf(Direction.UP, Direction.DOWN)
        }
    },
    HORIZONTAL('-', Direction.RIGHT) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return when (d) {
                Direction.LEFT -> Direction.LEFT to p + Direction.RIGHT
                Direction.RIGHT -> Direction.RIGHT to p + Direction.LEFT
                else -> throw Exception("Direction $d incorrect for $this pipe in $p")
            }
        }

        override fun directionsAccept(): List<Direction> {
            return listOf(Direction.RIGHT, Direction.LEFT)
        }
    },
    NORTH_EAST('L', Direction.UP) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return when (d) {
                Direction.RIGHT -> Direction.DOWN to p + Direction.UP
                Direction.UP -> Direction.LEFT to p + Direction.RIGHT
                else -> throw Exception("Direction $d incorrect for $this pipe in $p")
            }
        }

        override fun directionsAccept(): List<Direction> {
            return listOf(Direction.UP, Direction.RIGHT)
        }
    },
    NORTH_WEST('J', Direction.UP) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return when (d) {
                Direction.UP -> Direction.RIGHT to p + Direction.LEFT
                Direction.LEFT -> Direction.DOWN to p + Direction.UP
                else -> throw Exception("Direction $d incorrect for $this pipe in $p")
            }
        }

        override fun directionsAccept(): List<Direction> {
            return listOf(Direction.UP, Direction.LEFT)
        }

    },
    SOUTH_WEST('7', Direction.DOWN) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return when (d) {
                Direction.DOWN -> Direction.RIGHT to p + Direction.LEFT
                Direction.LEFT -> Direction.UP to p + Direction.DOWN
                else -> throw Exception("Direction $d incorrect for $this pipe in $p")
            }
        }

        override fun directionsAccept(): List<Direction> {
            return listOf(Direction.DOWN, Direction.LEFT)
        }
    },
    SOUTH_EAST('F', Direction.DOWN) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            return when (d) {
                Direction.DOWN -> Direction.LEFT to p + Direction.RIGHT
                Direction.RIGHT -> Direction.UP to p + Direction.DOWN
                else -> throw Exception("Direction $d incorrect for $this pipe in $p")
            }
        }

        override fun directionsAccept(): List<Direction> {
            return listOf(Direction.DOWN, Direction.RIGHT)
        }
    },
    START('S', Direction.NONE) {
        override fun nextPosition(d: Direction, p: Point): Pair<Direction, Point> {
            throw Exception("No move allowed")
        }

        override fun directionsAccept(): List<Direction> {
            throw Exception("No move allowed")
        }

    };

    abstract fun nextPosition(d: Direction, p: Point): Pair<Direction, Point>

    abstract fun directionsAccept(): List<Direction>

    fun accept(d: Direction): Boolean = d in directionsAccept()

    override fun toString(): String {
        return label.toString()
    }

    companion object {
        fun pipeFrom(c: Char): Pipe {
            return when (c) {
                '|' -> VERTICAL
                '-' -> HORIZONTAL
                'L' -> NORTH_EAST
                'J' -> NORTH_WEST
                '7' -> SOUTH_WEST
                'F' -> SOUTH_EAST
                '.' -> NONE
                'S' -> START
                else -> throw Exception("Parsing error")
            }
        }
    }
}