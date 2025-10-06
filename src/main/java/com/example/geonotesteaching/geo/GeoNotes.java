package com.example.geonotesteaching.geo;

import com.example.geonotesteaching.exporter.MarkDownExporter;
import com.example.geonotesteaching.exporter.Timeline;
import com.example.geonotesteaching.model.*;
import com.example.geonotesteaching.services.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * GeoNotes — Clase principal con una CLI sencilla.
 *
 * COSAS A FIJARSE (Java 11 → 21):
 * - Java 11: API estándar consolidada; aquí usamos Instant (java.time) para fechas.
 * - Java 14: "switch expressions" con flechas (->) y posibilidad de yield en bloques (en este archivo usamos la forma simple; ver Describe para más).
 * - Java 15: "Text Blocks" (""" ... """) — se usan en Timeline.Render para generar JSON multilínea.
 * - Java 16: "records" (GeoPoint, Note, etc.) — clases inmutables concisas con constructor canónico, equals/hashCode/toString.
 * - Java 17: "sealed classes/interfaces" (Attachment) — jerarquías cerradas que el compilador puede verificar exhaustivamente.
 * - Java 17: pattern matching para instanceof (lo verás en Describe).
 * - Java 21: "record patterns" (lo verás en Match donde se desestructura un record directamente en un switch/if).
 * - Java 21: "Sequenced Collections" (Timeline podría usar SequencedMap/LinkedHashMap.reversed(); aquí mostramos el enfoque clásico, pero coméntalo en clase).
 * - Java 21: "Virtual Threads" (demo aparte en el proyecto moderno; no se usan aquí).
 */
public class GeoNotes {

    /*
     * timeline es el "modelo" de la aplicación: guarda las notas en memoria.
     * Timeline tiene una inner class no estática (Render) que sabe exportar el contenido a JSON con Text Blocks.
     * -> OJO: inner class no estática = necesita una instancia externa para crearse (ver exportNotesToJson()).
     */
    private static final Timeline timeline = new Timeline();

    /*
     * Scanner para leer del stdin. Mantener uno único y reutilizarlo es buena práctica para la CLI.
     */
    private static final Scanner scanner = new Scanner(System.in);

    /*
     * Generador simple de IDs. En un proyecto real, probablemente usarías UUID o una secuencia persistente.
     */
    private static long noteCounter = 1;

