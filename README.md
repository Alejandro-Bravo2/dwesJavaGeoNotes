# GeoNotesTeaching (Java 21)

Proyecto docente con Java clásico + moderno (records, sealed, text blocks, switch expression, pattern matching).
Incluye Gradle Wrapper (scripts) para facilitar la ejecución.

## Ejecutar
- IntelliJ: Abrir carpeta y ejecutar tarea Gradle `run` o `examples`.
- Terminal:
  ```bash
  ./gradlew run
  ./gradlew examples
  ```

## Bloque A1 - Validación y Excepciones

Se ha trabajado en el record `Note` añadiendo validaciones básicas:

- El **título**:
  - No puede ser `null`.
  - Se recorta con `trim()`.
  - Debe tener al menos 3 caracteres.

- El **contenido**:
  - Si es `null` se convierte en cadena vacía.
  - Se recorta con `trim()`.
  - Si queda vacío, se sustituye por `"–"`.

- **Otras validaciones**:
  - `location` es obligatorio (`null` no permitido).
  - `createdAt` se asigna con la fecha actual (`Instant.now()`) si no se pasa.

De esta forma, cuando se crean notas desde el menú, si falta algo o no es válido,
el programa lanza una excepción (`IllegalArgumentException`) con un mensaje claro
que aparece directamente en consola. En resumen, son validaciones sencillas para
que no se creen notas vacías o sin título.

---

## Bloque A2 - Equals/Hashcode

Se ha trabajado con la clase `LegacyPoint` para comparar su uso con una clase tipo record.

Clase **LegacyPoint**:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/services/LegacyPoint.java#L3-L52

### ¿Qué ventajas tiene en comparación con el record?
Las ventajas que tiene es que a estas clases le podemos añadir lógica como por ejemplo métodos o comprobaciones antes de crear la clase, en cambio los record son clases que solo sirven para almacenar datos de datos, además cuando nos referimos a almacenar datos también tiene una ventaja las clases normales en comparación con las record y es que las clases normales pueden modificar sus atributos en cambio los record son inmutables, esto puede ser una ventaja o desventaja en algunos casos, pero si bien en cierto que en las clases normales si queremos que algo no se modifique podremos cambiarle el setter de un atributo a privado y lo convertiríamos en inmutable por lo que realmente podríamos hacer lo mismo que una record.

### ¿Qué desventaja tiene con el record?
La principal desventaja es que el record ya tiene métodos pre hechos y tiene los setters y getters hechos por lo que realmente cuando solo queremos almacenar datos de forma inmutable permitiendo una mejor consistencia de los datos, las clases tipo  record te facilitan la construcción de dichas clase porque no tienes que crear el toString ni hascode ni otros métodos que tendrías que crear a mano si lo hicieras con una clase normal.

---

## Bloque B1 - Nuevo subtipo: Video

En este bloque se ha ampliado la jerarquía sellada Attachment añadiendo un nuevo record.
Validaciones incluidas:

- **Url**:

  - No puede ser `null` ni estar en blanco. 
  - Se recorta con `trim()`. 
  - Debe comenzar por `http://` o `https://`. 
  - `width` debe ser positivo. 
  - `seconds` no puede ser negativo.

- Cambios en la jerarquía:

Se actualizó Attachment (permits …) para incluir `Video` como subtipo sellado.

- Cambios en Describe.describeAttachment

Se añadió soporte para diferenciar entre:

  - Video normal → devuelve " Vídeo".
  - Video largo (más de 120 segundos) → devuelve "🎬 Vídeo largo".

Se eliminó el default para que el compilador obligue a cubrir todos los subtipos de Attachment.

---

## Bloque B2 - Formato corto vs Formato largo (en switch)
En este bloque hemos trabajado con la clase `Describe` y con la lógica de los switch para comprobar que es más efectivo si el formato largo o corto.

### Switch de describe:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/services/Describe.java#L12-L24

### Ventajas del formato corto
Las ventajas del formato corto es que no requiere de que escribas yield para devolver el objeto ni preocuparte por las llaves, por lo que facilita el uso del switch.

### Ventajas del formato largo
Las ventajas del formato largo es que permiten definir más logica y de forma mas organizada y estructurada que en el formato corto.

---

## Bloque C1 – Export JSON pretty

En este ejercicio se ha trabajado en la clase `Timeline.Render` para mejorar la exportación a JSON.  
Antes la salida se generaba en una sola línea, lo que hacía difícil leer el contenido de cada nota.

Los cambios han sido:
- Ajustar el **formato con *text blocks*** para que cada campo de la nota aparezca en su propia línea.
- Escapar las comillas del `content` con `.replace("\"", "\\\"")`, de forma que si el texto incluye comillas el JSON siga siendo válido.

