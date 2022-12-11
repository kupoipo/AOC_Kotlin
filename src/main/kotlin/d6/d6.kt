package d6

import readInput

fun part1(content : String) : Int {
    var letters = mutableListOf<Char>()

    letters.addAll(content.subSequence(0, 14).toList())

    for (i in 14  until content.length) {
        if (letters.toSet().size == 14 ) return i

        letters.add(content[i])
        letters.removeAt(0)
    }

    return -1
}

fun main() {
  //  println(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    println(part1(readInput("d6/input")[0]))
}