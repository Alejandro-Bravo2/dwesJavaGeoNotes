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

En mi parte (A1) he trabajado en el record `Note` añadiendo validaciones básicas:

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
