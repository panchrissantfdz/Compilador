package org.Compiladores.Kotlin

class ifSolver(private var nodo: Nodo) {


    fun resolver(){
        sol(nodo)
    }

    fun sol(n: Nodo){
        var arbol: Arbol?
        var solverAritmetico: SolverAritmetico

        var hijo: MutableList<Nodo>? = n.getHijos()
        var condicion: Nodo? = hijo?.get(0)

        val cuerpoIf: Nodo?= Nodo(Token(TokenType.SEMICOLON, ";"))
        var cuerpoElse: Nodo? = Nodo(Token(TokenType.SEMICOLON, ";"))

        if(hijo?.last()?.getValue()?.tipo == TokenType.ELSE){
            hijo.last()?.getHijos()?.let { cuerpoElse?.insertarHijos(it) }
        }
        else{
           cuerpoElse = null
        }

        hijo?.subList(1, hijo.size)?.let { cuerpoIf?.insertarHijos(it) }

        solverAritmetico = SolverAritmetico(condicion!!)
        var resultadoCond: Any? = solverAritmetico.resolver()

        if(resultadoCond is Boolean){
            if(resultadoCond){
                arbol = Arbol(cuerpoIf!!)
                arbol?.recorrer()
            }
            else if(cuerpoElse != null){
                arbol =if(cuerpoElse != null){ Arbol(cuerpoElse)} else {null}
                arbol?.recorrer()
            }
        }
        else {
            throw RuntimeException("Condiciones de IF deben de ser Booleanos")
        }



    }

}