De esta manera, ahora el JSON se muestra **mucho más legible** en textos con comillas.

---

## Bloque C2 - Exportar a markdown

En este bloque hemos trabajado con la clase `MarkdownExporter` para permitir exportar las notas a un formato markdown por terminal.

Clase **MarkdownExporter**:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/exporter/MarkDownExporter.java#L6-L23

### Ejemplo de ejecución:
<img width="691" height="367" alt="image" src="https://github.com/user-attachments/assets/b070d202-830e-49bc-a148-1bcccf35c36a" />

### Funcionamiento
Lo primero que hace es declarar una variable que es la que va a contener todas las notas y con el formato markdown dicha variable será de tipo string.
Luego agrega a dicha variable el título de Geonotes y añade un salto de línea, luego ejecuta un for llamando a la clase `timeline` para obtener todas las notas. En dicho for se recorrerán todas las notas y agregandolas a la variable que almacena las notas en markdown y luego agrega un salto de línea.

Una vez finalizado el bloque for se devuelve el valor de la variable y se imprime en pantalla.

---

## Bloque D1 - Orden por fecha y límite

En este bloque se ha trabajado en la clase `Timeline` para crear un método que devuelve las notas más recientes usando Streams y comparadores, y se ha añadido una opción de menú para listarlas.

### Método latest en la clase Timeline:

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/82aa58b73e3aa8670ef09d6034c9f8fa6eea58ea/src/main/java/com/example/geonotesteaching/exporter/Timeline.java#L56-L67

### Opción de menú en la clase GeoNotes:

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L77-L89

### Funcionamiento del método

El método `latest` recibe un número entero `n` que indica cuántas notas se quieren obtener.
A través de un Stream, se ordenan todas las notas por su fecha de creación (`createdAt`) en orden descendente (de la más reciente a la más antigua) y se limitan a las `n` primeras.

### Cambios en la CLI

Se ha añadido una nueva opción en el menú principal bajo el nombre “Listar las últimas notas”.
Esta opción solicita al usuario un número y muestra las `n` notas más recientes con su ID, título y contenido.
En caso de que no existan notas, el programa muestra un mensaje informativo.

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L119-L132

### Resultado

Con esta mejora, el usuario puede consultar de forma rápida las últimas notas creadas sin tener que revisar toda la lista completa.

---

## Bloque D2 - Búsqueda con varios criterios

En ese bloque hemos trabajado con la clase `GeoNotes` para añadir una opción más al menú, permitiendo que este posea la funcionalidad para filtrar por:
- Rango de lat/lon
- Palabra clave en tittle o content.

### Menú de busqueda avanzada:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L239-L273

### Opción de busqueda por lat/lon:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L275-L303

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L305-L317


### Opción de filtrado por palabra clave:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L329-L341

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L343-L356

### Método usado para mostrar notas encontradas en ambas opciones:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L320-L326


### Funcionamiento
**Funcionamiento de busqueda por latitud y longitud**
Lo primero que se realiza son una seríe de entradas por teclado de un rango de coordenadas para conocer las notas que tienen el rango de valores de latitud y longitud que estén dentro de ese rango. Una vez se obtienen esos datos lo que se realiza es un bucle for para ir comprobando nota a nota si sus valores de latitud o longitud están dentro de ese rango, en caso de que si estén entonces se guarda dicha nota en una array.

Una vez se ha recorrido todo el for lo que se realiza es una llamada a una función para mostrar las notas, en dicha función se comprueba si el array está vacio o no. En caso de que esté vacio se imprime por consola que no se han encontrado notas, en caso de que el array no esté vacio entonces se ejecuta un for que recorre todas las notas imprimiendo sus valores.

**Funcionamiento de busqueda por palabra clave**
Lo primero que se hace es pedirle al usuario que escriba una palabra clave para filtrar por ella, una vez obtenida dicha información se realiza un for para ir recorrriendo todas las notas que almacena la clase timeline y con un if iremos comprobando si el contenido o título de la nota contiene dicha palabra clave. En caso de que si la contenga entonces se agrega a un array dinámico dicha nota.

Una vez se ha recorrido todo el for lo que se realiza es una llamada a una función para mostrar las notas, en dicha función se comprueba si el array está vacio o no. En caso de que esté vacio se imprime por consola que no se han encontrado notas, en caso de que el array no esté vacio entonces se ejecuta un for que recorre todas las notas imprimiendo sus valores.

---

## Bloque E1 - Instanceof con patrón

En este bloque se ha trabajado en la clase `Describe` para introducir un nuevo método que utiliza **pattern matching con `instanceof`**, una característica introducida en las últimas versiones de Java.

