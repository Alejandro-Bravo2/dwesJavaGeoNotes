package com.example.geonotesteaching.exporter;

public final class JsonExporter extends AbstractExporter implements Exporter {
    private final String payload;

    public JsonExporter(String payload) {
        this.payload = payload;
    }

    public String export() {
        return payload;
    }
}