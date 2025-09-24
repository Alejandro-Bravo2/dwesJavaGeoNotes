package com.example.geonotesteaching;

import java.time.Instant;

// Un 'record' para la nota, que incluye un campo de tipo 'sealed interface'.
// El 'id' se genera automáticamente usando el hash code y el timestamp, aunque
// en un entorno real usaríamos un UUID.
public record Note(long id, String title, String content, GeoPoint location, Instant createdAt, Attachment attachment) {
    public Note {
        if (title == null) { 
            throw new IllegalArgumentException("title requerido");
        }

        title = title.trim();
        if (title.length() < 3) {
            throw new IllegalArgumentException("title debe tener 3 caracteres minimo");
        }
        if (content == null) content = "";
        content = content.trim();
        if (content.isEmpty()) {
            content = "–";
        }

        if (location == null) {
            throw new IllegalArgumentException("location requerido");
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}