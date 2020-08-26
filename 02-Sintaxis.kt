    //Sintaxis Basica en Kotlin 
//----------------------------------
/* 
    PAQUETES: Todos los contenidos como clases y funciones del codigo estan contenidos dentro de nuestro paquete, si el paquete no esta
    especificado los contenidos de un archivos perteneceran  al paquete predefinido si nombre
    IMPORTS AUTOMATICOS: Kotlin importa automaticamente el siguiente conjunto de paquetes para poder funcionar    
        kotlin.*
        kotlin.annotation.*
        kotlin.collections.*
        kotlin.comparisons.* (since 1.1)
        kotlin.io.*
        kotlin.ranges.*
        kotlin.sequences.*
        kotlin.text.*
    Adicionalmente se importan ciertos paquetes dependiendo de la plataforma 
        JVM:
          java.lang.*
          kotlin.jvm.*
        JS:
          kotlin.js.*
    PUNTO DE ENTRADA DE NUESTRO PROGRAMA:El punto de inicio o entry point en kotlin es la funcion main()


*/
//----------------------------------
package org.androidhms
//Podemos utilizar un comodin para importar todo lo que este dentro del paquete de text
import kotlin.text.*
//Tambien podriamos importar uno solo 
import org.example.Message
//Si hay un choque de nombres podemos utilizar la palabra reservada as para diferenciar como en este ejemplo
import org.example.Message 
import org.test.Message as testMessage 

//Este es nuestro punto de entrada aqui llamaremos funciones, crearemos objetos etc...
fun main() {
    println("Hello world!")
}

