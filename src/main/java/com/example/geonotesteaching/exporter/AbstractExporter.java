package com.example.geonotesteaching.exporter;


public abstract sealed class AbstractExporter implements Exporter permits JsonExporter, Timeline.Render {
    public abstract String export();
}