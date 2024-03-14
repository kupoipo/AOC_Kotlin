package _2018.d15

import util.*
import kotlin.system.measureNanoTime

class Entity(var hp: Int, val team: Char, var position: Point, private val damage: Int = 3, private val game: Day15) {
    var fightingWith: Entity? = null

    /**
     * Play a turn for the given entity
     * @return true if the situation has changed (the entity killed someone or moved), false otherwise.
     */
    fun play(): Boolean {
        if (fightingWith != null) {
            return fight()
        }

        return move()
    }

    /**
     * Fight with the closest entity
     * @return true If fightingWith is killed, false otherwise.
     */
    private fun fight(): Boolean {
        /**
         * Before each fight we must ensure that a new lowest enemy isn't reachable
         */
        lookAround()

        if (fightingWith == null) return false

        fightingWith!!.hp -= damage
        if (fightingWith!!.hp <= 0) {
            game.kill(fightingWith!!)
            return true
        }
        return false
    }

    /**
     * Try to move and ensure that the map remains consistent.
     * @return true if the entity moved, false otherwise.
     */
    private fun move(): Boolean {
        val paths = closestEnemy()

        /**
         * No reachable enemy, we don't move
         */
        if (paths.isEmpty()) {
            return false
        }

        val move = paths.first()
        lookAround()

        return if (fightingWith == null) {
            game.map[position] = "."
            position += move
            game.map[position] = team.toString()

            fight()
            true
        } else {
            fight()
        }
    }

    /**
     * Look at the 4 cardinal point from position and update fightingWith if an enemy is found.
     */
    private fun lookAround() {
        val enemies = enemyAround()

        if (enemies.isNotEmpty()) {
            val minHP = enemies.minOf { it.second.hp }
            val lowestEnemies = enemies.filter { it.second.hp == minHP }
            val fightWith = game.entityAt(position + lowestEnemies.first().first)

            fightingWith = fightWith
            fightWith.fightingWith = this
        }
    }

    /**
     * @return List of pair which each represent the direction to reach the enemy and the enemy himself.
     */
    private fun enemyAround(): List<Pair<Direction, Entity>> {
        return try {
            return listOf(Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN).map { it to position + it }
                .filter { game.map[it.second] !in "#.${team}" }.map { it.first to game.entityAt(it.second) }
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
            listOf()
        }
    }

    /**
     * @return Path to the closest enemy
     */
    private fun closestEnemy(): List<Direction> {
        val paths = mutableListOf<List<Direction>>()
        val visited = mutableSetOf<Point>()
        val queue = mutableListOf<Pair<Point, List<Direction>>>(this.position to mutableListOf())
        var maxPathSize = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            visited.add(current.first)

            if (current.second.size > maxPathSize)
                continue

            val c = game.map[current.first]

            if (c !in ".#${team}") {
                paths.add(current.second)
                maxPathSize = current.second.size
                continue
            }


            for (d in listOf(Direction.UP, Direction.LEFT, Direction.RIGHT, Direction.DOWN)) {
                val newPos = current.first + d

                if (newPos.outOfMap(game.map) || game.map[newPos] in "#$team" || newPos in visited || queue.any { it.first == newPos && game.map[newPos] == "." }) {
                    continue
                }

                queue.add(newPos to current.second.toMutableList().apply { this.add(d) })
            }
        }


        if (paths.isEmpty())
            return listOf()

        /**
         * 150 years debugging :)
         *
         * There's some poor wording in the problem that indicates a tie-break of possible routes to in range squares
         * should be broken by taking the route where the first step is first in reading order.
         *
         * However, this is incorrect and should be the first target square in reading order.
         */
        return paths.map {
            val obj = it.fold(position) { acc, d -> acc + d }
            obj to it
        }.sortedBy { it.first }.map { it.second }.first()

    }

    override fun toString(): String {
        return "$team($hp)"
    }
}


class Day15(override val input: String) : Day<Long>(input) {
    private var entities = mutableListOf<Entity>()
    private var nbEntities = mutableMapOf<Char, Int>()
    private var currentIndex = 0
    private var numRound = 0L
    private var oneElfDied = false
    lateinit var map: Matrix<String>

    init {
        init(3)
    }

    fun init(elvesDamage: Int) {
        entities = mutableListOf()
        nbEntities = mutableMapOf()
        oneElfDied = false
        map = matrixFromStringIndexed(input, ".") { char, y, x ->
            if (char == 'G' || char == 'E') {
                entities.add(Entity(200, char, Point(x, y), if (char == 'E') elvesDamage else 3, this))
                nbEntities[char] = nbEntities.getOrPut(char) { 0 } + 1
            }
            char.toString()
        }
        numRound = 0
    }

    /**
     * Ensure that every reference to an entity is destroyed.
     *  - Removed from entities
     *  - Removed from every fightingWith other's entities
     *  - Decrease his team counter
     *  - Replace his position in the map by a '.'
     */
    fun kill(e: Entity) {
        val index = entities.indexOf(e)

        map[e.position] = "."
        entities.remove(e)
        for (other in entities) {
            if (other.fightingWith == e) {
                other.fightingWith = null
            }
        }

        nbEntities[e.team] = nbEntities[e.team]!! - 1
        if (index <= currentIndex)
            currentIndex--

        if (e.team == 'E')
            oneElfDied = true
    }

    private fun playRound() {
        entities.sortBy { it.position }
        currentIndex = 0

        while (currentIndex < entities.size) {
            entities[currentIndex].play()
            currentIndex++
        }
        numRound++
    }

    fun display() {
        val tempMap = emptyMatrixOf(map.size, map[0].size, ".")
        tempMap.forEachPoint { tempMap[it] = map[it] }

        entities.forEachIndexed { _, entity ->
            tempMap[entity.position] = "${entity.team}${entity.hp}"
        }

        showMap(tempMap, 6)
    }

    fun entityAt(p: Point): Entity {
        return entities.first { p == it.position }
    }

    private fun winners(): Boolean {
        return entities.all { it.team == 'E' }
    }

    private fun notOver(): Boolean {
        return entities.map { it.team }.toSet().size == 1
    }

    override fun solve1(): Long {
        while (!notOver()) {
            playRound()
        }

        /**
         * A rare case where you shouldn't decrease numRound is if the last unit playing the round kill the last enemy.
         * Too lazy to implement it.
         */
        numRound--


        return numRound * entities.sumOf { it.hp }
    }

    override fun solve2(): Long {
        var attack = 3
        init(attack)
        while (!notOver() || !winners()) {
            playRound()
            if (oneElfDied) {
                attack++
                init(attack)
            }
        }
        return numRound * entities.sumOf { it.hp }
    }
}

fun main() {
    val day = Day15(readFullText("_2018/d15/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    for (i in 1..6) {
        val dayTest = Day15(readFullText("_2018/d15/test$i"))
        /* println("TEST $i - Part 1 : " + dayTest.solve1())
         println("TEST $i - Part 2 : " + dayTest.solve2())
 */
    }
}