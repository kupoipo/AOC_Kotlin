package _2019.d14

import util.Day
import util.firstInt
import util.readFullText
import kotlin.system.measureNanoTime

const val BASE_INGREDIENT = "ORE"

class Day14(private val isTest: Boolean, override val input: String) : Day<Long>(input) {
    data class Ingredient(val dose: Long, val name: String) {
        override fun toString(): String {
            return "$dose $name"
        }

        operator fun times(nb: Long): Ingredient = Ingredient(dose * nb, name)
    }

    data class Transformation(val ingredient: List<Ingredient>, val produce: Ingredient) {
        fun canProduce(stocks: Map<String, Long>) = ingredient.all { stocks[it.name]!! >= it.dose }
        override fun toString(): String {
            return "${ingredient.joinToString(", ")} => $produce"
        }
    }

    private fun produce(transformation: Transformation): Long {
        val previous = stocks[BASE_INGREDIENT]!!

        for (ingredient in transformation.ingredient) {
            while (stocks[ingredient.name]!! < ingredient.dose)
                produce(transformations[ingredient.name]!!)
        }

        stocks[transformation.produce.name] = stocks[transformation.produce.name]!! + transformation.produce.dose
        for (i in transformation.ingredient) {
            stocks[i.name] = stocks[i.name]!! - i.dose
        }

        return previous - stocks[BASE_INGREDIENT]!!
    }

    fun doseNeeded(ingredient: Ingredient): List<Ingredient> {
        return buildList {
            if (ingredient.name in baseIngredients) {
                for (i in transformations[ingredient.name]!!.ingredient) {
                    add(i * ingredient.dose)
                }
            }
        }
    }

    private fun strToIngredient(str: String) = Ingredient(str.firstInt().toLong(), str.trim().split(" ").last())
    private val transformations = input.split("\n").associate { line ->
        line.split("=>").let { lineSplit ->
            val produce = strToIngredient(lineSplit.last())
            produce.name to Transformation(
                lineSplit.first().split(",").map { strToIngredient(it) }, produce
            )
        }
    }

    private val stocks = transformations.keys.associateWith { 0L }.toMutableMap().also { it["ORE"] = 1_000_000_000L }
    private val baseIngredients =
        transformations.values.filter { it.ingredient.size == 1 && it.ingredient.first().name == BASE_INGREDIENT }
            .map { it.produce.name }

    override fun solve1(): Long = produce(transformations[if (isTest) "FUEL" else "FUEL"]!!)

    override fun solve2(): Long {
        return -1
    }
}

fun main() {
    val day = Day14(false, readFullText("_2019/d14/input"))
    println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + day.solve1()) } / 1e9}s")
    println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + day.solve2()) } / 1e9}s")

    println()


    for (i in 1..5) {
        try {
            val dayTest = Day14(true, readFullText("_2019/d14/test${i}"))
            println("Test $i")
            println("Temps partie 1 : ${measureNanoTime { println("Part 1 : " + dayTest.solve1()) } / 1e9}s")
            println("Temps partie 2 : ${measureNanoTime { println("Part 2 : " + dayTest.solve2()) } / 1e9}s")
            println()
        } catch (e: Exception) {
            break
        }
    }
}