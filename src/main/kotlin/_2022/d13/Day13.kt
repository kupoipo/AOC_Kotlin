package _2022.d13
import util.readInput

fun parseLine(s: String): MutableList<Any> {
    var l : MutableList<Any> = MutableList<Any>(0) { }
    var temp = MutableList<MutableList<Any>> (0 ) { mutableListOf() }
    var underList : MutableList<Any> = l
    var number : String = ""

    for (c in s.substring(1, s.length)) {
        when (c) {
            '[' -> {
                var newList = mutableListOf<Any>()

                underList.add(newList)
                temp.add(newList)
                underList = newList
            }

            ']' -> {
                if (number != "") {
                    underList.add(number.toInt())
                    number = ""
                }

                if (temp.isNotEmpty()) temp.removeLast()

                underList = if (temp.isNotEmpty()) temp.last() else l
            }

            ',' -> {
                if (number != "") {
                    underList.add(number.toInt())
                    number = ""
                }
            }

            else -> {
                number += c
            }
        }
    }

    return l
}

fun customCompare(l1: MutableList<Any>, l2: MutableList<Any>) : Int {
    for (i in l1.indices) {
        if (i >= l2.size) return 1

        var left  = l1[i]
        var right = l2[i]

        if (left is Int && right is Int) {
            if (right < left)
                return 1
            else if (right > left)
                return -1

        } else if (left is MutableList<*> && right is MutableList<*>) {
            var res =  customCompare(left as MutableList<Any>, right as MutableList<Any>)
            if (res != 0)
                return res
        } else {
            if (right is Int) {
                right = listOf(right)
            } else {
                left = listOf(left)
            }
            return customCompare(left as MutableList<Any>, right as MutableList<Any>)
        }
    }

    if (l1.size == l2.size )
        return 0

    return -1
}






fun part1(content : List<String>) : String {
    var maxIndice = 0

    for (i in content.indices step 3) {
        var l1 = parseLine(content[i])
        var l2 = parseLine(content[i+1])

        if (customCompare(l1,l2) == -1) {
            maxIndice += (i/3 + 1)
        }
    }
    return maxIndice.toString()
}

fun part2(content : List<String>) : String {
    var maxIndice = 1
    var allLine = mutableListOf<MutableList<Any>>()

    for (i in content.indices step 3) {
        allLine.add(parseLine(content[i]))
        allLine.add(parseLine(content[i+1]))
    }

    allLine.add(mutableListOf(mutableListOf(2)))
    allLine.add(mutableListOf(mutableListOf(6)))

    allLine.sortWith <MutableList<Any>> { l1, l2 -> customCompare(l1, l2) }
    println(allLine)

    for ((index, line) in allLine.withIndex()) {

        if (line.size == 1) {
            try {
                var l : MutableList<Any> = line[0] as MutableList<Any>

                if (l.size == 1 && l[0] == 2){
                    maxIndice *= (index+1)
                }
            } catch (e : Exception) {

            }
        }

        if (line.size == 1) {
            try {
                var l : MutableList<Any> = line[0] as MutableList<Any>

                if (l.size == 1 && l[0] == 6){
                    maxIndice *= (index+1)
                }
            } catch (e : Exception) {

            }
        }
    }

    return maxIndice.toString()
}

fun main() {
    var content = readInput("d13/input")


    println("Solution 1 : " + part1(content))

    println(part2(content))
}