package org.Compiladores.Kotlin


class Token(){

    constructor(tipo: TokenType, lexema: String, literal: Any?, linea: Int): this(){
        this.tipo = tipo
        this.lexema = lexema
        this.literal = literal
        this.linea = linea
    }

    constructor(tipo: TokenType, lexema: String): this(){
        this.tipo = tipo
        this.lexema = lexema
        this.literal = null
        this.linea = 0
    }

    constructor(tipo: TokenType, lexema: String, literal: Any?): this(){
        this.tipo = tipo
        this.lexema = lexema
        this.literal = null
        this.linea = 0
    }

    constructor(literal: Any?): this(){
        this.tipo = tipo
        this.lexema = lexema
        this.literal = null
        this.linea = 0
    }

    final var tipo: TokenType = TokenType.EOF
    final var lexema: String = ""
    final var literal: Any? = null
    final var linea: Int = 0

    // constructor(tipo:Ttoken, lexema:String, literal:Object){
      //  println("$tipo $lexema $literal")
    //  }
    /*init{
        this.tipo = tipo
        this.lexema = lexema 
        this.literal = literal 
        this.linea = linea
    }*/


    
    override fun toString(): String{
        return "${tipo.name + lexema + literal}"
    }

    override fun equals(other: Any?):Boolean{
        if(other !is Token){
            return false
        }

        return this.tipo == other.tipo

    }


    override fun hashCode(): Int {
        var result = tipo.hashCode()
        result = 31 * result + lexema.hashCode()
        result = 31 * result + literal.hashCode()
        result = 31 * result + linea
        return result
    }

    fun esOperando(): Boolean {
        return when (this.tipo) {
            TokenType.IDENTIFIER, TokenType.NUMBER, TokenType.STRING, TokenType.TRUE, TokenType.FALSE -> true
            else -> false
        }
    }

    fun esOperador(): Boolean {
        return when (this.tipo) {
            TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE,
            TokenType.EQUAL, TokenType.NOT_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL,
            TokenType.LESS, TokenType.LESS_EQUAL, TokenType.AND, TokenType.OR, TokenType.ASSIGN -> true
            else -> false
        }
    }

    fun esPalabraReservada(): Boolean {
        return when (this.tipo) {
            TokenType.VAR, TokenType.IF, TokenType.PRINT,
            TokenType.ELSE, TokenType.WHILE, TokenType.FOR -> true
            else -> false
        }
    }

    fun esEstructuraDeControl(): Boolean {
        return when (this.tipo) {
            TokenType.FOR, TokenType.WHILE, TokenType.IF, TokenType.ELSE -> true
            else -> false
        }
    }

    fun precedenciaMayorIgual(t: Token): Boolean {
        return tenerPrecedencia() >= t.tenerPrecedencia()
    }


    private fun tenerPrecedencia(): Int {
        when (this.tipo) {
            TokenType.MULTIPLY, TokenType.DIVIDE -> return 7
            TokenType.PLUS, TokenType.MINUS -> return 6
            TokenType.LESS, TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL -> return 5
            TokenType.EQUAL, TokenType.NOT_EQUAL -> return 4
            TokenType.AND -> return 3
            TokenType.OR -> return 2
            TokenType.ASSIGN -> return 1
            else -> {}
        }
        return 0
    }

    fun aridad(): Int {
        when (this.tipo) {
            TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.PLUS, TokenType.MINUS,
            TokenType.EQUAL, TokenType.ASSIGN, TokenType.NOT_EQUAL, TokenType.GREATER,
            TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL, TokenType.AND, TokenType.OR -> return 2
            else -> {}
        }
        return 0
    }

}

//fun String?.plus(other: Any?): String