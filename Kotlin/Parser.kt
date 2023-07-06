package org.Compiladores.Kotlin

import com.sun.media.sound.RIFFReader


public class Parser(private final var tokens: List<Token>) {

    private val EOF: Token = Token(TokenType.EOF, "")
    private val CLASS: Token = Token(TokenType.CLASS, "class")
    private val ELSE = Token(TokenType.ELSE, "else")
    private val FOR = Token(TokenType.FOR, "for")
    private val FUN = Token(TokenType.FUNCTION, "fun")
    private val IF = Token(TokenType.IF, "if")
    private val NULL = Token(TokenType.NULL, "null")
    private val PRINT = Token(TokenType.PRINT, "print")
    private val RETURN = Token(TokenType.RETURN, "return")
    private val SUPER = Token(TokenType.SUPER, "super")
    private val THIS = Token(TokenType.THIS, "this")
    private val TRUE = Token(TokenType.TRUE, "true")
    private val FALSE = Token(TokenType.FALSE, "false")
    private val WHILE = Token(TokenType.WHILE, "while")
    private val VAR = Token(TokenType.VAR, "var")
    private val AND = Token(TokenType.AND, "and")
    private val OR = Token(TokenType.OR, "or")
    private val NUMBER = Token(TokenType.NUMBER, "number")
    private val STRING = Token(TokenType.STRING, "string")
    private val IDENTIFIER = Token(TokenType.IDENTIFIER, "identificador")
    private val LEFT_PAREN = Token(TokenType.LEFT_PAREN, "(")
    private val RIGHT_PAREN = Token(TokenType.RIGHT_PAREN, ")")
    private val LEFT_BRACE = Token(TokenType.LEFT_BRACE, "{")
    private val RIGHT_BRACE = Token(TokenType.RIGHT_BRACE, "}")
    private val COMMA = Token(TokenType.COMMA, ",")
    private val DOT = Token(TokenType.DOT, ".")
    private val SEMICOLON = Token(TokenType.SEMICOLON, ";")
    private val MINUS = Token(TokenType.MINUS, "-")
    private val PLUS = Token(TokenType.PLUS, "+")
    private val MULTIPLY = Token(TokenType.MULTIPLY, "*")
    private val DIVIDE = Token(TokenType.DIVIDE, "/")
    private val NOT = Token(TokenType.NOT, "not")
    private val NOT_EQUAL = Token(TokenType.NOT_EQUAL, "!=")
    private val EQUAL = Token(TokenType.EQUAL, "==")
    private val ASSIGN = Token(TokenType.ASSIGN, "=")
    private val LESS = Token(TokenType.LESS, "less")
    private val LESS_EQUAL = Token(TokenType.LESS_EQUAL, "less_equal")
    private val GREATER = Token(TokenType.GREATER, "grater")
    private val GREATER_EQUAL = Token(TokenType.GREATER_EQUAL, "greater_equal")




    private lateinit var futuro: Token
    private var i = 0



    public fun parse() {
        i = 0
        futuro = tokens[i]
        program()

        if(futuro == EOF){
            //println("Funciona")
        }
    }

    private fun program() {
        when (futuro) {
            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER,STRING, IDENTIFIER,
            LEFT_PAREN, SUPER, FOR, IF, PRINT, RETURN, WHILE,
            LEFT_BRACE, CLASS, VAR, FUN -> {
                declarar()
            }

            else -> {
                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
            }

        }


    }


