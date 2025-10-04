package com.example.geonotesteaching.services;


import com.example.geonotesteaching.model.*;

// Esta clase usa 'switch expressions' y 'pattern matching' para describir un 'Attachment'.
// Los 'switch expressions' permiten que el 'switch' sea una expresión que devuelve un valor.
// El 'pattern matching' en el 'case' permite desestructurar el objeto y
// aplicar una condición ('when') de forma concisa.
public final class Describe {
    public static String describeAttachment(Attachment a) {
        return switch (a) {
            case Photo p when p.width() > 1920 ->
                    "📷 Foto en alta definición (%d x %d)".formatted(p.width(), p.height());
            case Photo p -> "📷 Foto";
            case Audio audio when audio.duration() > 300 -> {
                yield "🎵 Audio largo";
            }
            case Audio audio -> "🎵 Audio";
            case Link l -> "🔗 %s".formatted(l.effectiveLabel());
            case Video v when v.seconds() > 120 -> " Vídeo largo";
            case Video v -> "Vídeo";
            default -> throw new IllegalStateException("Unexpected value: " + a);
        };
    }
    public static int mediaPixels(Object o) {
        int result = 0;

        if (o instanceof Photo p) {
            result = p.width() * p.height();
        } else if (o instanceof Video v) {
            result = v.width() * v.height();
        }

        return result;

    }

}