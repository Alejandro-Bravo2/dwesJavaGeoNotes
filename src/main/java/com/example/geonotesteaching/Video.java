package com.example.geonotesteaching;

// Un 'record' para el video, que implementa la interfaz 'Attachment'.

public record Video(String url, String title) implements Attachment {
    public Video {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("url requerido");
        }
        url = url.trim();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("url debe empezar por http:// o https://");
        }

        if (title == null || title.isBlank()) {
            title = "–";
        } else {
            title = title.trim();
        }
    }
}
    
