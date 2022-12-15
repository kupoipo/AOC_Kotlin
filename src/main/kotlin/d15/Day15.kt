package d15

import kotlin.math.max
import kotlin.math.min
import readInput
import Point

import overlaps
import java.lang.StrictMath.abs

var sensorsBeacons = mutableListOf<Pair<Point, Point>>()

fun getFilledPositionsInRow(lig : Int) : List<IntRange> {
    var res = mutableListOf<IntRange>()

    sensorsBeacons.forEach{ (sensor, beacon) ->
        run {
            var dist = sensor.manhattan(beacon)
            var ecart = abs(lig - sensor.y)

            if (ecart < dist) {
                val dx = dist - ecart
                res.add(-dx + sensor.x..dx + sensor.x)
            }
        }
    }

    return res
}

fun part2() : String {
    for (lig in 0..4_000_000) {
        var filledPos = getFilledPositionsInRow(lig).sortedBy { it.first }

        var bigRange = filledPos[0]

        for (range in filledPos.drop(1)) {
            if (range overlaps bigRange) {
                bigRange = min(bigRange.first, range.first) .. max(bigRange.last, range.last)
            } else {
                return (bigRange.last * 4_000_000L + lig.toLong()).toString()
            }
        }
    }

    return "Not found"
}
fun main() {
    var content = readInput("d15/input")

    readData(content)
    println(part2())
}
fun readData(content: List<String>) {
    content.forEach {
        var ints = """-?\d+""".toRegex().findAll(it).map{ it.value.toInt() }.toList()
        var sensor = Point(ints[0], ints[1])
        var beacons = Point(ints[2],ints[3])

        sensorsBeacons.add(sensor to beacons)
    }
}
