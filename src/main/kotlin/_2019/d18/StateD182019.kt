package _2019.d18

import util.Point
import util.State
import util.showMap

data class StateD182019(
    val level: Level,
    val player: Point,
    val keys: Set<Char>,
    val destroyedDoors: Set<Char>,
    override var time: Int,
    override var parent: State?
) :
    State(time = time) {
    override fun isDeadLock(): Boolean = false

    override fun nextStates(): MutableList<State> = level.paths[player]!!.filter {
        it.doors.all { it in destroyedDoors }
    }.map { path ->
        StateD182019(
            level,
            path.goal.copy(),
            keys.toMutableSet().also { it.remove(level.map[path.goal.y.toInt()][path.goal.x.toInt()]) },
            destroyedDoors.toMutableSet()
                .also { it.add(level.map[path.goal.y.toInt()][path.goal.x.toInt()].uppercase().first()) },
            time + path.length,
            this
        )
    }.toMutableList()

    override fun timeToGoal(): Int {
        return  keys.size * 20 +  time
    }

    override fun isGoal(): Boolean = keys.isEmpty() // keys.size == level.keys.size

    override fun hashCode(): Int {
        return "$player $keys".hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return player == (other as StateD182019).player && keys == (other).keys
    }

    override fun toString(): String {
        showMap(level.map, 2) { c ->
            if (c !in "#.") {
                if (c in keys || c in destroyedDoors) "."
                else c.toString()
            } else
                c.toString()
        }

        return "$player"
    }
}
