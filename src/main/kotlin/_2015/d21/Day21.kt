package _2015.d21

import d7.list
import util.*
import java.lang.Exception
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.pow
import kotlin.system.measureNanoTime

data class Item(val cost: Int, val damage: Double, val armor: Double)

class Player(var hp: Double = 100.0, var damage: Double = 0.0, var armor: Double = 0.0, var cost: Int = 0) {
    fun winAgainst(p: Player): Boolean {
        if (damage == 0.0) return false

        val hitToKillP = ceil(p.hp / (damage - p.armor).let { if (it <= 0) 1.0 else it })
        val hitToKillThis = ceil(hp / (p.damage - armor).let { if (it <= 0) 1.0 else it })

        return hitToKillP <= hitToKillThis
    }

    companion object {
        fun playerFromItems(ite: Iterable<Item>): Player = Player(100.0, 0.0, 0.0).apply {
            for (i in ite) {
                damage += i.damage
                armor += i.armor
                cost += i.cost
            }
        }
    }
}

class Day21(override val input: String) : Day<Long>(input) {
    val attacks = mutableListOf<Item>()
    val defense = mutableListOf<Item>()
    val rings = mutableListOf<Item>()
    val boss = Player(100.0, 8.0, 2.0)
    var winners = mutableListOf<Player>()
    var losers = mutableListOf<Player>()

    init {

        input.split("\n\n").let { data ->
            data[0].split("\n").forEach {
                it.allInts().let { (cost, damage, armor) ->
                    attacks.add(Item(cost, damage.toDouble(), armor.toDouble()))
                }
            }

            data[1].split("\n").forEach {
                it.allInts().let { (cost, damage, armor) ->
                    defense.add(Item(cost, damage.toDouble(), armor.toDouble()))
                }
            }

            data[2].split("\n").forEach {
                it.allInts().let { (cost, damage, armor) ->
                    rings.add(Item(cost, damage.toDouble(), armor.toDouble()))
                }
            }
        }

        var p: Player
        val items = mutableListOf<Item>()

        attacks.forEach { attack ->
            defense.allArrangement().forEach { defenseArrangement ->
                rings.allArrangement(maxElement = 2).forEach { ringsArrangement ->
                    items.clear()
                    items.add(attack)
                    items.addAll(defenseArrangement)
                    items.addAll(ringsArrangement)

                    p = Player.playerFromItems(items)

                    if (p.winAgainst(boss)) {
                        winners.add(p)
                    } else {
                        losers.add(p)
                    }
                }
            }
        }

        winners.sortWith { p, p1 -> p.cost.compareTo(p1.cost) }
        losers.sortWith { p, p1 -> p1.cost.compareTo(p.cost) }
    }

    override fun solve1(): Long = winners.first().cost.toLong()

    override fun solve2(): Long = losers.first().cost.toLong()
}

fun main() {
    val day = Day21(readFullText("_2015/d21/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day21(readFullText("_2015/d21/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}