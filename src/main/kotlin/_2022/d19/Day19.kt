package _2022.d19
import util.allInts
import util.readInput

var blueprints = mutableListOf<BluePrint>()
const val TIME_MAX = 25

fun solve(printer : BluePrint) {
    solveRecu(printer, 1, 2,TYPE.NONE)
}

var MAX_GEODE = 0

fun solveRecu(printer: BluePrint, time: Int, nbRobot : Int, r : TYPE): Production{
    var realTime = time
    //println("Robot : $r, Before production - Resources : " + printer.resources + " - Robots : " + printer.robots + " robots to exit : $nbRobot")
    var produc = Production(0,0,0,0)

    do {
        produc += printer.produce()
        realTime += 1
    } while (printer.robotCreable().size < nbRobot && realTime < TIME_MAX)

   // println("realTime $time - $realTime, After production :\nResources : " + printer.resources + "\nRobots : " + printer.robots + "\n ")
    if (realTime == TIME_MAX) {

        if (printer.resources[TYPE.GEODE]!! > MAX_GEODE)
            MAX_GEODE = printer.resources[TYPE.GEODE]!!
        return produc
    }

    var creable = printer.robotCreable();

    if (creable.contains(TYPE.OBSIDIAN) || creable.contains(TYPE.GEODE)) {
    //    creable.remove(TYPE.NONE)
        creable.remove(TYPE.ORE)
        creable.remove(TYPE.CLAY)
    }

    for (robot in creable) {
        printer.robotToProduce = robot
        var res = solveRecu(printer, realTime, if (robot == TYPE.NONE) nbRobot + 1 else 2, robot)
        printer.removeProduction(res)
        printer.removeRobot(robot)

    }
    return produc
}

fun part1() : Int {
    solve(blueprints[1])

    return MAX_GEODE
}

fun part2() : String {

    return ""
}

fun main() {
    var content = readInput("d19/test")

    initBluePrints(content)
    println(part1())
    println(part2())
}

fun initBluePrints(content: List<String>) {
    content.forEach{ line -> run {
            blueprints.add(line.allInts().let {
                BluePrint(
                    mapOf(
                        TYPE.ORE to mapOf(TYPE.ORE to it[1]),
                        TYPE.CLAY to  mapOf(TYPE.ORE to it[2]),
                        TYPE.OBSIDIAN to  mapOf(TYPE.ORE to it[3], TYPE.CLAY to it[4]),
                        TYPE.GEODE to  mapOf(TYPE.ORE to it[5], TYPE.OBSIDIAN to it[6])
                    )
                )
            })
        }
    }
}
