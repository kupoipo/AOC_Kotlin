package _2019.d18

import util.*
import java.lang.Exception

data class PathInformation(val length: Int, val goal: Point, val doors: Set<Char>)
class Level(
    val map: Matrix<Char>,
    val paths: Map<Point, List<PathInformation>>,
    val keys: Set<Point>,
    val doors: Set<Point>
) {
    lateinit var state: StateD182019


    fun display() {
        showMap(map, 2)

    }

    companion object {
        fun levelFrom(input: String): Level {
            val matrix = matrixFromString(input, '.') { it }
            val tempDoorKeys = mutableSetOf<Point>()
            var player: Point = Point(0, 0)

            matrix.forEachPoint {
                when (matrix[it]) {
                    '@' -> {
                        player = it
                    }

                    else -> {
                        tempDoorKeys.add(it)
                    }
                }
            }

            val keys = tempDoorKeys.filter { matrix[it] in 'a'..'z' }
            val doors = tempDoorKeys.filter { matrix[it] in 'A'..'Z' }
            val paths = keys.associateWith { mutableListOf<PathInformation>() }.toMutableMap()
                .also { it[player] = mutableListOf() }
            for (i in keys.indices) {
                val key = keys[i]
                for (j in i + 1 until keys.size) {
                    val info = getPathInfo(key, keys[j], matrix)
                    paths[key]!!.add(info)
                    paths[keys[j]]!!.add(getPathInfo(keys[j], key, matrix))
                }
            }

            for (i in keys) {
                paths[player]!!.add(getPathInfo(player, i, matrix))
            }

            val level = Level(matrix, paths, keys.toMutableSet(), doors.toMutableSet())
            level.state = StateD182019(level, player, emptySet(), emptySet(), 0, null)
            println("Level computed")
            return level
        }

        fun getPathInfo(from: Point, to: Point, map: Matrix<Char>): PathInformation {
            val queue = mutableListOf(from to emptyList<Direction>())
            val visited = mutableListOf<Point>()

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()

                if (current.first == to) {

                    return PathInformation(current.second.size, to, buildSet {
                        var pos = from

                        for (d in current.second) {
                            pos += d
                            if (map[pos] in 'A'..'Z') {
                                add(map[pos])
                            }
                        }
                    })

                }
                visited.add(current.first)

                Direction.values().forEach { d ->
                    val newPos = current.first + d
                    if (map[newPos.y.toInt()][newPos.x.toInt()] != '#' && newPos !in visited) {
                        queue.add(newPos to current.second.toMutableList().also { it.add(d) })
                    }
                }
            }

            throw Exception("Shouldn't happen")
        }
    }

}