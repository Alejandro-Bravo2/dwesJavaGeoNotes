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


## Ventajas y desventajas de usar record
La ventaja de usar record en comparación con las clases normales es que el record te configura
automáticamente los métodos de comparación, hash y el toString y como ya vienen escritos
no hace falta hacerlos manualmente y sobre todo funcionan correctamente.
Además permite ser usados como dto facilmente. También una ventaja
esque los record son inmutables por lo que permiten una mejor consistencia
de los datos. 

## Código de la clase **Note**
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/eeb3f41e60b5b580712f433e99310a314b1ad1d8/src/main/java/com/example/geonotesteaching/Note.java#L8-L31

## Código de la clase **LegacyPoint**
https://github.com/Alejandro-Bravo2/dwesJavaGeoNotes/blob/eeb3f41e60b5b580712f433e99310a314b1ad1d8/src/main/java/com/example/geonotesteaching/LegacyPoint.java#L3-L52

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

  - Video normal → devuelve "🎬 Vídeo".
  - Video largo (más de 120 segundos) → devuelve "🎬 Vídeo largo".

Se eliminó el default para que el compilador obligue a cubrir todos los subtipos de Attachment.

