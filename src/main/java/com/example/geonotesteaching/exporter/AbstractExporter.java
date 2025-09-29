package com.example.geonotesteaching.exporter;

import com.example.geonotesteaching.model.Timeline;

public abstract sealed class AbstractExporter implements Exporter permits JsonExporter, Timeline.Render {
    public abstract String export();
}