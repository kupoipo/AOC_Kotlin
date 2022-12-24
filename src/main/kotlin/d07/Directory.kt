package d7

import util.readInput

const val FILE = 'F'
const val DIRECTORY = 'D'

interface Component {
    fun getSize() : Int
}

class File(val name : String, private val size : Int) : Component {
    override fun getSize(): Int {
        return size
    }
}

class Directory(private val name: String, val father : Directory?) : Component {
    var elements = mutableMapOf<String, Component?>()

    fun addDirectory(dir : Directory?) {
        if (elements.containsValue(dir)) return

        elements[dir!!.name] = dir
    }

    fun addFile(file : File?) {
        if (elements.containsValue(file)) return

        elements[FILE + file!!.name] = file
    }

    override fun getSize(): Int {
        var size = 0

        elements.forEach {
            size += it.value!!.getSize()
        }

        return size
    }

    override fun toString() : String {
        return name + "  " + getSize()
    }
}
var sum = 0
var list = mutableMapOf<Int, Directory>()

fun sortDirectory(dir : Directory?) {
    var size = dir!!.getSize()

    list[size] = dir

    dir.elements.forEach {
        if (it.value is Directory) {
            sortDirectory(it.value as Directory)
        }
    }
}

fun part1(content : List<String>) {
    var currentDir : Directory? = Directory("/", null)
    val racine = currentDir

    for (line in content) {
        if (line.startsWith("\$ cd")) {
            var dirName = line.substring(5)

            currentDir = when (dirName) {
                ".." -> currentDir!!.father
                "/" -> racine
                else -> {
                    currentDir!!.elements[dirName] as Directory?
                }
            }
        } else {
            // After listing element
            if (!line.startsWith("\$ ls")) {

                // Adding a directory
                if (line.startsWith("dir")) {
                    var dirName = line.substring(4)

                    if (!currentDir!!.elements.containsKey(dirName)) {
                        currentDir?.addDirectory(Directory(dirName, currentDir))
                    }
                }
                // Adding a file
                else {
                    var size = line.substring(0, line.indexOf(" ")).toInt()
                    var name = line.substring(line.indexOf(" ") + 1)

                    currentDir?.addFile(File(name, size))
                }
            }
        }
    }

    var sizeNeeded = 30000000 - (70000000 - racine!!.getSize())
    println(sizeNeeded)

    sortDirectory(racine)

    list.toSortedMap().forEach {
        if (it.key > sizeNeeded) {
            println(it.key)
            return
        }
    }
}

fun main() {
    part1(readInput("d7/input"))
}