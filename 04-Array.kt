 //Sintaxis Basica en Kotlin 
//----------------------------------
/* 
    1 - Arreglos con elementos especificos
    2 - Arreglos con tamaño especifico
    3 - El tamaño de nuestro arreglo
    4 - Accediendo a los elementos
    5 - Comparando arreglos
*/

/*
    Arreglos con elementos especificos
    Kotlin nos provee de varios tipos para representar arreglos asi que nuestros arreglos podemos definir a nuestros arreglos con diferentes tipos
    IntArray ,LongArray DoubleArray FloatArray, CharArray, ShortArray, ByteArray, BooleanArray
    Tenemos String Array? no
*/

val numbers = intArrayOf(1, 2, 3, 4, 5)
val characters = charArrayOf('K', 't', 'l')
val doubles = doubleArrayOf(1.25, 0.17, 0.4)

/*
    Arreglos con tamaño especifico
    Podriamos determinar el tamaño de un arreglo para que este con crezca mas de lo debido, para logralo lo hacemos
    de la siguiente manera
*/

val numbers = IntArray(5)
val doubles = DoubleArray(7)

/*
    El tamaño de nuestro arreglo
    un arreglo siempre tiene un tamaño que es determinado por el numero de elementos. Nosotros podemos 
    obtener este valor a traves de una propiedad llamada size la cual es un numero del tipo entero
    Este tamaño no podra ser cambiado una vez que lo hayas definido
*/

val numbers = intArrayOf(1, 2, 3, 4, 5)
println(numbers.size) // 5

/*
    Accediendo a los elementos
    siempre sera necesario acceder y obtener los elementos de un array y para ello debemos de usar el indice 
    del arreglo. Los indices de estos arreglos van desde 0 a array.size - 1 
    (De esta manera obtendremos el ultimo elemento)
*/

//Asignando un elemento
array[index] = elem
//Obteniendo un elemento
val elem = array[index]

//INDICES
val numbers = IntArray(3) // numbers: 0, 0, 0

numbers[0] = 1 // numbers: 1, 0, 0
numbers[1] = 2 // numbers: 1, 2, 0
numbers[2] = numbers[0] + numbers[1] // numbers: 1, 2, 3

ArrayIndexOutOfBoundsException // Esta excepcion nos aparecera cuando intentemos acceder a un elemento inexistente


//Kotlin nos provee de algunos metodos que nos facilitan el acceso 
first()
last()
lastIndex

/*
    Comparando arreglos
    Como podemos comparar arreglos? para ello podriamos invocar una funcion llamada contentEquals() con esta funcion
    solo sera necesario pasar un array como parametro y obtendremos un tru o false dependiendo de si los 
    elementos son exactamente iguales.
*/

val numbers1 = intArrayOf(1, 2, 3, 4)
val numbers2 = intArrayOf(1, 2, 3, 4)
val numbers3 = intArrayOf(1, 2, 3)

println(numbers1.contentEquals(numbers2)) // true
println(numbers1.contentEquals(numbers3))