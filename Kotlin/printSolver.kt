package org.Compiladores.Kotlin

class printSolver(private var nodo: Nodo) {

    fun imprimir(){
        sol(nodo)
    }

    fun sol(n: Nodo){

        val aritmeticosolver: SolverAritmetico


        val hijo: MutableList<Nodo>? = n.getHijos()
        val loQueVaAImprimir: Nodo? = hijo?.get(0)



       // val resol = SolverAritmetico(loQueVaAImprimir!!) // <---
        aritmeticosolver = SolverAritmetico(loQueVaAImprimir!!)


        println(aritmeticosolver.resolver())



    }

}