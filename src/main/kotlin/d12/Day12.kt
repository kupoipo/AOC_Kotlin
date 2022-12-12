package d12
import readInput
import kotlin.math.abs

var arrive : Pair<Int, Int> = Pair(0,0)
var map : List<Array<Char>> = mutableListOf<Array<Char>>()
class Node(val parent : Node?, val cout : Int, val col : Int, val lig : Int) {

    fun getNeighbors() : List<Node> {
        var height : Char = map[lig][col]
        var neighbors = mutableListOf<Node>()

        for (i in 1 ..  1) {
            for (j in -1 ..  1) {
                if (abs(i+j) == 1) {
                    var newLig = lig + i
                    var newCol = col + 1
                    if (newLig > 0 && newCol > 0 && newCol < map[0].size && newLig < map.size) {
                        if (map[newLig][newCol] <= height) {
                            neighbors.add(Node(this, cout + 1, newCol, newLig))
                        }
                    }
                }
            }
        }

        return neighbors
    }

    fun heuristique(): Int {
        return abs(col - arrive.second) + abs(lig - arrive.first) + cout
    }
}

fun part1(content : List<String>) : String {
    var closedList = mutableListOf<Node>()
    var openList = mutableListOf<Node>()

    var depart = Node(null, 0, 0, 0)

    while (openList.isNotEmpty()) {
        
    }

    return ""
}

fun part2(content : List<String>) : String {

    return ""
}

fun readMap(content : List<String>) : List<Array<Char>>{
    map = List<Array<Char>> (5) { Array<Char> (8) { '.' } }

    for ((lig, line) in map.withIndex()) {
        for ((col, cell) in line.withIndex()) {
            if (cell == 'E') {
                arrive = Pair(lig, col)
            }
            map[lig][col] = cell
        }
    }

    return map
}

fun main() {
    var content = readInput("d12/input")
    
    println(part1(content))
    println(part2(content))
}