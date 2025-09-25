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
  

## Ventajas y desventajas de usar record
La ventaja de usar record en comparación con las clases normales es que el record te configura
automáticamente los métodos de comparación, hash y el toString y como ya vienen escritos
no hace falta hacerlos manualmente y sobre todo funcionan correctamente.
Además permite ser usados como dto facilmente. También una ventaja 
esque los record son inmutables por lo que permiten una mejor consistencia
de los datos. 