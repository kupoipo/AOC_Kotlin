package _2015.d21

import util.Day
import util.allBinaryPossibility
import util.allInts
import util.readFullText
import java.lang.Exception
import kotlin.math.pow
import kotlin.system.measureNanoTime

enum class Type {
    ATTACK, DEFENSE, RING
}

data class Item(val cost: Int, val damage: Int, val armor: Int, val type: Type)

class Player(var hp: Int = 100, var damage: Int = 0, var armor: Int = 0, var cost: Int = 0) {
    var items = mutableListOf<Item>()
    fun winAgainst(p: Player): Boolean {
        if (damage == 0) return false

        val hitToKillP = p.hp / (damage - p.armor).let { if (it <= 0) 1 else it }
        val hitToKillThis = hp / (p.damage - armor).let { if (it <= 0) 1 else it }

        return hitToKillP <= hitToKillThis
    }

    override fun toString(): String {
        return "$hp - $damage - $armor - $cost"
    }

    companion object {
        fun playerFromItems(ite: Iterable<Item>): Player = Player(100, 0, 0).apply {
            for (i in ite) {
                damage += i.damage
                armor += i.armor
                cost += i.cost
                items.add(i)
            }
        }
    }
}

class Day21(override val input: String) : Day<Long>(input) {
    val attacks = mutableListOf<Item>()
    val defense = mutableListOf<Item>()
    val rings = mutableListOf<Item>()
    val boss = Player(100, 8, 2)

    init {
        input.split("\n\n").let { data ->
            data[0].split("\n").forEach {
                it.allInts().let { (cost, damage, armor) ->
                    attacks.add(Item(cost, damage, armor, Type.ATTACK))
                }
            }

            data[1].split("\n").forEach {
                it.allInts().let { (cost, damage, armor) ->
                    defense.add(Item(cost, damage, armor, Type.DEFENSE))
                }
            }

            data[2].split("\n").forEach {
                it.allInts().let { (cost, damage, armor) ->
                    rings.add(Item(cost, damage, armor, Type.RING))
                }
            }
        }
    }

    override fun solve1(): Long {
        var NB=0
        var p: Player
        val items = mutableListOf<Item>()
        var winners = mutableListOf<Player>()

        attacks.forEach { attack ->
            (2.0.pow(defense.size)).toInt().allBinaryPossibility().forEach { defenseBinary ->
                for (i1 in -1 until rings.size) {
                    for (i2 in i1 until rings.size) {
                        items.clear()
                        items.add(attack)
                        items.addAll(defense.filterIndexed { index, _ -> defenseBinary[index] == '1' })

                        if (i1 != -1) {
                            val r1 = rings[i1]
                            val r2 = rings[i2]

                            items.add(r1)

                            if (r1 != r2) {
                                items.add(r2)
                            }
                        }

                        NB++

                        p = Player.playerFromItems(items)

                        if (p.winAgainst(boss)) {
                            winners.add(p)
                        }

                        if (i1 == -1) break
                    }
                }

            }
        }

        winners.sortWith({ p, p1 -> p.cost.compareTo(p1.cost) })

        for (winner in winners) {
            println(winner)
        }
        println(NB)

        return 0
    }

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    println(32.allBinaryPossibility())

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