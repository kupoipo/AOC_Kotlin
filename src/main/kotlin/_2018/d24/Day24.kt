package _2018.d24

import util.Day
import util.allLong
import util.findAllMatch
import util.readFullText
import java.lang.Exception
import kotlin.system.measureNanoTime

class Day24(override val input: String) : Day<Long>(input) {

    class Army {
        var groups = mutableListOf<Group>()
    }

    enum class AttackType {
        FIRE, COLD, SLASHING, BLUDGEONING, RADIATION;
    }

    data class Group(
        val group: Int,
        var nbMembers: Long,
        val hp: Long,
        var attack: Long,
        private val type: AttackType,
        val initiative: Long,
        private val weakness: List<AttackType>,
        private val immunities: List<AttackType>
    ) {
        val nbMembersMax = nbMembers
        val attackOrigin = attack
        fun isAlive() = nbMembers > 0
        fun hp() = hp
        fun power() = nbMembers * attack

        override fun toString(): String {
            if (!isAlive()) return "Dead"

            return buildString {
                append("$group : $nbMembers : $hp HP; $attack ATT")
            }
        }

        fun damageFrom(g: Group): Long {
            if (immunities.contains(g.type)) return 0L
            if (weakness.contains(g.type)) return g.power() * 2
            return g.power()
        }

        fun attack(attacked: Group) {
            var damageLeft = attacked.damageFrom(this)
            val enemyHP = attacked.hp()

            while (damageLeft > enemyHP) {
                attacked.nbMembers--
                damageLeft -= enemyHP
            }
        }
    }

    init {
        reset()
        val groupImmune = input.split("\n").drop(1).takeWhile { it.isNotEmpty() } to immune
        val groupInfection = input.split("\n").drop(groupImmune.first.size + 3) to infection

        for ((lines, army) in listOf(groupInfection, groupImmune)) {
            for (line in lines) {
                val longs = line.allLong()
                val attack =
                    AttackType.valueOf(line.findAllMatch("[a-z]* damage").first().takeWhile { it != ' ' }.uppercase())
                val weakness = mutableListOf<AttackType>()
                val immunities = mutableListOf<AttackType>()

                if (line.findAllMatch("""(weak|immune)""").isNotEmpty()) {
                    val subLine = line.substring(line.indexOf("(") + 1, line.indexOf(")"))
                    subLine.split(";").forEach { immuneOrWeak ->
                        val list = if (immuneOrWeak.contains("immune")) immunities else weakness

                        immuneOrWeak.findAllMatch("[a-z]*(?:(,|\$))").filter { it.isNotEmpty() }.forEach {
                            list.add(AttackType.valueOf(it.let { if (it.last() == ',') it.dropLast(1) else it }
                                .uppercase()))
                        }
                    }
                }

                val g = Group(
                    if (army == immune) IMMUNE else INFECTION,
                    longs.first(),
                    longs[1],
                    longs[2],
                    attack,
                    longs.last(),
                    weakness,
                    immunities
                )

                army.groups.add(g)
                all.groups.add(g)
            }
        }
    }

    private fun targeting(): MutableList<Pair<Group, Group>> {
        val res = mutableListOf<Pair<Group, Group>>()
        val allOrdered = all.groups.filter { it.isAlive() }.sortedWith { g1, g2 ->
            if (g1.power() != g2.power()) -g1.power().compareTo(g2.power())
            else -g1.initiative.compareTo(g2.initiative)
        }

        val available = allOrdered.toMutableSet()

        for (g in allOrdered) {
            val enemy =
                (if (g.group == IMMUNE) infection else immune).groups.filter {
                    it.isAlive() && available.contains(it) && it.damageFrom(
                        g
                    ) > 0
                }

            if (enemy.isEmpty()) continue

            val list = enemy.sortedWith { g1, g2 ->
                if (g1.damageFrom(g) != g2.damageFrom(g)) -g1.damageFrom(g).compareTo(g2.damageFrom(g))
                else if (g1.power() != g2.power()) -g1.power().compareTo(g2.power())
                else g1.initiative.compareTo(g2.initiative)
            }

            val attacked = list.first()

            res.add(g to attacked)
            available.remove(attacked)
        }

        return res
    }

    private fun attack(fights: MutableList<Pair<Group, Group>>) {
        fights.sortByDescending {
            it.first.initiative
        }
        for (fight in fights) {
            if (!fight.first.isAlive()) continue

            fight.first.attack(fight.second)
        }
    }

    private fun simulate(immuneBoost: Long = -1L): Army {
        if (immuneBoost != -1L) {
            immune.groups.forEach { it.attack = it.attackOrigin + immuneBoost }
        }

        all.groups.forEach { it.nbMembers = it.nbMembersMax }

        var lastInfection = infection.groups.sumOf { it.nbMembers }
        var lastImmune = immune.groups.sumOf { it.nbMembers }

        while (infection.groups.any { it.isAlive() } && immune.groups.any { it.isAlive() }) {
            attack(targeting())

            if (lastInfection == infection.groups.sumOf { it.nbMembers } &&
                lastImmune == immune.groups.sumOf { it.nbMembers }) return infection

            lastInfection = infection.groups.sumOf { it.nbMembers }
            lastImmune = immune.groups.sumOf { it.nbMembers }
        }
        return if (immune.groups.any { it.isAlive() }) immune else infection
    }

    override fun solve1(): Long = simulate().groups.filter { it.isAlive() }.sumOf { it.nbMembers }

    override fun solve2(): Long {
        var currentBoost = 15L

        while (simulate(currentBoost) != immune) {
            currentBoost++
        }

        println(immune.groups)
        println(currentBoost)

        return immune.groups.filter { it.isAlive() }.sumOf { it.nbMembers }
    }

    companion object {
        const val IMMUNE = 0
        const val INFECTION = 1
        private var all: Army = Army()
        private var infection: Army = Army()
        private var immune: Army = Army()

        fun reset() {
            all.groups.clear()
            infection.groups.clear()
            immune.groups.clear()
        }
    }
}


fun main() {
    val day = Day24(readFullText("_2018/d24/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day24(readFullText("_2018/d24/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}