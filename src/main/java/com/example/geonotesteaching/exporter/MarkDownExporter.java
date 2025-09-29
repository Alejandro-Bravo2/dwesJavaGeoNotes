package com.example.geonotesteaching.exporter;


import com.example.geonotesteaching.model.Note;
import com.example.geonotesteaching.Timeline;

public final class MarkDownExporter implements Exporter {

    private Timeline timeline;

    public MarkDownExporter(Timeline timeline){
        this.timeline = timeline;
    }

    @Override
    public String export() {
        String markdownNotas = "";
        markdownNotas += "# GeoNotes\n";
        for (Note nota : timeline.getNotes().values()){
            markdownNotas+= "- [ID "+nota.id()+"] "+ nota.title()+ " - ("+nota.location().lat()+","+nota.location().lon()+") - "+nota.createdAt()+"\n" ;
        }
        return markdownNotas;
    }
}
