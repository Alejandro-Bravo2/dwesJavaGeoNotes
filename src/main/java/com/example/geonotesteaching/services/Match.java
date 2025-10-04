package com.example.geonotesteaching.services;

import com.example.geonotesteaching.geo.GeoArea;
import com.example.geonotesteaching.geo.GeoPoint;

// Utilidades de “matching” y geolocalización.
// Un 'record' que contiene un método para usar 'record patterns'.
// El 'record pattern' permite desestructurar un record directamente en los parámetros
// de un método o en un 'if' o 'switch', lo que es muy útil para la validación y el filtrado.
public final class Match {
    public static boolean isInArea(GeoPoint point, GeoArea area) {
        return point.lat() >= area.topLeft().lat() &&
                point.lat() <= area.bottomRight().lat() &&
                point.lon() >= area.topLeft().lon() &&
                point.lon() <= area.bottomRight().lon();
    }
    /**
     * Versión didáctica estilo “record patterns” (Java 21) para ubicar un punto en regiones lógicas.
     * Utiliza:
     * - Switch como expresión (devuelve un valor directamente).
     * - Record pattern: desestructura el record GeoPoint en lat y lon.
     * - Guardas (when): añaden condiciones adicionales a cada caso.
     */
    public static String where(GeoPoint p){
        return switch (p) {
            case GeoPoint(double lat, double lon) when lat== 0 && lon== 0 -> "ORIGIN";
            case GeoPoint(double lat, double lon) when lat== 0 -> "Equator";
            case GeoPoint(double lat, double lon) when lon== 0 -> "Greenwich";
            case GeoPoint(double lat, double lon) -> "(" + lat + "," + lon + ")";
        };
    }
}