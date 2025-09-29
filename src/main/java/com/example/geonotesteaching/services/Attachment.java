package com.example.geonotesteaching.services;

import com.example.geonotesteaching.model.Audio;
import com.example.geonotesteaching.model.Link;
import com.example.geonotesteaching.model.Photo;

// Una 'sealed interface' permite controlar qué clases o records pueden implementarla.
// Esto es útil para modelar jerarquías cerradas y seguras.
public sealed interface Attachment permits Photo, Audio, Link {
}