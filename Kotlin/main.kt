package org.Compiladores.Kotlin


import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths


var error: Boolean = false



@kotlin.jvm.Throws fun main(args: Array<String>){
    IOException()
    if(args.size > 1){
       println("Uso correcto: interprete [script]")

        // Convención defininida en el archivo "system.h" de UNIX
        System.exit(64)
    }
    else if(args.size == 1){
        ejecutarArchivo(args[0].toString())
    }
    else{
        ejecutarPrompt()
    }




    

    //var tipo: Ttoken = Ttoken.EOF
    //var dattt = Token(tipo, "", {}, 0)
    //  println("$dattt")
}


fun ejecutarArchivo(path: String){
    IOException()
    val bytes: ByteArray = Files.readAllBytes(Paths.get(path))
  //  ejecutar(new String(bytes, Charset.defaultCharset()));
    ejecutar(String(bytes, Charset.defaultCharset()));

    // Se indica que existe un error
    if(error) System.exit(65);


}

fun ejecutar(source: String){
    var scanner = Scanner(source)
    val tokens: List<Token> = scanner.scanTokens()
    val parser = Parser(tokens)
    parser.parse()

   // for(token: Token in tokens){
     //   println(token)
    //}



    val gpf = GeneradorPostfija(tokens)
    val postfija = gpf.convertir()


    val gast = GeneradorAST(postfija)
    val programa = gast.generarAST()
    programa.recorrer()


}

fun ejecutarPrompt(){
    IOException()
    var input: InputStreamReader = InputStreamReader(System.`in`)
    var reader:BufferedReader = BufferedReader(input)
    val v: Boolean = true
    do{
        println("--->")
        var linea: String =reader.readLine()
        if(linea == null) break // Ctrl + C
        ejecutar(linea)
        error = false

    }while(v == true)


}

fun error(numberLine: Int, message: String?) {
    if (message != null) {
        report(numberLine, "", message)
    }
}

private fun report(numberLine: Int, where: String, message: String) {
    System.err.println(
        "[line $numberLine] Error$where: $message"
    )
    error = true
}
