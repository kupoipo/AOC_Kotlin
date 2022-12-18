package d17
import Direction
import matrixOf
import readInput
import Point
import addFirstLine
import afficheMap

const val ROCK = '@'
const val PLACED_ROCK = '#'
const val EMPTY = '.'

const val NB_FALL = 1_000_000_000_000
const val NB_ROCK_CYCLE = 1690
const val HEIGHT_CYCLE = 2647
const val NB_ROCK_BEFORE_CYCLE = 1690
const val HEIGHT_BEFORE_CYCLE = 2645

var STARTING_POINT = Point(2, 0)
var map = matrixOf(MutableList<MutableList<Char>>(7) {MutableList<Char>(7) { EMPTY } } )

fun willCollide(nextRock: Rock, dir : Direction): Boolean {
    nextRock.move(dir)

    if (nextRock.y + nextRock.getHeight() > map.size || nextRock.x + nextRock.getWidth() > map[0].size || nextRock.y < 0 || nextRock.x < 0) {
        nextRock.unmove(dir)
        return true
    }

    for (y in nextRock.y until nextRock.y + nextRock.getHeight()) {
        for (x in nextRock.x until nextRock.x + nextRock.getWidth()) {
            if (nextRock.piece[y - nextRock.y][x - nextRock.x] == ROCK && map[y][x] != EMPTY) {
                nextRock.unmove(dir)
                return true
            }
        }
    }

    nextRock.unmove(dir)

    return false
}

fun tryAndMove(nextRock: Rock, s: Char) {
    var dir = if (s == '>') Direction.RIGHT else Direction.LEFT

    if (!willCollide(nextRock, dir))
        nextRock.move(dir)
}

fun addRock(nextRock: Rock) {

    for (y in nextRock.y  until  nextRock.y + nextRock.getHeight()) {
        for (x in nextRock.x  until  nextRock.x + nextRock.getWidth()) {
            if (nextRock.piece[y - nextRock.y][x - nextRock.x] == ROCK)
                map[y][x] = PLACED_ROCK
        }
    }
}
fun removeRock(nextRock: Rock) {

    for (y in nextRock.y  until  nextRock.y + nextRock.getHeight()) {
        for (x in nextRock.x  until  nextRock.x + nextRock.getWidth()) {
            if (nextRock.piece[y - nextRock.y][x - nextRock.x] == ROCK)
                map[y][x] = EMPTY
        }
    }
}

fun part1(content : String) : String {
    var currentMove = 0

    var rockToFall = (NB_FALL - NB_ROCK_BEFORE_CYCLE) % NB_ROCK_CYCLE
    var cyclePast = (NB_FALL - NB_ROCK_BEFORE_CYCLE) / NB_ROCK_CYCLE
    var l = mutableListOf<Pair<Int, Int>>()
    
    for (i in 1 .. NB_ROCK_BEFORE_CYCLE + NB_ROCK_CYCLE +rockToFall) {


        var nextRock : Rock = RockFactory.getNext()
        var falling = true
        var emptyLines = (0..if (map.size < 7) map.size - 1 else 6).count { line -> !map[line].contains(PLACED_ROCK) }

        for (i in 0 until  (3 + nextRock.getHeight() - emptyLines)) {
            map.addFirstLine(EMPTY)
        }


        if (emptyLines > 3 + nextRock.getHeight()) {
            nextRock.y = emptyLines - 3 - nextRock.getHeight()
        }
        // Found pattern
        // println((RockFactory.next % 5).toString()+ " - " + (currentMove%content.length))

        // Found starting rock pattern, height pattern and number rock by pattern
        // if ((RockFactory.next % 5) == 0 && (currentMove%content.length) == 4) {
        // l.add(i to (map.size - (0..6).count { line -> !map[line].contains(PLACED_ROCK) }))
        // }

        while (falling) {
            tryAndMove(nextRock, content[currentMove])
            currentMove = (currentMove + 1) % content.length

            if (willCollide(nextRock, Direction.DOWN)) {
                addRock(nextRock)

                falling = false
            } else
                nextRock.move(Direction.DOWN)
        }
    }


    return (((cyclePast - 1) * (HEIGHT_CYCLE)) + (map.size - (0..6).count { line -> !map[line].contains(PLACED_ROCK) })).toString()
}




fun part2(content : List<String>) : String {

    return ""
}

fun main() {
    var content = readInput("d17/input")

    println(part1(content[0]))
    println(part2(content))
}