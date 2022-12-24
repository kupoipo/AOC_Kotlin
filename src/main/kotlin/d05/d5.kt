package d5

import util.readInput
import java.util.Stack

fun part1(content : List<String>) : String {
    var piles : MutableList<Stack<Char>> =  arrayListOf()

    var i = 0;

    do {
       for (j in 0..content[i].length step 4) {
           if (content[i].substring(j ,j+3) != "   ") {
               while (piles.size <= j/4) {
                   piles.add(Stack<Char>())

               }
               piles[j/4].add(0, content[i][j+1])
           }
       }
        i++
    } while (content[i].substring(1,2) != "1")

    i+=2

    for (i in i until content.size) {
        var nb = content[i].substring(5, content[i].indexOf("from") - 1).toInt()
        var from = content[i].substring( content[i].indexOf("from") + 5, content[i].indexOf("to") - 1).toInt()
        var to = content[i].substring( content[i].indexOf("to") + 3).toInt()

        var nbt =  piles[from-1].size - nb

        for (k in 1..nb) {
            piles[to-1].add(piles[from-1].removeAt(nbt))
        }
    }
    piles.forEach {
        println(it)
    }

    return "";
}

fun main()  {
    println(part1(readInput("d5/input")))
}