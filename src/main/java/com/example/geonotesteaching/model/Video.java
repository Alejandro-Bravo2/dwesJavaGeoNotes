package com.example.geonotesteaching.model;

// Nuevo subtipo Video de Attachment
public record Video(String url, int width, int height, int seconds) implements Attachment {
    public Video {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("url requerido");
        }
        url = url.trim();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("La url debe empezar por http:// o https://");
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Dimensiones positivas requeridas");
        }
        if (seconds < 0) {
            throw new IllegalArgumentException("Los segundos no pueden ser negativos");
        }
        // Asignación implícita del constructor compacto:
        // this.url = url; this.width = width; this.height = height; this.seconds = seconds;
    }
}
