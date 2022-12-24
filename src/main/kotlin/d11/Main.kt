package d11

import util.readInput

var N : Int = 1
var myJungl = Jungl()

class Monkey {
    var items = mutableListOf<Long>()
    var operation : Pair<Char, String>? = null
    var divisible = 1
    var trueTo = 0; var falseTo = 0
    var inspectedItems : Long = 0

    fun newWorry(item : Long) : Long {
        inspectedItems++
        var itemBy = if (operation!!.second.equals("old")) item else operation!!.second.toLong()

        return when (operation?.first) {
            ('+') -> (item% N + itemBy% N) % N
            ('*') -> (item% N * itemBy% N) % N
            else -> {
                item
            }
        }
    }

    override fun toString() : String {
        return "\tStartings items : $items\n\tOperation : new = old $operation\n\tTest: divisible by $divisible\n\t\tIf true : throw to monkey $trueTo \n\t\tIf false : throw to monkey $falseTo"
    }
}



class Jungl {
    fun show() {
        for ((index, m) in bestiary.withIndex()) {
            println("Monkey $index: inspected items :" + m.inspectedItems)
            println(m)
        }
    }

    var bestiary = mutableListOf<Monkey>()
}

fun part1() {
    var nb_round = 10000

    repeat(nb_round) {
        for (monkey in myJungl.bestiary) {
            while (monkey.items.isNotEmpty()) {
                var item = monkey.items.removeFirst()
                item = monkey.newWorry(item)

                if (item%monkey.divisible == 0L) {
                    myJungl.bestiary[monkey.trueTo].items.add(item)
                } else {
                    myJungl.bestiary[monkey.falseTo].items.add(item)
                }

            }
        }
    }

    myJungl.bestiary.sortByDescending { it.inspectedItems }
    myJungl.show()
    println(myJungl.bestiary[0].inspectedItems * myJungl.bestiary[1].inspectedItems)
}

fun parseJungl(content : List<String>) {
    var i = 1

    while (i < content.size) {
        var monkeyTemp = Monkey()

        content[i].substringAfter("items: ").split(",").forEach {
            monkeyTemp.items.add(it.replace(" ", "").toLong())
        }
        i++

        monkeyTemp.operation = Pair(content[i].substringAfter("new = old ").substringBefore(" ")[0], content[i].substringAfter("new = old ").substringAfter(" "));
        i++

        monkeyTemp.divisible = content[i].substringAfter("by ").toInt()
        i++

        monkeyTemp.trueTo = content[i].substringAfter("monkey ").toInt()
        i++

        monkeyTemp.falseTo = content[i].substringAfter("monkey ").toInt()
        i+=3

        myJungl.bestiary.add(monkeyTemp)
    }

    N = myJungl.bestiary.map { it.divisible }.reduce(Int::times) // 96577 -> test
}

fun main() {
    parseJungl(readInput("d11/input"))
    part1()
}