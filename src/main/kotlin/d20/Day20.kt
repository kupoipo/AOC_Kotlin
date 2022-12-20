package d20
import readInput
import java.util.LinkedList

var li : ArrayList<Long> = ArrayList<Long>()
var size : Int = 0

fun part2() : Long  {
    var llIndex : LinkedList<Int> = LinkedList<Int>( List (size) { it } )

    repeat(10) {
        for (n in li.withIndex()) {
            var index = llIndex.indexOf(n.index);

            var newIndex = (n.value % (size - 1)) + index

            newIndex = if (newIndex <= 0 && n.value != 0L) (newIndex % size) + size - 1 else newIndex % (size - 1)

            llIndex.removeAt(index)
            llIndex.add(newIndex.toInt(), n.index)

        }
    }

    var index = llIndex.indexOf(li.indexOf(0))


    return List(3){li[llIndex[(index + (it+1) * 1000) % size]]}.sum()
}

fun main() {
    var content = readInput("d20/input")
    li = ArrayList(content.map { it -> it.toLong() * 811_589_153 })
    size = content.size

    println(part2())
}