    private fun match(t: Token){
        if(t.tipo == futuro.tipo || futuro.tipo == t.tipo){
            i++
            futuro = tokens[i]
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }


    // ---------------------------------------------------------------
        //Declaraciones
    // ---------------------------------------------------------------

    private fun declarar() {
        when (futuro) {
            CLASS -> {
                classDecl()
                declarar()
            }
            FUN ->{
                funDecl()
                declarar()
            }
            VAR ->{
                varDecl()
                declarar()
            }
            NOT, MINUS,TRUE, FALSE, NULL, THIS, NUMBER, STRING, IDENTIFIER,
            LEFT_PAREN, SUPER, FOR, IF, PRINT, RETURN, WHILE, LEFT_BRACE -> {
                declaracion()
                declarar()
            }


        }
    }


    private fun classDecl(){
        if(futuro == CLASS){
            match(CLASS)
            match(IDENTIFIER)
            classHer()
            match(LEFT_BRACE)
            funciones()
            match(RIGHT_BRACE)

        }
        else {
            println("Se esperaba un identificador")
        }
    }
        private fun classHer(){
            if(futuro == LESS){
                match(LESS)
                match(IDENTIFIER)
            }
        }
    private fun funDecl(){
        if(futuro == FUN){
            match(FUN)
            funcion()
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }


    private fun varDecl(){
        if(futuro == VAR){
            match(VAR)
            match(IDENTIFIER)
            varInit()
            match(SEMICOLON)

        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }
        private fun varInit(){
            if(futuro == ASSIGN){
                match(ASSIGN)
                expresion()
            }
        }

    //---------------------------------------------------------

    //---------------------------------------------------------


    private fun declaracion(){
        when(futuro){
            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
            IDENTIFIER, LEFT_PAREN, SUPER -> {
                expresionDecl()
            }
            FOR -> {
                forExp()
            }
            IF -> {
                ifExp()
            }
            PRINT -> {
                printExp()
            }
            RETURN -> {
                returnExp()
            }
            WHILE -> {
                whileExp()
            }
            LEFT_BRACE -> {
                block()
            }
            else -> {
                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
            }
        }
    }


    private fun expresionDecl(){
        when(futuro){
            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
            IDENTIFIER, LEFT_PAREN, SUPER -> {
                expresion()
                match(SEMICOLON)
            }
            else -> {
                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
            }
        }

    }

    private fun forExp(){
        if(futuro == FOR){
            match(FOR)
            match(LEFT_PAREN)
            forexp1()
            forexp2()
            forexp3()
            match(RIGHT_PAREN)
            declaracion()
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }
        private fun forexp1(){
            when(futuro){
                VAR -> {
                    varDecl()
                }
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER -> {
                    expresionDecl()
                }
                SEMICOLON -> {
                    match(SEMICOLON)
                }
                else -> {
                    throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                }

            }
        }

        private fun forexp2(){
            when(futuro){
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER -> {
                    expresion()
                    match(SEMICOLON)
                }
                SEMICOLON -> {
                    match(SEMICOLON)
                }
                else -> {
                    throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                }

            }
        }

        private fun forexp3(){
            when(futuro) {
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER -> {
                    expresion()
                   // match(SEMICOLON)
                }
            }
        }

    private fun ifExp(){
        if(futuro == IF){
            match(IF)
            match(LEFT_PAREN)
            expresion()
            match(RIGHT_PAREN)
            declaracion()
            elseExp()
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }

        private fun elseExp(){
            if(futuro == ELSE){
                match(ELSE)
                declaracion()
            }
        }

    private fun printExp(){
        if(futuro == PRINT){
            match(PRINT)
            expresion()
            match(SEMICOLON)
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }

    private fun returnExp(){
        if(futuro == RETURN){
            match(RETURN)
            returnexpresionOpcional()
            match(SEMICOLON)
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }

        private fun returnexpresionOpcional(){
            when(futuro){
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER -> {
                    expresion()
                }
            }
        }

    private fun whileExp(){
        if(futuro == WHILE){
            match(WHILE)
            match(LEFT_PAREN)
            expresion()
            match(RIGHT_PAREN)
            declaracion()
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }

    private fun block(){
        if(futuro == LEFT_BRACE){
            match(LEFT_BRACE)
            blockDecl()
            match(RIGHT_BRACE)
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }
        private fun blockDecl(){

            /*

            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
            IDENTIFIER, LEFT_PAREN, SUPER -> {
                expresionDecl()
            }
            FOR -> {
                forExp()
            }
            IF -> {
                ifExp()
            }
            PRINT -> {
                printExp()
            }
            RETURN -> {
                returnExp()
            }
            WHILE -> {
                whileExp()
            }
            LEFT_BRACE -> {
                block()
            }

             */



            when(futuro){
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER, PRINT, FOR, IF, RETURN, WHILE, LEFT_BRACE -> {
                    declaracion()
                    blockDecl()
                }
            }
        }

    // -------------------------------------------------

    // -------------------------------------------------


    private fun expresion(){
        when(futuro){
            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
            IDENTIFIER, LEFT_PAREN, SUPER -> {
                asignar()
            }
            else -> {
                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
            }
        }
    }

    private fun asignar(){
        when(futuro){
            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
            IDENTIFIER, LEFT_PAREN, SUPER -> {
                logicOr()
                asignarOp()
            }
            else -> {
                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
            }
        }
    }

        private fun logicOr(){
            when(futuro){
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER -> {
                    logicAnd()
                    logicOr2()
                }
                else -> {
                    throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                }
            }
        }

        private fun asignarOp(){
            if(futuro == ASSIGN){
                match(ASSIGN)
                expresion()
            }
        }

        private fun logicOr2(){
            if(futuro == OR){
                match(OR)
                logicAnd()
                logicOr2()
            }
        }

        private fun logicAnd(){
            when(futuro){
                NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                IDENTIFIER, LEFT_PAREN, SUPER -> {
                    igualdad()
                    logicAnd2()
                }
                else -> {
                    throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                }
            }
        }
            private fun logicAnd2(){
                if(futuro == AND){
                    match(AND)
                    igualdad()
                    logicAnd2()
                }
            }
            private fun igualdad(){
                when(futuro){
                    NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                    IDENTIFIER, LEFT_PAREN, SUPER -> {
                        comparar()
                        igualdad2()
                    }
                    else -> {
                        throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                    }
                }
            }

            private fun igualdad2(){
                when(futuro){
                    NOT_EQUAL -> {
                        match(NOT_EQUAL)
                        comparar()
                        igualdad2()
                    }
                    EQUAL -> {
                         match(EQUAL)
                        comparar()
                        igualdad2()
                    }
                }
            }

            private fun comparar(){
                when(futuro){
                    NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                    IDENTIFIER, LEFT_PAREN, SUPER -> {
                        term()
                        comparar2()
                    }
                    else -> {
                        throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                    }
                }
            }

                private fun comparar2(){
                    when(futuro){
                        GREATER -> {
                            match(GREATER)
                            term()
                            comparar2()
                        }
                        GREATER_EQUAL -> {
                            match(GREATER_EQUAL)
                            term()
                            comparar2()
                        }
                        LESS -> {
                            match(LESS)
                            term()
                            comparar2()
                        }
                        LESS_EQUAL -> {
                            match(LESS_EQUAL)
                            term()
                            comparar2()
                        }
                    }
                }

                private fun term(){
                    when(futuro){
                        NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                        IDENTIFIER, LEFT_PAREN, SUPER -> {
                            factor()
                            term2()
                        }
                        else -> {
                            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                        }
                    }
                }

                private fun term2(){
                    when(futuro){
                        MINUS -> {
                            match(MINUS)
                            factor()
                            term2()
                        }
                        PLUS -> {
                            match(PLUS)
                            factor()
                            term2()
                        }
                    }
                }

                    private fun factor(){
                        when(futuro){
                            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
                            IDENTIFIER, LEFT_PAREN, SUPER -> {
                                unario()
                                factor2()
                            }
                            else -> {
                                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                            }
                        }
                    }

                    private fun factor2(){
                        when(futuro){
                            DIVIDE -> {
                                match(DIVIDE)
                                unario()
                                factor2()
                            }
                            MULTIPLY -> {
                                match(MULTIPLY)
                                unario()
                                factor2()
                            }
                        }
                    }

                    private fun unario(){
                        when(futuro){
                            NOT -> {
                                match(NOT)
                                unario()
                            }
                            MINUS -> {
                                match(MINUS)
                                unario()
                            }
                            TRUE, FALSE, NULL, THIS, NUMBER, STRING, IDENTIFIER,
                            LEFT_PAREN, SUPER -> {
                                call()
                            }
                            else -> {
                                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                            }
                        }
                    }

                private fun call(){
                    when(futuro){
                        TRUE, FALSE, NULL, THIS, NUMBER, STRING, IDENTIFIER,
                        LEFT_PAREN, SUPER -> {
                            primary()
                            call2()
                        }
                        else -> {
                            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                        }
                    }
                }

                private fun call2(){
                    when(futuro){
                        LEFT_PAREN -> {
                            match(LEFT_PAREN)
                            argumentOP()
                            match(RIGHT_PAREN)
                            call2()
                        }
                        DOT -> {
                            match(DOT)
                            match(IDENTIFIER)
                            call2()
                        }
                    }
                }

                private fun primary(){
                    when(futuro){
                        TRUE -> match(TRUE)
                        FALSE -> match(FALSE)
                        NULL -> match(NULL)
                        THIS -> match(THIS)
                        NUMBER -> match(NUMBER)
                        STRING -> match(STRING)
                        IDENTIFIER -> match(IDENTIFIER)
                        LEFT_PAREN -> {
                            match(LEFT_PAREN)
                            expresion()
                            match(RIGHT_PAREN)
                        }
                        SUPER -> {
                            match(SUPER)
                            match(DOT)
                            match(IDENTIFIER)
                        }
                        else -> {
                            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
                        }
                    }
                }


    // -------------------------------------------------------------

    // -------------------------------------------------------------



    private fun funcion(){
        if(futuro == IDENTIFIER){
            match(IDENTIFIER)
            match(LEFT_PAREN)
            parametrosOp()
            match(RIGHT_PAREN)
            block()
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }
    private fun funciones(){
        if(futuro == IDENTIFIER){
            funcion()
            funciones()
        }
    }

    private fun parametrosOp(){
        if(futuro == IDENTIFIER){
            parametros()
        }
    }

    private fun parametros(){
        if(futuro == IDENTIFIER){
            match(IDENTIFIER)
            parametros2()
        }
        else{
            throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
        }
    }

    private fun parametros2(){
        if(futuro == COMMA){
            match(COMMA)
            match(IDENTIFIER)
            parametros2()
        }
    }

    private fun argumentOP(){
        when(futuro){
             NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
             IDENTIFIER, LEFT_PAREN, SUPER -> {
                argumento()
            }
        }

    }

    private fun argumento(){
        when(futuro){
            NOT, MINUS, TRUE, FALSE, NULL, THIS, NUMBER, STRING,
            IDENTIFIER, LEFT_PAREN, SUPER -> {
                expresion()
                argumento2()
            }
            else -> {
                throw Error("Error at ${futuro.linea} en la DECLARACION esperada")
            }
        }
    }

    private fun argumento2(){
        if(futuro == COMMA){
            match(COMMA)
            expresion()
            argumento2()
        }
    }





}