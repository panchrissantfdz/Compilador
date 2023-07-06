package org.Compiladores.Kotlin

object TabladeSimbolos {
    val tablaSimbolos = HashMap<String, Any?>()

    fun estaDefinida(identificador: String): Boolean {
        return tablaSimbolos.containsKey(identificador)
    }

    fun getValor(identificador: String): Any? {
        return tablaSimbolos[identificador]
    }

    fun setValor(identificador: String, valor: Any?) {
        if (estaDefinida(identificador)) {
            tablaSimbolos[identificador] = valor
        }
        else throw RuntimeException("$identificador no está definido")
    }

    fun addVariable(identificador: String, valor: Any?) {
        if (estaDefinida(identificador)) {
            throw RuntimeException("$identificador ya está definida")
        }
        tablaSimbolos[identificador] = valor;
    }
}