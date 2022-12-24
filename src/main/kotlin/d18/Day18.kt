package d18

import util.readInput
import util.Point3D
const val MAP_SIZE = 25

/**
 * Set des points étant une roche, un set empêche la duplicité des éléments en lui
 */
val setRock = mutableSetOf<Point3D>()
fun Point3D.getNbNeighbors() = getNeighbors().filter { it in setRock }.size
fun inMap(p : Point3D): Boolean {
    return p.x >= -1 && p.y >= -1 && p.z >=-1 && p.x < MAP_SIZE && p.y < MAP_SIZE && p.z < MAP_SIZE
}
fun part1(content : List<String>) : Int {
    var numberSizeExposed = 0

    for (line in content) {
        /**
         * Regex qui premet de récupérer les 3 entiers par ligne de l'input et de les transformer en un point
         */
        var p = """-?\d+""".toRegex().findAll(line).map{ it.value.toInt() }.toList().let { Point3D(it[0], it[1], it[2]) }

        setRock.add(p)
        numberSizeExposed += 6

        /**
         * On enlève 2 fois le nombre de voisin car chaque point couvre aussi le côté opposé de son voisin
         */
        numberSizeExposed -= p.getNbNeighbors() * 2
    }

    return numberSizeExposed
}

fun part2(content : List<String>) : Int {
    var queue = mutableListOf(Point3D(-1,-1,-1))
    var lavaVisited = mutableSetOf<Point3D>()
    var side3D     = mutableSetOf<Point3D>()

    /**
     * Récursivité, on commence en un point initial [-1,-1,-1]
     * Pour chaque point on :
     *  - Regarde tous ces voisins :
     *     - Si voisin est une roche alors c'est un côté du cube de lave donc on l'ajoute à size3D qui représente la bordure de notre problème
     *     - Sinon c'une case ou la lave peut aller donc on l'ajoute à queue qui représente les cases où la lave peut aller mais pas encore visitée par notre algo
     *
     *   A la fin on se retrouve avec toutes les cases où la lave peut aller dans lavaVisited, et la bordure de notre obsidienne dans side3D
     */
    while (queue.isNotEmpty()) {
        val currentLava = queue.removeFirst()

        currentLava.getNeighbors().forEach {
            if (it in setRock) side3D.add(it)
            else {
                if (it !in lavaVisited && inMap(it) && it !in queue) {
                    queue.add(it)
                }
            }
        }

        lavaVisited.add(currentLava)
    }

    /**
     * Pour chaque point dans side3D on compte son nombre de voisins qui sont de la lave et on l'ajoute à la somme puis on la renvoie
     */
    return side3D.sumBy { side ->
        side.getNeighbors().sumBy { if (it in lavaVisited) 1 else 0 }
    }
}

fun main() {
    var content = readInput("d18/input") // On lit le fichier^^

    println(part1(content))
    println(part2(content))
}