package _2022.d19

enum class TYPE {
    NONE, ORE, CLAY, OBSIDIAN, GEODE
}

enum class ROBOT(val init : TYPE, val resources : List<TYPE>) {
    R_ORE(TYPE.ORE, listOf(TYPE.ORE)), R_CLAY(TYPE.CLAY, listOf(TYPE.ORE)), R_OBSIDIAN(TYPE.OBSIDIAN, listOf(TYPE.ORE, TYPE.CLAY)), R_GEODE(TYPE.GEODE, listOf(TYPE.ORE, TYPE.OBSIDIAN))
}

class Production(var ore : Int, var clay : Int, var obsi : Int, var geode : Int) {
    operator fun plus(other : Production): Production  = Production(ore + other.ore, clay + other.clay, obsi + other.obsi, geode + other.geode)
    operator fun minus(other : Production): Production  = Production(ore - other.ore, clay - other.clay, obsi - other.obsi, geode - other.geode)


}

data class BluePrint(val cost : Map<TYPE, Map<TYPE, Int>>) {
    var resources = mutableMapOf(TYPE.ORE to 0, TYPE.CLAY to 0, TYPE.OBSIDIAN to 0, TYPE.GEODE to 0)
    var robots    = mutableMapOf(TYPE.ORE to 1, TYPE.CLAY to 0, TYPE.OBSIDIAN to 0, TYPE.GEODE to 0)
    var robotToProduce : TYPE = TYPE.NONE

    fun robotCreable() : MutableList<TYPE> {
        var res = mutableListOf(TYPE.NONE)

        for (robot in ROBOT.values()) {
            var creable = true
            for (resNecessary in robot.resources) {
                if (cost[robot.init]!![resNecessary]!! > resources[resNecessary]!!) {
                    creable = false
                }
            }

            if (creable) res.add(0, robot.init)
        }



        return res
    }

    fun createRobot(type : TYPE) {
        if (type == TYPE.NONE) return

        robots[type] = robots[type]!! + 1
        for (cout in cost[type]!!) {
            resources[cout.key] = resources[cout.key]!! - cout.value
        }
    }

    fun removeProduction(prod : Production) {
        resources[TYPE.ORE] = resources[TYPE.ORE]!! - prod.ore
        resources[TYPE.CLAY] = resources[TYPE.CLAY]!! - prod.clay
        resources[TYPE.OBSIDIAN] = resources[TYPE.OBSIDIAN]!! - prod.obsi
        resources[TYPE.GEODE] = resources[TYPE.GEODE]!! - prod.geode
    }

    fun removeRobot(robot: TYPE) {
        if (robot == TYPE.NONE) return

        robots[robot] = robots[robot]!! - 1

        for (fabric in cost[robot]!!) {
            resources[fabric.key] = resources[fabric.key]!! + fabric.value
        }

    }

    fun produce() : Production {
        var produc = Production(0,0,0,0)

        robots.forEach { (robot, nb) ->
            resources[robot] = resources[robot]!! + nb
            if (robot == TYPE.ORE) {
                produc.ore = nb
            }
            if (robot == TYPE.CLAY) {
                produc.clay = nb
            }
            if (robot == TYPE.OBSIDIAN) {
                produc.obsi = nb
            }
            if (robot == TYPE.GEODE) {
                produc.geode = nb
            }

        }
        if (robotToProduce != TYPE.NONE) {
            createRobot(robotToProduce)
            robotToProduce = TYPE.NONE
        }

        return produc
    }

    override fun toString(): String {
        return "Each ore robot costs " + cost[TYPE.ORE]!![TYPE.ORE] + " ore. " +
                "Each clay robot costs " + cost[TYPE.CLAY]!![TYPE.ORE] + " ore. " +
                "Each obsidian robot costs " + cost[TYPE.OBSIDIAN]!![TYPE.ORE] + " ore and " + cost[TYPE.OBSIDIAN]!![TYPE.CLAY] + " clay. " +
                "Each geode robot costs "+ cost[TYPE.GEODE]!![TYPE.ORE]  +" ore and " +  cost[TYPE.GEODE]!![TYPE.OBSIDIAN]  +" obsidian.\n"
    }


}