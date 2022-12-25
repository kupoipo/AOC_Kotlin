package _2022.d12
import util.readInput

import util.Direction

var arrive : Pair<Int, Int> = Pair(0,0)
var start : MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int,Int>>()
var map : MutableList<MutableList<Int>> = mutableListOf<MutableList<Int>>()

class Node(val parent : Node?, val lig : Int, val col : Int) {
    var id = ID++
    fun getNeighbors() : List<Node> {
        var height = map[lig][col]
        var neighbors = mutableListOf<Node>()

        for (dir in Direction.values()) {
            var newLig = lig + dir.dy
            var newCol = col + dir.dx

            if (newLig >= 0 && newCol >= 0 && newCol < map[0].size && newLig < map.size) {

                if (map[newLig][newCol] - 1 <= height) {
                    neighbors.add(Node(this,  newLig, newCol))
                }
            }

        }

        return neighbors
    }

    override fun equals(other : Any?) : Boolean {
        if (other == null || other !is Node) return false

        return this.col == other.col && this.lig == other.lig
    }

    override fun toString(): String {
        return "Node n°" + id + " - " + map[lig][col].toString() + "  [" + lig + "," + col + "]"
    }

    fun isFinish(): Boolean {
        return arrive.first == lig && arrive.second == col
    }

    fun showPath() : Int {
        var compteur = 0
        var parent : Node? = this

        while (parent != null) {
          //  println(parent)
            parent = parent.parent
            compteur++
        }

        return compteur - 1
    }

    companion object {
        var ID = 0
    }
}

fun part1(startingPoint : Pair<Int, Int>) : Int {
    var nodeStart = Node(null, startingPoint.first, startingPoint.second)
    var queue = mutableListOf<Node>()
    var visitedQueue = mutableListOf<Node>()

    queue.add(nodeStart)

    while (queue.isNotEmpty()) {
        var currentNode = queue.removeFirst()

        if (currentNode.isFinish()) {
            return currentNode.showPath()
        }

        visitedQueue.add(currentNode)
        currentNode.getNeighbors().forEach {
            if (it !in visitedQueue && it !in queue) {
                queue.add(it)
            }
        }

    }

    return -1
}


fun readMap(content : List<String>) {
    map = MutableList<MutableList<Int>> (0) { mutableListOf() }

    for ((lig, line) in content.withIndex()) {
        map.add(mutableListOf())
        for ((col, cell) in line.withIndex()) {

            if (cell == 'S' || cell == 'a') {
                start.add(Pair(lig, col))
                map[lig].add(0)
                continue
            }

            if (cell == 'E') {
                arrive = Pair(lig, col)
                map[lig].add(26)
                continue
            }

            map[lig].add(cell.code - 97)

        }
    }
}


fun main() {
    var content = readInput("d12/input")

    readMap(content)
    for (startingPoint in start) {
        var cout = part1(startingPoint)
        if (cout != -1) println( cout)
        else println(startingPoint)
    }
}



