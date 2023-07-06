package org.Compiladores.Kotlin

class forSolver(private var nodo: Nodo){

    fun para(){
        sol(nodo)
    }

    fun sol(n: Nodo){
        val aritmeticSolver: SolverAritmetico?
        var arbol: Arbol

        val hijo: MutableList<Nodo>? = n.getHijos()
        val declaracion: Nodo? = n.getHijos()?.get(0)
        val condition = hijo?.get(1)
        val aumento: Nodo? = n.getHijos()?.get(2)

        var cuerpoPar: Nodo? = Nodo(Token(TokenType.SEMICOLON, ";"))
        hijo?.subList(3, hijo.size)?.let { cuerpoPar?.insertarHijos(it) }
        cuerpoPar?.insertarHijo(aumento!!)

        arbol = Arbol(cuerpoPar!!)

        if(declaracion!!.getValue().tipo == TokenType.VAR){
           val valsolver = varSolver(declaracion)
           //val val1 = valsolver.sol(declaracion)
            valsolver.registrar()
        }
        else if(declaracion!!.getValue().tipo == TokenType.ASSIGN){
            val assignsolver = assignSolver(declaracion)
            //val val1 = assignsolver.sol(declaracion)
            assignsolver.asignar()
        }

        aritmeticSolver = SolverAritmetico(condition!!)
        var resultadoCond = aritmeticSolver?.resolver()

        arbol = Arbol(cuerpoPar!!)

        if(resultadoCond is Boolean){
            while(resultadoCond as Boolean){
                arbol.recorrer()
                resultadoCond = aritmeticSolver?.resolver()
            }
        }


    }


}