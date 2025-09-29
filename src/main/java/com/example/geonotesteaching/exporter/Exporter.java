package com.example.geonotesteaching.exporter;

import com.example.geonotesteaching.Timeline;

// Una 'sealed interface' para la jerarquía de exportadores.
// 'non-sealed' permite que otras clases fuera de este archivo la extiendan,
// mientras que 'final' impide cualquier otra extensión.
public sealed interface Exporter permits AbstractExporter, JsonExporter, MarkDownExporter, Timeline.Render {
    String export();
}