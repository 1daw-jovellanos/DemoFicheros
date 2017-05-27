# DemoFicheros

En este proyecto hay cinco paquetes distintos con demos acerca de técnicas sencillas con 
ficheros.

1. *Diferencia entre texto y binario*: se graban unos cuantos enteros de un array
   a un fichero de texto secuencial por un lado y a un fichero binario por otro. 
   En el de texto hay que decidir un formato, así que se escribe un entero en cada línea.
   Cada entero tiene una longitud distinta dependiendo de la cantidad de cifras decimales que tenga.
   En el binario, cada entero ocupa exactamente 8 bytes en complemento a 2.

Las siguientes demos tienen el mismo objetivo, pero lo logran de manera distinta.

2. *Texto CSV*: Se graban los datos de varios objetos de tipo _Alumno_ que están en un array,
   y luego se recupera una solo de ellos, del que se pide la posición al usuario por terminal.
   El formato CSV no es estándar, pero se suele utilizar de una manera muy parecida en
   casi todas las implementaciones. Es un formato posicional, así que cada dato está siempre en
   la misma posición. Los datos van separados por comas. Cada alumno ocupa una línea.

3. a) *Binario de acceso secuencial*: Se graban los datos de cada alumno secuencialmente en binario. De las cadenas
   se graba primero su longitud con 2 bytes, y luego los carácteres. Los primitivos tienen un tamaño fijo.
   Como los datos de cada alumno tienen una longitud distinta, para recuperar un dato hay que pasar por
   los anteriores. Es secuencial.
3. b) *Binario de acceso aleatorio*: En los ficheros de acceso aleatorio se graban los datos ocupando todos la
   misma cantidad de bytes. Para ello, las cadenas se graban con una cantidad fija de bytes.
   Se puede saltar a cualquier posición del fichero para realizar operaciones de lectura y escritura.
4. a) *Serialización nativa*: El mecanismo de serialización nativa de Java permite guardar un objeto entero
   con todas sus referencias a otros objetos de manera automática. El único requisito para serializar es que
   los objetos a serializar implementen la interface java.io.Serializable, que no conlleva ninguna 
   obligación
5. b) *Serialización XML con XStream*: Una alternativa a la serialización nativa. La librería XStream permite 
   serializar utilizando ficheros de texto XML.



