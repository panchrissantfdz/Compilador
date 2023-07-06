package org.Compiladores.Kotlin

class SolverAritmetico(private val nodo: Nodo) {
    fun resolver(): Any? {
        return resolver(nodo)
    }

    private fun resolver(n: Nodo): Any? {
        var tabladeSimbolos = TabladeSimbolos
        // No tiene hijos, es un operando
        if (n.getHijos() == null) {
            if (n.getValue().tipo == TokenType.NUMBER|| n.getValue().tipo == TokenType.STRING) {
                return n.getValue().literal
            }
            else if (n.getValue().tipo == TokenType.IDENTIFIER) {
                // Ver la tabla de símbolos
                return tabladeSimbolos.getValor(n.getValue().lexema)
            }
            else if (n.getValue().tipo == TokenType.TRUE || n.getValue().tipo == TokenType.FALSE) {
                val a = n.getValue().lexema.toBoolean()
                return a
            }

        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        val izq: Nodo? = n.getHijos()?.get(0)
        val der: Nodo? = n.getHijos()?.get(1)


        val resultadoIzquierdo: Any? = izq?.let { resolver(it) }
        val resultadoDerecho: Any? = der?.let { resolver(it) }

        if (resultadoIzquierdo is Double && resultadoDerecho is Double) {
            when (n.getValue().tipo) {
                TokenType.PLUS -> return resultadoIzquierdo + resultadoDerecho
                TokenType.MINUS-> return resultadoIzquierdo - resultadoDerecho
                TokenType.MULTIPLY-> return resultadoIzquierdo * resultadoDerecho
                TokenType.DIVIDE -> return resultadoIzquierdo / resultadoDerecho
                TokenType.GREATER -> return resultadoIzquierdo > resultadoDerecho
                TokenType.GREATER_EQUAL -> return resultadoIzquierdo >= resultadoDerecho
                TokenType.LESS -> return resultadoIzquierdo < resultadoDerecho
                TokenType.LESS_EQUAL -> return resultadoIzquierdo <= resultadoDerecho
                TokenType.EQUAL -> return resultadoIzquierdo == resultadoDerecho
                TokenType.NOT_EQUAL -> return resultadoIzquierdo != resultadoDerecho


                else -> return null
            }
        } else if (resultadoIzquierdo is String && resultadoDerecho is String) {
            when(n.getValue().tipo){
                TokenType.EQUAL -> return resultadoIzquierdo == resultadoDerecho
                TokenType.NOT_EQUAL -> return resultadoIzquierdo != resultadoDerecho
                TokenType.PLUS -> return resultadoIzquierdo + resultadoDerecho


                else -> return null
            }
        } else if(resultadoIzquierdo is Boolean && resultadoDerecho is Boolean) {
            // Error por diferencia de tipos
            when(n.getValue().tipo){
                TokenType.AND -> return resultadoIzquierdo && resultadoDerecho
                TokenType.OR -> return resultadoIzquierdo || resultadoDerecho
                TokenType.EQUAL -> return resultadoIzquierdo == resultadoDerecho
                TokenType.NOT_EQUAL -> return resultadoIzquierdo != resultadoDerecho
                else -> return null
            }

        }

        return null
    }
}