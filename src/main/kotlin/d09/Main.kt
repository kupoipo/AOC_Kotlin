package d9

import readInput
import java.lang.Math.abs
import java.lang.String.format
import kotlin.math.sqrt

const val SIZE = 1000
const val DEBUG = false

fun getNewPos(pos1 : Pair<Int, Int>, pos2 : Pair<Int, Int>) : Pair<Int, Int> {
    var dx = 0; var dy = 0;

    if (pos1.first - pos2.first == -2) {
        dy = 1
    }
    if (pos1.first - pos2.first == 2) {
        dy = -1
    }
    if (pos1.second - pos2.second == -2) {
        dx = 1
    }
    if (pos1.second - pos2.second == 2) {
        dx = -1
    }

    if (dx != 0 || dy != 0) {
        return Pair(pos1.first + dy, pos1.second + dx)
    }

    return Pair(pos2.first, pos2.second)

}

fun part1(content : List<String>) {
    var map = Array<Array<Char>>(SIZE) { Array<Char>(SIZE) {'.'} }
    var visited = Array<Array<Char>>(SIZE) { Array<Char>(SIZE) {'.'} }

    var posHead = Pair(SIZE/2 - 1, SIZE/2 - 1)
    var posTail = Pair(SIZE/2 - 1, SIZE/2 - 1)

    map[posHead.first][posHead.second] = 'H'
    map[posTail.first][posTail.second] = 'T'

    var cptVisited = 1

    for (line in content) {
        var dir = line.split(" ")[0]
        var nb = line.split(" ")[1].toInt()

        var dx : Int; var dy : Int

        when (dir) {
            ("R") -> {
                dx = 1; dy = 0
            }

            ("L") -> {
                dx = -1; dy = 0
            }

            ("U") -> {
                dx = 0; dy = -1
            }

            else -> {
                dx = 0; dy = 1;
            }
        }

        for (i in 1..nb) {
            map[posHead.first][posHead.second] = '.'

            posHead = Pair(posHead.first + dy, posHead.second + dx)

            map[posHead.first][posHead.second] = 'H'

            posTail = getNewPos(posHead, posTail)

            if (visited[posTail.first][posTail.second] != '#') {
                cptVisited++
                visited[posTail.first][posTail.second] = '#'
            }
       }


    }

    if (visited[SIZE/2 - 1][SIZE/2 - 1] == '#') {
        cptVisited--
    }

}

fun move(posDebut : Pair<Int, Int>, posArrive : Pair<Int, Int>, map : Array<Array<Char>>, c : Char) : Pair<Int, Int> {
    if (map[posDebut.first][posDebut.second] == c || map[posDebut.first][posDebut.second] == '.') {
        map[posDebut.first][posDebut.second] = '.'
        map[posArrive.first][posArrive.second] = c
    }

    return posArrive
}

fun part2(content : List<String>) {
    var map = Array<Array<Char>>(SIZE) { Array<Char>(SIZE) {'.'} }
    var visited = Array<Array<Char>>(SIZE) { Array<Char>(SIZE) {'.'} }

    var posHead = Pair(SIZE/2 - 1, SIZE/2 - 1)
    var posTail = Array<Pair<Int, Int>> (9) {Pair(SIZE/2 - 1, SIZE/2 - 1) }

    map[posHead.first][posHead.second] = 'H'

    var cptVisited = 1

    for (line in content) {
        var dir = line.split(" ")[0]
        var nb = line.split(" ")[1].toInt()

        var dx : Int; var dy : Int

        when (dir) {
            ("R") -> {
                dx = 1; dy = 0
            }

            ("L") -> {
                dx = -1; dy = 0
            }

            ("U") -> {
                dx = 0; dy = -1
            }

            else -> {
                dx = 0; dy = 1;
            }
        }

        for (i in 1..nb) {
            posHead = move(posHead, Pair(posHead.first + dy, posHead.second + dx), map, 'H')
            posTail[0] = move(posTail[0], getNewPos(posHead, posTail[0]), map, '1')

            for (i in 1 until posTail.size ) {
                posTail[i] = move(posTail[i], getNewPos(posTail[i-1], posTail[i]), map, (i+1).toString()[0])
            }

            if (visited[posTail[posTail.size - 1].first][posTail[posTail.size - 1].second] != '#') {
                cptVisited++
                visited[posTail[posTail.size - 1].first][posTail[posTail.size - 1].second] = '#'
            }

            if (DEBUG) affiche(map)
        }

    }

    if (visited[SIZE/2 - 1][SIZE/2 - 1] == '#') {
        cptVisited--
    }


    println(cptVisited)

}

fun <T> affiche(tab : Array<Array<T>>) {
    print("  ")
    for (i in 0 until SIZE) {
        print("%2d".format(i))
    }

    println()

    for ((index,line) in tab.withIndex()) {
        print("%2d ".format(index))
        for (cell in line) {
            print(cell.toString()+ " ")
        }
        println()
    }
}

fun main() {
    part2(readInput("d9/input"))
}