package org.Compiladores.Kotlin

import java.util.ArrayList

class Scanner internal constructor(private val source: String){
    private val tokens: MutableList<Token>
    private val lexeme: StringBuilder
    private var numberLine = 1
    private val keywords: MutableMap<String, TokenType>

    init {
        tokens = ArrayList()
        lexeme = StringBuilder()
    }

    init {
        // Set language keywords
        keywords = HashMap()
        keywords["else"] = TokenType.ELSE
        keywords["class"] = TokenType.CLASS
        keywords["for"] = TokenType.FOR
        keywords["fun"] = TokenType.FUNCTION
        keywords["if"] = TokenType.IF
        keywords["null"] = TokenType.NULL
        keywords["print"] = TokenType.PRINT
        keywords["return"] = TokenType.RETURN
        keywords["super"] = TokenType.SUPER
        keywords["this"] = TokenType.THIS
        keywords["true"] = TokenType.TRUE
        keywords["false"] = TokenType.FALSE
        keywords["while"] = TokenType.WHILE
        keywords["var"] = TokenType.VAR
        keywords["and"] = TokenType.AND
        keywords["or"] = TokenType.OR

    }

    fun scanTokens(): List<Token> {
        var state = 0
        var i = 0
        while (i <= source.length) {
            val currentCharacter = getCurrentCharacter(i, source.length)
            numberLine = incrementNumberLine(currentCharacter)
            when (state) {
                0 -> if (currentCharacter != '\u0000') {
                    if (currentCharacter == '<') {
                        state = 1
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '=') {
                        state = 2
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '>') {
                        state = 3
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '!') {
                        state = 4
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '(') {
                        state = 5
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == ')') {
                        state = 6
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '[') {
                        state = 7
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == ']') {
                        state = 8
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '{') {
                        state = 9
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '}') {
                        state = 10
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '"') {
                        state = 11
                        lexeme.append(currentCharacter)
                    } else if (Character.isDigit(currentCharacter)) {
                        state = 12
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '+') {
                        state = 18
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '-') {
                        state = 19
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '*') {
                        state = 20
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '/') {
                        state = 21
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '%') {
                        state = 22
                        lexeme.append(currentCharacter)
                    } else if (Character.isLetter(currentCharacter)) {
                        state = 25
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '_') {
                        state = 26
                        lexeme.append(currentCharacter)
                    } else if (Character.isSpaceChar(currentCharacter) || Character.isISOControl(currentCharacter)) {
                        state = 27
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == ';') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.SEMICOLON, lexeme.toString())
                    } else if (currentCharacter == ',') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.COMMA, lexeme.toString())
                    } else if (currentCharacter == '.') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.DOT, lexeme.toString())
                    } else {
                        throw RuntimeException("Unable to parse: $currentCharacter")
                    }
                }

                1 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.LESS_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.LESS, lexeme.toString())
                    }
                }

                2 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.ASSIGN, lexeme.toString())
                    }
                }

                3 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.GREATER_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.GREATER, lexeme.toString())
                    }
                }

                4 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.NOT_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.NOT, lexeme.toString())
                    }
                }

                5 -> {
                    i--
                    state = 0
                    addToken(TokenType.LEFT_PAREN, lexeme.toString())
                }

                6 -> {
                    i--
                    state = 0
                    addToken(TokenType.RIGHT_PAREN, lexeme.toString())
                }

                7 -> {
                    i--
                    state = 0
                    addToken(TokenType.LEFT_CORCH, lexeme.toString())
                }

                8 -> {
                    i--
                    state = 0
                    addToken(TokenType.RIGHT_CORCH, lexeme.toString())
                }

                9 -> {
                    i--
                    state = 0
                    addToken(TokenType.LEFT_BRACE, lexeme.toString())
                }

                10 -> {
                    i--
                    state = 0
                    addToken(TokenType.RIGHT_BRACE, lexeme.toString())
                }

                11 -> if (currentCharacter != '"' && currentCharacter != '\u0000') {
                    lexeme.append(currentCharacter)
                } else if (currentCharacter == '\u0000') {
                    throw RuntimeException("Unable to parse: $lexeme")
                } else {
                    state = 0
                    lexeme.append(currentCharacter)
                    addToken(
                        TokenType.STRING,
                        lexeme.toString(),
                        lexeme.substring(1, lexeme.length - 1)
                    )
                }

                12 -> if (currentCharacter >= '0' && currentCharacter <= '9') {
                    lexeme.append(currentCharacter)
                } else if (currentCharacter == '.') {
                    state = 13
                    lexeme.append(currentCharacter)
                } else if (currentCharacter == 'e' || currentCharacter == 'E') {
                    state = 15
                    lexeme.append(currentCharacter)
                } else {
                    i--
                    state = 0
                    addToken(TokenType.NUMBER, lexeme.toString(), lexeme.toString().toDouble())
                }

                13 -> if (currentCharacter >= '0' && currentCharacter <= '9') {
                    state = 14
                    lexeme.append(currentCharacter)
                } else {
                    throw RuntimeException("Unable to parse: $lexeme")
                }

                14 -> if (currentCharacter >= '0' && currentCharacter <= '9') {
                    lexeme.append(currentCharacter)
                } else if (currentCharacter == 'e' || currentCharacter == 'E') {
                    state = 15
                    lexeme.append(currentCharacter)
                } else {
                    i--
                    state = 0
                    addToken(TokenType.NUMBER, lexeme.toString(), lexeme.toString().toDouble())
                }

                15 -> if (currentCharacter >= '0' && currentCharacter <= '9') {
                    state = 17
                    lexeme.append(currentCharacter)
                } else if (currentCharacter == '+' || currentCharacter == '-') {
                    state = 16
                    lexeme.append(currentCharacter)
                } else {
                    throw RuntimeException("Unable to parse: $lexeme")
                }

                16 -> if (currentCharacter >= '0' && currentCharacter <= '9') {
                    state = 17
                    lexeme.append(currentCharacter)
                } else {
                    throw RuntimeException("Unable to parse: $lexeme")
                }

                17 -> if (currentCharacter >= '0' && currentCharacter <= '9') {
                    lexeme.append(currentCharacter)
                } else {
                    i--
                    state = 0
                    addToken(TokenType.NUMBER, lexeme.toString(), lexeme.toString().toDouble())
                }

                18 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.PLUS_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.PLUS, lexeme.toString())
                    }
                }

                19 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.MINUS_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.MINUS, lexeme.toString())
                    }
                }

                20 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.MULTIPLY_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.MULTIPLY, lexeme.toString())
                    }
                }

                21 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.DIVIDE_EQUAL, lexeme.toString())
                    } else if (currentCharacter == '/') {
                        state = 28
                        lexeme.append(currentCharacter)
                    } else if (currentCharacter == '*') {
                        state = 29
                        lexeme.append(currentCharacter)
                    } else {
                        i--
                        addToken(TokenType.DIVIDE, lexeme.toString())
                    }
                }

                22 -> {
                    state = 0
                    if (currentCharacter == '=') {
                        lexeme.append(currentCharacter)
                        addToken(TokenType.MOD_EQUAL, lexeme.toString())
                    } else {
                        i--
                        addToken(TokenType.MOD, lexeme.toString())
                    }
                }

                23 -> if (currentCharacter == '&') {
                    state = 0
                    lexeme.append(currentCharacter)
                    addToken(TokenType.AND, lexeme.toString())
                } else {
                    throw RuntimeException("Unable to convert: $lexeme")
                }

                24 -> if (currentCharacter == '|') {
                    state = 0
                    lexeme.append(currentCharacter)
                    addToken(TokenType.OR, lexeme.toString())
                } else {
                    throw RuntimeException("Unable to convert: $lexeme")
                }

                25 -> if (currentCharacter >= 'a' && currentCharacter <= 'z' || currentCharacter >= 'A' && currentCharacter <= 'Z' || currentCharacter >= '0' && currentCharacter <= '9' || currentCharacter == '_') {
                    lexeme.append(currentCharacter)
                } else {
                    i--
                    state = 0
                    addToken(TokenType.IDENTIFIER, lexeme.toString())
                }

                26 -> if (currentCharacter >= 'a' && currentCharacter <= 'z' || currentCharacter >= 'A' && currentCharacter <= 'Z' || currentCharacter >= '0' && currentCharacter <= '9') {
                    state = 25
                    lexeme.append(currentCharacter)
                } else if (currentCharacter == '_') {
                    lexeme.append(currentCharacter)
                } else {
                    throw RuntimeException("Unable to parse: $lexeme")
                }

                27 -> if (currentCharacter == ' ' || currentCharacter == '\t' || currentCharacter == '\n' || currentCharacter == '\r') {
                    lexeme.append(currentCharacter)
                } else {
                    i--
                    state = 0
                    lexeme.delete(0, lexeme.length)
                }

                28 -> if (currentCharacter != '\n') {
                    lexeme.append(currentCharacter)
                } else {
                    i--
                    state = 0
                    lexeme.delete(0, lexeme.length)
                }

                29 -> if (currentCharacter == '*') {
                    state = 30
                } else {
                    lexeme.append(currentCharacter)
                }

                30 -> {
                    if (currentCharacter == '/') {
                        state = 31
                    }
                    lexeme.append(currentCharacter)
                }

                31 -> {
                    i--
                    state = 0
                    lexeme.delete(0, lexeme.length)
                }

                else -> throw RuntimeException("Unable to parse: $currentCharacter")
            }
            i++
        }
        tokens.add(Token(TokenType.EOF, "", null, numberLine))
        return tokens
    }

    private fun addToken(type: TokenType, lexeme: String) {
        var type = type
        if (type === TokenType.IDENTIFIER) {
            type = keywords!!.getOrDefault(lexeme, TokenType.IDENTIFIER)
        }
        tokens.add(Token(type, lexeme, null, numberLine))
        this.lexeme.delete(0, this.lexeme.length)
    }

    private fun addToken(type: TokenType, lexeme: String, literal: Any) {
        tokens.add(Token(type, lexeme, literal, numberLine))
        this.lexeme.delete(0, this.lexeme.length)
    }

    private fun incrementNumberLine(current: Char): Int {
        if (current == '\n') numberLine++
        return numberLine
    }

    private fun getCurrentCharacter(index: Int, sourceLength: Int): Char {
        return if (index >= sourceLength) {
            '\u0000'
        } else source[index]
    }
}



