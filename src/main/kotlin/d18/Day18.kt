package d18

import readInput

const val MAP_SIZE = 25

enum class SIZE3D(val dx : Int, val dy : Int, val dz : Int) {
    X1(1,0,0), X_MINUS_1(-1,0,0),Y1(0,-1,0), Y_MINUS_1(-0,1,0),Z1(0,0,-1), Z_MINUS_1(-0,0,1)
}

data class Point3D(val x : Int, val y : Int, val z : Int) {
    /**
     * Fonction qui renvoie le nombre de voisin du Point3D courant
     */
    fun getNbNeighbors() = getNeighbors().filter { it in setRock }.size

    /**
     * Renvoie une liste contenant les voisins du point courant
     */
    fun getNeighbors() : List<Point3D> {
        val neighbors = mutableListOf<Point3D>()

        /**
         * Pour chaque direction possible dans l'espace on génère le nouveau point adjacent
         */
        SIZE3D.values().forEach {
            var newPoint = Point3D(x + it.dx, y + it.dy, z + it.dz)
            neighbors.add(newPoint)
        }

        return neighbors
    }
    override fun toString(): String = "[$x,$y,$z]"

    /**
     * Permet de savoir si le Point3D est dans la map du problème du jour, sinon la récusirvité de l'exo 2 est infini
     */
    fun inMap(): Boolean {
        return x >= -1 && y >= -1 && z >=-1 && x < MAP_SIZE && y < MAP_SIZE && z < MAP_SIZE
    }
    companion object {
        /**
         * Constante servant à générer un ID unique pour les Point
         */
        const val PRIME_NUMBER = 31

        /**
         * Set des points étant une roche, un set empêche la duplicité des éléments en lui
         */
        val setRock = mutableSetOf<Point3D>()
    }
}

fun part1(content : List<String>) : Int {
    var numberSizeExposed = 0

    for (line in content) {
        /**
         * Regex qui premet de récupérer les 3 entiers par ligne de l'input et de les transformer en un point
         */
        var p = """-?\d+""".toRegex().findAll(line).map{ it.value.toInt() }.toList().let { Point3D(it[0], it[1], it[2]) }

        Point3D.setRock.add(p)
        numberSizeExposed += 6

        /**
         * On enlève 2 fois le nombre de voisin car chaque point couvre aussi le côté opposé de son voisin
         */
        numberSizeExposed -= p.getNbNeighbors() * 2
        println(numberSizeExposed)
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
            if (it in Point3D.setRock) side3D.add(it)
            else {
                if (it !in lavaVisited && it.inMap() && it !in queue) {
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