### Método creado en la clase Describe:

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/services/Describe.java#L26-L37

### Funcionamiento

El método `mediaPixels(Object o)` comprueba el tipo del objeto recibido:
- Si es una instancia de `Photo`, calcula su ancho por su alto.
- Si es una instancia de `Video`, hace lo mismo.
- Si no es ninguno de los dos, devuelve 0.

De esta forma, se evita el uso de casting manual y se aprovecha el **pattern matching** para simplificar el código y hacerlo más limpio.

## Bloque E2 - Record patterns
En este bloque se ha trabajado con la clase `Match` y la clase `GeoNote`.

En la clase **Match** se ha creado un método llamado `where` que lo que permite es a través de un switch comprobar si los valores de latitud o longitud tienen cierto valor, en caso de que tenga ese valor entonces devolverá una string que corresponde al sitio en que se ubica dicha nota. 

### Método where de la clase Match:
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/services/Match.java#L24-L31

### Método en la clase GeoNotes encargada de la opción del menú para encontrar notas por las distintas ubicaciones
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L172-L214

### Funcionamiento
Lo primero que se hace es mostrar un menú con las pociones disponibles, luego el programa le pedirá al usuario que ingrese una opción. El programa revisasará si esa opción es válida con un if, en caso de que la opción sea válida entonces el programa realizará un for por cada nota de timeline usando el método where de Match para comprobar si dichas coordenadas corresponden a la ubicación dicha por el usuario. Todas las notas que sus ubicaciones correspondan a la que dijo el usuario se irán almacenando en un array. Una vez se haya recorrido todas las notas se imprimirán las notas encontradas.

---

## Bloque F1 - Manejo de InputMismatch / NumberFormat

En este bloque se ha trabajado en la clase `GeoNotes`, concretamente en el método `createNote()`, para mejorar la validación de las entradas del usuario al crear una nueva nota.

### Método modificado en la clase GeoNotes:

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L134-L177

### Cambios realizados
Antes si el usuario introducía un texto en lugar de un número al escribir la latitud o longitud, el programa lanzaba una excepción y se detenía.  
Ahora se ha mejorado esa parte del código con un bloque `try/catch` que captura la excepción `NumberFormatException`, mostrando un mensaje de error sin interrumpir la ejecución del programa.

### Resultado
El programa ahora es seguro frente a errores del usuario, ya que valida las entradas y muestra mensajes de error en lugar de cerrarse.

---

## Bloque F2 - Comprobaciones nulas

En este bloque se ha trabajado con la clase `Link` y lo que se realiza es una comprobación de si el label introducido es null o vacío, en caso de serlo se reemplazará por la url.

### Método encargado de dicha comprobación
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/model/Link.java#L15-L17

También se ha cambiado la clase `Describe` ya que en su switch no hacía uso de este método.

**Clase describe**
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/bbdd3bc0b10cae4548261d9be305ef9c975fd90f/src/main/java/com/example/geonotesteaching/services/Describe.java#L11-L24

---

## Bloque G1 - Vista invertida (SequencedMap)

En este bloque se ha trabajado con la clase `Timeline` para aprovechar las nuevas funcionalidades introducidas en **Java 21** con las colecciones secuenciadas (`SequencedMap`).  
El objetivo era obtener una vista invertida de las notas sin alterar su orden original.

### Declaración del mapa y método reversed:

**Declaración del mapa**

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/exporter/Timeline.java#L18

**Método `reserved`**

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/exporter/Timeline.java#L69-L71

### Funcionamiento
El tipo de mapa interno se cambió a `SequencedMap<Long, Note>` para aprovechar la función `reversed()`.  
Con esto se puede obtener la colección de notas en orden inverso (de la más reciente a la más antigua) de forma inmediata, sin necesidad de usar streams o crear listas auxiliares.

### Cambios en la CLI
Se añadió una opción en el menú llamada **“Listar notas en orden inverso”**, que muestra las notas usando el nuevo método `reversed()`.

https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/0c78c1b86fbc6b9f0b40863df3fdc85d3e11a856/src/main/java/com/example/geonotesteaching/geo/GeoNotes.java#L119-L132

### Resultado
Con esta mejora, el proyecto incorpora una de las novedades más actuales de **Java 21**, demostrando cómo combinar estructuras clásicas como `LinkedHashMap` con las nuevas APIs modernas para obtener código más claro y funcional.

---

## Autores
Este proyecto ha sido desarrollado conjuntamente por:
- **Alejandro Bravo**
- **Natalia Alejo**

Trabajo realizado dentro del módulo de **Desarrollo Web en entorno servidores (2º DAW)**.

