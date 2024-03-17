package _2018.register

class Register2018 {
    private val hmNameToFunction = mutableMapOf(
        "addr" to ::addr,
        "addi" to ::addi,
        "mulr" to ::mulr,
        "muli" to ::muli,
        "banr" to ::banr,
        "bani" to ::bani,
        "borr" to ::borr,
        "bori" to ::bori,
        "setr" to ::setr,
        "seti" to ::seti,
        "gtri" to ::gtri,
        "gtir" to ::gtir,
        "gtrr" to ::gtrr,
        "eqir" to ::eqir,
        "eqri" to ::eqri,
        "eqrr" to ::eqrr
    )
    val register = mutableMapOf<String, Int>()

    fun addr(a: String, b: String, c: String) {
        register[c] = get(a) + get(b)
    }

    fun addi(a: String, b: String, c: String) {
        register[c] = get(a) + b.toInt()
    }

    fun mulr(a: String, b: String, c: String) {
        register[c] = get(a) * get(b)
    }

    fun muli(a: String, b: String, c: String) {
        register[c] = get(a) * b.toInt()
    }

    fun banr(a: String, b: String, c: String) {
        register[c] = get(a) and get(b)
    }

    fun bani(a: String, b: String, c: String) {
        register[c] = get(a) and b.toInt()
    }

    fun borr(a: String, b: String, c: String) {
        register[c] = get(a) or get(b)
    }

    fun bori(a: String, b: String, c: String) {
        register[c] = get(a) or b.toInt()
    }

    fun setr(a: String, b: String, c: String) {
        register[c] = get(a)
    }

    fun seti(a: String, b: String, c: String) {
        register[c] = a.toInt()
    }

    fun gtir(a: String, b: String, c: String) {
        register[c] = if (a.toInt() > get(b)) 1 else 0
    }

    fun gtri(a: String, b: String, c: String) {
        register[c] = if (get(a) > b.toInt()) 1 else 0
    }

    fun gtrr(a: String, b: String, c: String) {
        register[c] = if (get(a) > get(b)) 1 else 0
    }

    fun eqir(a: String, b: String, c: String) {
        register[c] = if (a.toInt() == get(b)) 1 else 0
    }

    fun eqri(a: String, b: String, c: String) {
        register[c] = if (get(a) == b.toInt()) 1 else 0
    }

    fun eqrr(a: String, b: String, c: String) {
        register[c] = if (get(a) == get(b)) 1 else 0
    }

    fun getFunction(name: String) : (String, String, String) -> (Unit) {
        return hmNameToFunction[name]!!
    }

    operator fun get(x: String) = register.getOrPut(x) { 0 }
    operator fun set(key: String, value: Int) {
        register[key] = value
    }

    fun clear() {
        register.clear()
    }

}