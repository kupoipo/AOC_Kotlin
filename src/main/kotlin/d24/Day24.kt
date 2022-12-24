package d24
import Day
import util.*

var map : Matrix<Char> = matrixOf()
val order = listOf(Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT)

class Blizzard(var pos : Point, val c : Char = '>') {

    private val dir : Direction = when (c) {
        '<' -> Direction.LEFT
        '>' -> Direction.RIGHT
        '^' -> Direction.UP
        else -> { Direction.DOWN }
    }

    fun nextPos() {
        pos += dir.times(1)
        if (pos.x == map[0].size - 1) pos.x = 1
        if (pos.x == 0) pos.x = map[0].size - 2
        if (pos.y == map.size - 1) pos.y = 1
        if (pos.y == 0) pos.y = map.size - 2
    }
}

class State(val pos : Point, val time : Int, val blizzards : Set<Blizzard>) {
    fun nextState() {
        (1 until map.size - 1).forEach { lig -> (1 until map[lig].size - 1).forEach { col -> map[lig][col] = '.' } }

        blizzards.forEach {
            it.nextPos();
            map[it.pos] = it.c
        }

        map[pos] = 'E'
    }
}

class Day24(override val input: String) : Day<String>(input) {

    var currentState = State(Point(x = 1, y = 0), 0, buildSet {
        input.split("\n").forEachIndexed{ y, line ->
            line.forEachIndexed { x, case -> if (case in "><^v") add(Blizzard(Point(y = y, x = x), c = case))
            }
        }
    } )

    val goal : Point

    init {
        map = emptyMatrixOf(input.count { it == '\n' } + 1, input.length / (input.count { it == '\n' } + 1) - 1, '.')
        (0 until map.size).forEach{line -> map[line][0] = '#'; map[line][map[0].size - 1] = '#'}
        (0 until map[0].size).forEach{col -> map[0][col] = '#'; map[map.size - 1][col] = '#'}
        goal = Point(x = map[0].size - 2, y = map.size - 1)
        map[goal] = 'G'; map[currentState.pos] = '.'
        currentState.blizzards.forEach{ map[it.pos] = it.c}
    }

    override fun solve1(): String {
       repeat(18) {
           println("Minute " + (it+1).toString() + ":")
           afficheMap(map)
       }
        return ""
    }

    override fun solve2(): String {
        return ""
    }

}


/*
for (d in order) {
    if (map[pos + d.times(1)] == '.') {
        pos += d.times(1)
        break
    }
}*/

fun main() {
    var d = Day24(readFullText("d24/test"))
    print(d.solve1())

}