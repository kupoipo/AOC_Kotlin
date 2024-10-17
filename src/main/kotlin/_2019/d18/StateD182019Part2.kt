package _2019.d18

import util.Point
import util.State
import util.showMap

data class StateD182019Part2(
    val levels: List<Level>,
    val players: List<Point>,
    val keys: Set<Char>,
    val destroyedDoors: Set<Char>,
    override var time: Int,
    override var parent: State?
) :
    State(time = time) {
    override fun isDeadLock(): Boolean = false

    override fun nextStates(): MutableList<State> = levels.flatMapIndexed { index, level ->
        level.paths[players[index]]!!.filter { it ->
            it.doors.all { it in destroyedDoors }
        }.map { path ->
            val newPlayers = players.toMutableList()
            newPlayers[index] = path.goal.copy()
            StateD182019Part2(
                levels,
                newPlayers,
                keys.toMutableSet().also { it.remove(level.map[path.goal.y.toInt()][path.goal.x.toInt()]) },
                destroyedDoors.toMutableSet()
                    .also { it.add(level.map[path.goal.y.toInt()][path.goal.x.toInt()].uppercase().first()) },
                time + path.length,
                this
            )
        }
    }.toMutableList()


    override fun timeToGoal(): Int {
        return keys.size * 20 + time
    }

    override fun isGoal(): Boolean = keys.isEmpty() // keys.size == level.keys.size

    override fun hashCode(): Int {
        return "$players $keys".hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return players == (other as StateD182019Part2).players && keys == (other).keys
    }
}
