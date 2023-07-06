package org.Compiladores.Kotlin

class whileSolver (private var nodo: Nodo) {

    fun ciclar(){
        sol(nodo)
    }

    fun sol(n:Nodo){
        var arithmeticSolver: SolverAritmetico?
        var arbol: Arbol

        val hijo: MutableList<Nodo>? = n.getHijos()
        val condition = hijo?.get(0)
        hijo?.removeAt(0)



        var cuerpoPar: Nodo? = Nodo(Token(TokenType.SEMICOLON, ";"))
        hijo?.let { cuerpoPar?.insertarHijos(it) }

        arithmeticSolver = if(condition != null){SolverAritmetico(condition)}else{null}
        var resultadoCond = arithmeticSolver?.resolver()

        arbol = Arbol(cuerpoPar!!)

        if(resultadoCond is Boolean){
            while(resultadoCond as Boolean){
                arbol.recorrer()
                resultadoCond = arithmeticSolver?.resolver()
            }
        }
        else{
            throw RuntimeException("Condiciones de WHILE deben de ser Booleanos")
        }

    }


}