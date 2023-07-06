package org.Compiladores.Kotlin

class Nodo(private val value: Token) {

    private var hijos: MutableList<Nodo>? = null

    fun insertarHijo(n: Nodo) {
        if (hijos == null) {
            hijos = mutableListOf<Nodo>()
            hijos?.add(n)
        } else {
            hijos?.add(0, n)
        }
    }

    fun insertarSiguienteHijo(n: Nodo) {
        if (hijos == null) {
            hijos = ArrayList()
            hijos?.add(n)
        } else {
            hijos?.add(n)
        }
    }

    fun insertarHijos(nodosHijos: List<Nodo>) {
        if (hijos == null) {
            hijos = ArrayList()
        }
        for (n in nodosHijos) {
            hijos?.add(n)
        }
    }



    fun getValue(): Token {
        return value
    }

    fun getHijos(): MutableList<Nodo>? {
        return hijos
    }
}