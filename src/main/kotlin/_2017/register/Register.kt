package _2017.register

import util.isInt
import java.lang.Exception

class Register(val instructions: List<String>) {
    val register = mutableMapOf<String, Long>()
    private var valuesSent = mutableListOf<Long>()
    private var registersWaiting = mutableListOf<String>()
    var linkedRegister: Register? = null
    var index = 0L
    var isPending = false
    var nbValueSent = 0

    init {
        register["p"] = numRegister++
    }

    fun jgz(key: String, value: String): Long {
        if (getValue(key) > 0)
            return getValue(value)

        return 1
    }

    fun set(key: String, value: String) {
        register[key] = getValue(value)
    }

    private fun pop() {
        isPending = if (linkedRegister!!.valuesSent.isEmpty()) {
            true
        } else {
            set(registersWaiting.removeAt(0), linkedRegister!!.valuesSent.removeAt(0).toString())
            registersWaiting.isNotEmpty()
        }
    }

    fun rcv(key: String) {
        registersWaiting.add(key)
        pop()
    }

    fun snd(key: String) {
        valuesSent.add(getValue(key))
        linkedRegister!!.msgSend()
        nbValueSent++
    }

    fun msgSend() {
        if (isPending) {
            pop()
        }
    }

    fun getValue(str: String): Long {
        if (str.matches(Regex("""-?\d+"""))) return str.toLong()

        return register.getOrPut(str) { 0 }
    }

    fun compareTo(left: String, right: String, operand: String): Boolean {
        return when (operand) {
            "==" -> getValue(left) == getValue(right)
            "<=" -> getValue(left) <= getValue(right)
            ">=" -> getValue(left) >= getValue(right)
            "!=" -> getValue(left) != getValue(right)
            "<" -> getValue(left) < getValue(right)
            ">" -> getValue(left) > getValue(right)
            else -> throw Exception("Operand $operand isn't supported yet")
        }
    }

    fun execute(instruction: String) {
        val data = instruction.split(" ")

        assert(data.size in 2..3)

        if (data.first() != "jgz") skip()

        when (data.first()) {
            "add", "inc" -> inc(data[1], data.last())
            "dec" -> dec(data[1], data.last())
            "mul" -> mul(data[1], data.last())
            "mod" -> mod(data[1], data.last())
            "snd" -> snd(data.last())
            "rcv" -> rcv(data.last())
            "set" -> set(data[1], data.last())
            "jgz" -> {
                index += jgz(data[1], data.last())
            }

            else -> throw Exception("${data.first()} unknown.")
        }
    }

    fun hasNext(): Boolean = index <= instructions.lastIndex

    fun next() {
        assert(hasNext())
        if (isPending) return

        execute(instructions[index.toInt()])
    }

    fun skip() {
        index += 1
    }

    fun inc(key: String, value: String) {
        register[key] = register.getOrPut(key) { 0 } + getValue(value)
    }

    fun dec(key: String, value: String) {
        register[key] = register.getOrPut(key) { 0 } - getValue(value)
    }

    fun mul(key: String, value: String) {
        register[key] = register.getOrPut(key) { 0 } * getValue(value)
    }

    fun mod(key: String, value: String) {
        register[key] = register.getOrPut(key) { 0 } % getValue(value)
    }

    companion object {
        var numRegister = 0L
    }
}