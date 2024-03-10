package _2017.register

class Register(val instructions: List<String>) {
    val register = mutableMapOf<String, Long>()
    val nbInstructions = mutableMapOf<String, Long>()
    private var valuesSent = mutableListOf<Long>()
    private var registersWaiting = mutableListOf<String>()
    var linkedRegister: Register? = null
    var index = 0L
    var isPending = false
    var nbValueSent = 0

    init {
        register["p"] = numRegister++
    }

    private fun jgz(key: String, value: String): Long {
        if (getValue(key) > 0)
            return getValue(value)

        return 1
    }

    operator fun set(key: String, value: String) {
        register[key] = getValue(value)
    }

    operator fun set(key: String, value: Long) {
        register[key] = value
    }

    private fun pop() {
        isPending = if (linkedRegister!!.valuesSent.isEmpty()) {
            true
        } else {
            set(registersWaiting.removeAt(0), linkedRegister!!.valuesSent.removeAt(0).toString())
            registersWaiting.isNotEmpty()
        }
    }

    private fun rcv(key: String) {
        registersWaiting.add(key)
        pop()
    }

    private fun snd(key: String) {
        valuesSent.add(getValue(key))
        linkedRegister!!.msgSend()
        nbValueSent++
    }

    private fun msgSend() {
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

    private fun instructionImpactIndex(first: String): Boolean {
        return listOf("jgz", "jnz").contains(first)
    }

    private fun execute(instruction: String) {
        val data = instruction.split(" ")

        assert(data.size in 2..3)

        nbInstructions[data.first()] = nbInstructions.getOrPut(data.first()) { 0 } + 1
        if (!instructionImpactIndex(data.first())) skip()

        when (data.first()) {
            "add", "inc" -> inc(data[1], data.last())
            "dec", "sub" -> dec(data[1], data.last())
            "mul" -> mul(data[1], data.last())
            "mod" -> mod(data[1], data.last())
            "snd" -> snd(data.last())
            "rcv" -> rcv(data.last())
            "set" -> set(data[1], data.last())
            "jgz" -> {
                index += jgz(data[1], data.last())
            }
            "jnz" -> jnz(data[1], data.last())

            else -> throw Exception("${data.first()} unknown.")
        }
    }

    fun hasNext(): Boolean = index <= instructions.lastIndex

    fun next() {
        assert(hasNext())
        if (isPending) return

        execute(instructions[index.toInt()])
    }

    private fun skip() {
        index += 1
    }

    /**
     * Add on day 23, jumps with an offset of value if the key is != 0
     */
    private fun jnz(key: String, value: String) {
        index += if (getValue(key) == 0L) 1 else getValue(value)
    }
    fun inc(key: String, value: String) {
        register[key] = getValue(key) + getValue(value)
    }

    fun dec(key: String, value: String) {
        register[key] = getValue(key) - getValue(value)
    }

    private fun mul(key: String, value: String) {
        register[key] = getValue(key) * getValue(value)
    }

    private fun mod(key: String, value: String) {
        register[key] = getValue(key) % getValue(value)
    }

    companion object {
        var numRegister = 0L
    }

    override fun toString(): String {
        return register.toString()
    }

    operator fun get(s: String): Long {
        return getValue(s)
    }
}