package org.Compiladores.Kotlin

class varSolver(private var nodo: Nodo) {


    fun registrar(){
        sol(nodo)
    }

    fun sol(n: Nodo){

        val tabladeSimbolos = TabladeSimbolos
        //println("tabladeSimbolos ${tabladeSimbolos}")
        val aritmeticosolver: SolverAritmetico?
        //println("aritmeticosolver ${aritmeticosolver}")

        val registrarVarIzq: Nodo? = n.getHijos()?.get(0)
        //println("registrarVarIzq ${registrarVarIzq}")

        //println("n.getHijos()?.size ${n.getHijos()?.size}")
        val hijosSize = if(n.getHijos()?.size != null) n.getHijos()?.size else 0
        val registrarVarDer: Nodo? = if(hijosSize!! > 1) {n.getHijos()?.get(1)} else {null}
        //println("registrarVarDer ${registrarVarDer}")

      //  aritmeticosolver = SolverAritmetico(registrarVarDer!!)

        aritmeticosolver = if(registrarVarDer != null){SolverAritmetico(registrarVarDer)} else{null}

        val izq: String = registrarVarIzq!!.getValue().lexema
        //println("izq ${izq}")
        //val der: Any? = aritmeticosolver.resolver()
        val der = if(registrarVarDer != null) { aritmeticosolver?.resolver() } else{null}
       // println("der: ${der}")

        tabladeSimbolos.addVariable(izq, der)






    }



}