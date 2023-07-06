package org.Compiladores.Kotlin;


import java.util.*

class GeneradorAST(private val postfija: List<Token>) {
    private val pila = Stack<Nodo>()

    fun generarAST(): Arbol {
        val pilaPadres = Stack<Nodo>()
        val raiz = Nodo(postfija[postfija.size - 1])
        pilaPadres.push(raiz)
        var padre: Nodo = raiz

        for (t: Token in postfija) {
            if (t.tipo == TokenType.EOF) {
                break
            }
            if (t.esPalabraReservada()) {
                val n = Nodo(t)
                padre = pilaPadres.peek()
                padre.insertarSiguienteHijo(n)
                pilaPadres.push(n)
                padre = n
            }
            else if (t.esOperando()) {
                val n = Nodo(t)
                pila.push(n)
            }
            else if (t.esOperador()) {
                val aridad = t.aridad() - 1
                val n = Nodo(t)
                for (i in aridad downTo 0 step 1) {
                    val nodoAux = pila.pop()
                    n.insertarHijo(nodoAux)
                }
                pila.push(n)
            }
            else if (t.tipo == TokenType.SEMICOLON) {
                if (pila.isEmpty()) {
                    pilaPadres.pop()
                            padre = pilaPadres.peek()
                            }
                else {

                    val n = pila.pop()
                    if (padre.getValue().tipo == TokenType.VAR) {
                        if (n.getValue().tipo == TokenType.ASSIGN) {
                            padre.insertarHijos(n.getHijos()!!)
                        }
                        else {
                            padre.insertarSiguienteHijo(n)
                        }
                        pilaPadres.pop()
                        padre = pilaPadres.peek()
                    }
                    else if (padre.getValue().tipo == TokenType.PRINT) {
                    padre.insertarSiguienteHijo(n)
                    pilaPadres.pop()
                    padre = pilaPadres.peek()
                    }
                    else {
                    padre.insertarSiguienteHijo(n)
                    }
                }
            }
        }


        return Arbol(raiz)
    }
}

