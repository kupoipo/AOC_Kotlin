package d14
import util.readInput

val FALLING_MOVES = arrayOf(1 to 0, 1 to -1, 1 to 1)

const val ROCK = '#'
const val AIR = '.'
const val SAND = '+'
const val FELL_SAND = 'o'

var map : MutableList<MutableList<Char>> = mutableListOf()
var sandEntryPoint : Int = 0

fun part1() : String {
    var cptSand = 0
    var sandFalling = true

    map[0][sandEntryPoint] = SAND

    while (sandFalling) {
        var lig = 0; var col = sandEntryPoint
        var falling = true

        while (falling) {
            falling = false
            for (it in FALLING_MOVES) {
                if (map[lig + it.first][col + it.second] == '.') {
                    lig += it.first
                    col += it.second
                    falling = true
                    break
                }
            }

            if (!falling) {
                if (lig == 0 && col == sandEntryPoint) sandFalling = false
                map[lig][col] = FELL_SAND
            }
        }
        cptSand++
    }

    return (cptSand).toString()
}
fun createMap(content : List<String>) {
    var decalage = 300
    var listRocks = mutableListOf<List<Pair<Int,Int>>>()


    content.forEach { listRocks.add( it.split(" -> ").map {
        duo -> duo.split(",").let {
                (a,b) -> a.toInt() to b.toInt()
        }
    })}

    var minCol : Int = listRocks.minOf { it.minOf { pair -> pair.first } }
    sandEntryPoint = 500 - minCol + decalage

    map = MutableList<MutableList<Char>> (listRocks.maxOf { it.maxOf { pair -> pair.second } } + 1) {
        MutableList<Char> (listRocks.maxOf { it.maxOf { pair -> pair.first } } - minCol + 1 ) { AIR }
    }

    listRocks.forEach { rocks ->
        var lastPoint = rocks[0]

        for (point in rocks.subList(1, rocks.size)) {
            var pairCol =
                (if (point.first < lastPoint.first) (point.first to lastPoint.first) else (lastPoint.first to point.first))
            var pairLig =
                (if (point.second < lastPoint.second) (point.second to lastPoint.second) else (lastPoint.second to point.second))

            for (lig in pairLig.first .. pairLig.second) {
                for (col in pairCol.first - minCol .. pairCol.second - minCol ) {
                    map[lig][col] = ROCK
                }
            }

            lastPoint = point
        }
    }

    map.forEach {
        for (i in 0 until decalage) {
            it.add(0, AIR)
            it.add(AIR)
        }
    }

    map.add(MutableList<Char>(map[0].size)  { AIR })
    map.add(MutableList<Char>(map[0].size)  { ROCK })
}

fun main() {
    var content = readInput("d14/input")
    createMap(content)
    println(part1())
}