package _2015.d22

import util.Day
import util.State
import util.readFullText
import kotlin.system.measureNanoTime

enum class Spell {
    NONE, MAGIC_MISSILE, DRAIN, SHIELD, POISON, RECHARGE
}

const val BOSS_DAMAGE = 10
const val MANA_PLAYER = 500
const val HP_PLAYER = 50
const val HP_BOSS = 71

var hard = 0

/*
const val BOSS_DAMAGE = 8
const val MANA_PLAYER = 250
const val HP_PLAYER = 10
const val HP_BOSS = 14*/
class StateMage(
    parent: State?,
    time: Int,
    val mana: Int,
    val armor: Int,
    val hp: Int,
    val hpBoss: Int,
    var playerTurn: Boolean,
    var shieldActive: Int = 0,
    var poisonActive: Int = 0,
    var rechargeActive: Int = 0,
    val spell: Spell = Spell.NONE
) : State(parent, time) {
    var armor_: Int
    var poison_: Int
    var mana_: Int

    init {
        if (shieldActive < 0) shieldActive = 0
        if (poisonActive < 0) poisonActive = 0
        if (rechargeActive < 0) rechargeActive = 0

        armor_ = if (shieldActive > 0) this.armor else 0
        poison_ = if (poisonActive > 0) 3 else 0
        mana_ = if (rechargeActive > 0) this.mana + 101 else this.mana
    }

    override fun isDeadLock(): Boolean {
        return mana < 53 || hp < 1
    }

    override fun nextStates(): MutableList<State> {
        val states = mutableListOf<State>()

        if (playerTurn) {
            states.add(castMagicMissile())
            states.add(castDrain())

            if (shieldActive <= 0) {
                states.add(castShield())
            }

            if (poisonActive <= 0) {
                states.add(castPoison())
            }

            if (rechargeActive <= 0) {
                states.add(castRecharge())
            }
        } else {
            states.add(bossTurn())
        }

        return states
    }

    fun bossTurn(): StateMage {
        return StateMage(
            this,
            time,
            mana_,
            armor_,
            hp - (BOSS_DAMAGE - armor_) - hard,
            hpBoss - poison_,
            true,
            shieldActive - 1,
            poisonActive - 1,
            rechargeActive - 1
        )
    }

    fun castMagicMissile(): StateMage {
        return StateMage(
            this,
            time + 53,
            mana_ - 53,
            armor_,
            hp,
            hpBoss - poison_ - 4,
            false,
            shieldActive - 1,
            poisonActive - 1,
            rechargeActive - 1,
            Spell.MAGIC_MISSILE
        )
    }

    fun castDrain(): StateMage {
        return StateMage(
            this,
            time + 73,
            mana_ - 73,
            armor_,
            hp + 2,
            hpBoss - poison_ - 2,
            false,
            shieldActive - 1,
            poisonActive - 1,
            rechargeActive - 1,
            Spell.DRAIN
        )
    }

    fun castShield(): StateMage {
        return StateMage(
            this,
            time + 113,
            mana_ - 113,
            7,
            hp,
            hpBoss - poison_,
            false,
            5,
            poisonActive - 1,
            rechargeActive - 1,
            Spell.SHIELD
        )
    }

    fun castPoison(): StateMage {
        return StateMage(
            this,
            time + 173,
            mana_ - 173,
            armor_,
            hp,
            hpBoss - 3,
            false,
            shieldActive - 1,
            5,
            rechargeActive - 1,
            Spell.POISON
        )
    }

    fun castRecharge(): StateMage {
        return StateMage(
            this,
            time + 229,
            mana_ - 229 + 101,
            armor_,
            hp,
            hpBoss - poison_,
            false,
            shieldActive - 1,
            poisonActive - 1,
            4,
            Spell.RECHARGE
        )
    }

    override fun isGoal(): Boolean {
        return hpBoss <= 0
    }

    override fun equals(other: Any?): Boolean {
        val o = other as StateMage
        return o.hp == hp && o.hpBoss == hpBoss && o.poisonActive == poisonActive && o.shieldActive == shieldActive && armor == o.armor && mana == o.mana && rechargeActive == o.rechargeActive
    }

    override fun toString(): String {
        return buildString {
            if (this@StateMage.playerTurn) {
                append("-- Player turn --\n")
            } else {
                append("-- Boss turn --\n")
            }

            append("- Player has $hp hit points, $armor armor, $mana mana.\n")
            append("- Boss has $hpBoss hit point.\n")

            if (poisonActive > 0) append("Poison deals 3 damage; its timer is now $poisonActive.\n")
            if (shieldActive > 0) append("Shield's timer is now 5.\n")
            if (rechargeActive > 0) append("Recharge provides 101 mana; its timer is now $rechargeActive.\n")

            if (playerTurn) {
                append("Boss attacks for $BOSS_DAMAGE - $armor = ${BOSS_DAMAGE - armor} damage!\n")
            } else {
                append("Player casts $spell\n")
            }

            append("")
        }
    }
}

class Day22(override val input: String) : Day<Long>(input) {
    override fun solve1(): Long {
        /**
         * Work without giving the first spell's to cast, but it's quite obvious that we need to launch every spell that last in time in first place,
         * so we gain 1s of calculus time by writing them instead of trying to start the fight with fireball or drain
         */
        val init =
            StateMage(null, 0, MANA_PLAYER, 0, HP_PLAYER, HP_BOSS, true).castPoison().bossTurn().castShield().bossTurn()
                .castRecharge().bossTurn()

        val path = State.shortestPastFrom(init)?.rebuildPath(true)

        return path!!.first()!!.time.toLong()
    }

    override fun solve2(): Long {
        val init = StateMage(null, 0, MANA_PLAYER, 0, HP_PLAYER - 1, HP_BOSS, true)

        hard = 1
        val path = State.shortestPastFrom(init)?.rebuildPath(false)

        return path!!.first()!!.time.toLong()
    }
}

fun main() {
    val day = Day22(readFullText("_2015/d22/input"))

    val t1 = measureNanoTime { println("Part 1 : " + day.solve1()) }
    println("Temps partie 1 : ${t1 / 1e9}s")

    val t2 = measureNanoTime { println("Part 2 : " + day.solve2()) }
    println("Temps partie 2 : ${t2 / 1e9}s")

    println()
    println()

    val dayTest = Day22(readFullText("_2015/d22/test"))
    val t1Test = measureNanoTime { println("TEST - Part 1 : " + dayTest.solve1()) }
    println("Temps partie 1 : ${t1Test / 1e9}s")

    val t2Test = measureNanoTime { println("TEST - Part 2 : " + dayTest.solve2()) }
    println("Temps partie 2 : ${t2Test / 1e9}s")
}