    // La clase principal del programa. Contiene el menú interactivo para la CLI.
    public static void main(String[] args) {
        /*
         * Modo "examples":
         * Gradle define una tarea 'examples' que invoca main con el argumento "examples".
         * Útil para mostrar rápidamente la salida JSON sin teclear en la CLI.
         */
        if (args != null && args.length > 0 && "examples".equalsIgnoreCase(args[0])) {
            seedExamples();
            exportNotesToJson();
            return;
        }
        System.out.println("--------------------------------------");
        System.out.println("  📝 Bienvenid@ a la aplicación GeoNotes");
        System.out.println("--------------------------------------");
        boolean running = true;
        while (running) {
            printMenu();
            try {

                /*
                 * Leemos la opción como String y la convertimos a int.
                 * En lugar de nextInt(), usamos nextLine()+parseInt() para evitar "pegarse" con saltos de línea restantes.
                 */
                int choice = Integer.parseInt(scanner.nextLine().trim());

                /*
                 * SWITCH EXPRESSION (Java 14):
                 * - Sintaxis con flechas (->), no hace falta 'break' y es más clara.
                 * - Si usáramos bloques complejos, podríamos usar 'yield' para devolver un valor.
                 * Aquí lo empleamos en su forma de "switch moderno" sobre efectos (no devuelve valor).
                 */
                switch (choice) {
                    case 1 -> createNote();
                    case 2 -> listNotes();
                    case 3 -> filterNotes();
                    case 4 -> exportNotesToJson();
                    case 5 -> exportMarkdown();
                    case 6 -> busquedaAvanzada();
                    case 7 -> ultimasNotas();
                    case 8 -> obtenerNotasPorUbicacion();
                    case 9 -> listarNotasReversed();
                    case 10 -> running = false;
                    default -> System.out.println("❌ Opción no válida. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                /*
                 * Manejo de errores "clásico" (en Kotlin tendrías null-safety y Result más idiomáticos).
                 * Aquí mostramos un mensaje claro al usuario.
                 */
                System.out.println("❌ Entrada no válida. Por favor, ingresa un número.");
            }
        }
        System.out.println("¡Gracias por usar GeoNotes! 👋");


        // MIS PRUEBAS
        LegacyPoint lgcyPnt = new LegacyPoint(10, 20);
        LegacyPoint lgcyPnt2 = new LegacyPoint(10, 20);
        System.out.println(lgcyPnt.hashCode());
        System.out.println(lgcyPnt2.hashCode());

        System.out.println(lgcyPnt2);

        GeoPoint geoPoint = new GeoPoint(10, 20);
        GeoPoint geoPoint2 = new GeoPoint(10, 20);
        System.out.println(geoPoint.hashCode());
        System.out.println(geoPoint2.hashCode());

        System.out.println(geoPoint2);


    }

    private static void printMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Crear una nueva nota");
        System.out.println("2. Listar todas las notas");
        System.out.println("3. Filtrar notas por palabra clave");
        System.out.println("4. Exportar notas a JSON (Text Blocks)");
        System.out.println("5. Exportar notas a Markdown");
        System.out.println("6. Busqueda avanzada");
        System.out.println("7. Listar las últimas notas");
        System.out.println("8. Obtener notas por ubicación");
        System.out.println("9. Listar notas en orden inverso");
        System.out.println("10. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void createNote() {
        System.out.println("\n--- Crear una nueva nota ---");

        // 'var' (Java 10) para inferencia local: útil para código más legible; en APIs públicas, mejor tipos explícitos.
        System.out.print("Título: ");
        var title = scanner.nextLine();
        System.out.print("Contenido: ");
        var content = scanner.nextLine();

        /*
         * Lectura robusta de números: ahora protegida con try/catch
         * para evitar errores si el usuario introduce texto.
         */
        double lat, lon;
        try {
            System.out.print("Latitud: ");
            lat = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Longitud: ");
            lon = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: introduce valores numéricos válidos para latitud y longitud.");
            return;
        }

        try {
            /*
             * RECORDS (Java 16):
             * - GeoPoint es un record con "compact constructor" que valida rangos (ver clase GeoPoint).
             * - Note también es record; su constructor valida title/location/createdAt.
             * Ventaja: menos boilerplate (constructor/getters/equals/hashCode/toString generados).
             */
            var geoPoint = new GeoPoint(lat, lon);

            /*
             * Instant.now() (java.time) para timestamps — la API java.time es la recomendada desde Java 8.
             * attachment lo dejamos a null en este flujo simple; podrías pedirlo al usuario.
             */
            var note = new Note(noteCounter++, title, content, geoPoint, Instant.now(), null);
            timeline.addNote(note);
            System.out.println("✅ Nota creada con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void obtenerNotasPorUbicacion(){
        System.out.println();
        System.out.println("--- FILTRAR NOTAS POR UBICACIÓN ---");
        System.out.println("Opciones disponibles");
        System.out.println("1. Equador");
        System.out.println("2. Origen");
        System.out.println("3. Greenwitch");
        System.out.println("Elige una opción:");

        ArrayList<Note> notasEncontradas = new ArrayList<>();

        String opcion;
        do {
            opcion = scanner.nextLine().trim();
            if (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3")){
                System.out.println("Has introducido una opción no válida, vuelve a intentarlo.");
            }
        } while (!opcion.trim().equals("1") && !opcion.trim().equals("2") && !opcion.trim().equals("3"));


        String opcionUbicacion = "";
        switch (opcion) {
            case "1": opcionUbicacion = "Equador"; break;
            case "2": opcionUbicacion = "Origen"; break;
            case "3": opcionUbicacion = "Greenwitch"; break;
        }


        for (Note notaConcreta : timeline.getNotes().values() ){
            if (Match.where(notaConcreta.location()) == opcionUbicacion){
                notasEncontradas.add(notaConcreta);
            }
        }
        System.out.println("--- Notas que coinciden con la ubicación deseada ---");
        if (notasEncontradas.isEmpty()){
            System.out.println("No hay notas encontradas");
        } else {
            for (Note nota : notasEncontradas){
                System.out.println(nota);
            }
        }

    }

    private static void listNotes() {
        System.out.println("\n--- Notas disponibles ---");
        if (timeline.getNotes().isEmpty()) {
            System.out.println("No hay notas creadas.");
            return;
        }

        /*
         * Bucle forEach sobre el Map<Long, Note>.
         * En Kotlin harías algo similar con forEach y String templates.
         */
        timeline.getNotes().forEach((id, note) -> {
            var gp = note.location();
            var region = Match.where(gp); // usamos record patterns
            var attachmentInfo = (note.attachment() == null)
                    ? "—"
                    : Describe.describeAttachment(note.attachment());
            System.out.printf("ID: %d | %s | %s | loc=%s | adj=%s%n",
                    id, note.title(), note.content(), region, attachmentInfo);
        });
    }


    private static void busquedaAvanzada() {
        System.out.println("\n");
        System.out.println("\n--- Busca avanzada ---");
        System.out.println("Elige una opción para filtrar:");
        System.out.println("1. Por rango de latitud / longitud");
        System.out.println("2. Por palabra clave en tittle o content");
        System.out.println("-----------------------------------------");
        System.out.println("Escriba su respuesta: ");

        Boolean respuestaValida = Boolean.FALSE;

        do {

            String opcion = scanner.next();

            switch (opcion) {
                case "1":
                    busquedaLatitudLongitud();
                    respuestaValida = Boolean.TRUE;
                    break;


                case "2":
                    busquedaPalabraClave();
                    respuestaValida = Boolean.TRUE;
                    break;

                default:
                    System.out.println("Has introducido una opción invalida, introduce una opción válida....");
                    break;
            }
        } while (!respuestaValida);


    }

    private static void busquedaLatitudLongitud() {


        System.out.println("Escriba el número inicial para buscar entre ese rango de valores:");
        String primerRangoFiltro = scanner.nextLine();


        String expresionRegularParaComprobarNumero = "^\\d{1,99}$";

        if (!primerRangoFiltro.matches(expresionRegularParaComprobarNumero)) {
            System.out.println("Has introducido valores no numericos en el rango de valores, vuelve a intentarlo:");
            primerRangoFiltro = scanner.nextLine();
        }


        System.out.println("Escriba el número final:");
        String segundoRangoFiltro = scanner.nextLine();


        if (!segundoRangoFiltro.matches(expresionRegularParaComprobarNumero)) {
            System.out.println("Has introducido valores no numericos en el rango de valores, vuelve a intentarlo:");
            segundoRangoFiltro = scanner.nextLine();
        }

        ArrayList<Note> listaNotasEncontradas = obtenerNotasEntreRangoDeLocalizacion(primerRangoFiltro, segundoRangoFiltro);
        mostrarNotasEncontradas(listaNotasEncontradas);


    }

    private static ArrayList<Note> obtenerNotasEntreRangoDeLocalizacion(String primerRangoFiltro, String segundoRangoFiltro) {
        ArrayList<Note> listaNotasCoincididas = new ArrayList<Note>();

        for (Note notaDeLista : timeline.getNotes().values()) {
            if (notaDeLista.location().lat() > Integer.parseInt(primerRangoFiltro) && notaDeLista.location().lat() < Integer.parseInt(segundoRangoFiltro)) {
                listaNotasCoincididas.add(notaDeLista);
            } else if (notaDeLista.location().lon() > Integer.parseInt(primerRangoFiltro) && notaDeLista.location().lon() < Integer.parseInt(segundoRangoFiltro)) {
                listaNotasCoincididas.add(notaDeLista);
            }
        }

        return listaNotasCoincididas;
    }


    private static void mostrarNotasEncontradas(ArrayList<Note> listaNotasCoincididas) {
        System.out.println("Lista de notas encontradas:");
        for (Note notaDeLista : listaNotasCoincididas) {
            System.out.println("Nota: ID:, " + notaDeLista.id() + ", Titulo: " + notaDeLista.title() + ", Contenido: " + notaDeLista.content() + ", Latitud:" + notaDeLista.location().lat() + ", Longitud:" + notaDeLista.location().lon());
        }

    }


    private static void busquedaPalabraClave() {



        System.out.println("Escriba una palabra para filtrar por ella entre los titulos y contenidos de las notas:");
        String palabraClaveEntrada = scanner.next();


        ArrayList<Note> listaNotasEncontradas = obtenerNotasPorNombre(palabraClaveEntrada);
        mostrarNotasEncontradas(listaNotasEncontradas);


    }

    private static ArrayList<Note> obtenerNotasPorNombre(String palabraClave) {
        ArrayList<Note> listaNotasCoincididas = new ArrayList<>();


        for (Note notaDeLista : timeline.getNotes().values()) {
            if (notaDeLista.content().contains(palabraClave)) {
                listaNotasCoincididas.add(notaDeLista);
            } else if (notaDeLista.title().contains(palabraClave)) {
                listaNotasCoincididas.add(notaDeLista);
            }
        }
        return listaNotasCoincididas;
    }


    private static void filterNotes() {
        System.out.print("\nIntroduce la palabra clave para filtrar: ");
        var keyword = scanner.nextLine();
        System.out.println("\n--- Resultados de búsqueda ---");

        /*
         * Streams (desde Java 8) — muy similares a las funciones de colección en Kotlin.
         * Filtramos por título o contenido y recogemos en una List inmutable (toList() desde Java 16 retorna una lista no modificable).
         */
        var filtered = timeline.getNotes().values().stream()
                .filter(n -> n.title().contains(keyword) || n.content().contains(keyword))
                .toList();
        if (filtered.isEmpty()) {
            System.out.println("No se encontraron notas con: " + keyword);
            return;
        }
        filtered.forEach(n -> System.out.printf("ID: %d | %s | %s%n",
                n.id(), n.title(), n.content()));
    }

    private static void exportNotesToJson() {
        /*
         * INNER CLASS NO ESTÁTICA:
         * - Timeline.Render es una clase interna "no estática" (inner class).
         * - Por eso se instancia con: timeline.new Render()
         * - Así Render queda LIGADA a ESTA instancia de Timeline (y accede a sus 'notes').
         *
         * Si Render fuera 'static', se instanciaría como 'new Timeline.Render(timeline)' pasando la Timeline explícita.
         */
        var renderer = timeline.new Render(); // ¿Por qué esto no funciona new Timeline().new Render();?

        /*
         * TEXT BLOCKS (Java 15) — ver Timeline.Render:
         * - Allí se usan literales de cadena multilínea """ ... """ para construir JSON legible.
         * - Se normaliza la indentación y no necesitas escapar comillas constantemente.
         */
        String json = renderer.export();

        System.out.println("\n--- Exportando notas a JSON ---");
        System.out.println(json);
    }

    private static void exportMarkdown() {
        MarkDownExporter exportarMarkdown = new MarkDownExporter(timeline);
        String NotasInMarkdown = exportarMarkdown.export();
        String[] listaNotas = NotasInMarkdown.split("\n");
        for (String nota : listaNotas) {
            System.out.println(nota);
        }
        return;
    }

    private static void seedExamples() {
        /*
         * Semilla de ejemplo para la tarea Gradle 'examples'.
         * También aquí vemos la jerarquía sellada (sealed) Attachment con tres records:
         *   Photo, Audio, Link — y cómo se pasan a Note como polimorfismo clásico.
         */
        timeline.addNote(new Note(noteCounter++, "Cádiz", "Playita",
                new GeoPoint(36.5297, -6.2927),
                Instant.now(),
                new Photo("u", 2000, 1000)));

        timeline.addNote(new Note(noteCounter++, "Sevilla", "Triana",
                new GeoPoint(37.3826, -5.9963),
                Instant.now(),
                new Audio("a", 320)));

        timeline.addNote(new Note(noteCounter++, "Córdoba", "Mezquita",
                new GeoPoint(37.8790, -4.7794),
                Instant.now(),
                new Link("http://cordoba", "Oficial")));        /*
         * DONDE VER EL RESTO DE NOVEDADES:
         * - Pattern matching para instanceof + switch con guardas 'when': ver Describe.
         * - Record patterns (Java 21): ver Match (desestructurar GeoPoint en switch/if).
         * - SequencedMap / reversed(): ver Timeline (versión moderna). En este “teaching” usamos LinkedHashMap clásico,
         *   pero explica a los alumnos que en Java 21 LinkedHashMap implementa SequencedMap y se puede pedir la vista invertida.
         * - Virtual Threads: demo aparte en el otro proyecto “moderno” (no se usan aquí).
         */


    }

    private static void ultimasNotas() {
        System.out.print("\nIntroduce el número de notas recientes a listar: ");
        try {
            int n = Integer.parseInt(scanner.nextLine().trim());
            var ultimasNotas = timeline.latest(n);
            if (ultimasNotas.isEmpty()) {
                System.out.println("Debes introducir un número mayor a 0.");
                return;
            }
            System.out.println("\n--- Últimas " + n + " notas ---");
            ultimasNotas.forEach(nota -> System.out.printf("ID: %d | %s | %s%n",
                    nota.id(), nota.title(), nota.content()));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Por favor, ingresa un número entero.");
        }
    }

    private static void listarNotasReversed() {
        System.out.println("\n--- Mostrando las notas en orden inverso ---");
        var notasInvertidas = timeline.reversed();

        if (notasInvertidas.isEmpty()) {
            System.out.println("No hay notas creadas todavía.");
            return;
        }

        for (var nota : notasInvertidas) {
            System.out.printf("ID: %d | %s | %s%n",
                    nota.id(), nota.title(), nota.content());
        }
    }

}