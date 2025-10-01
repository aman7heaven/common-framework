package com.autopilot.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Utility class for JSON serialization and deserialization using Gson.
 * Author: Shashwat Singh
 */
public class JsonUtils {

    // Constructor to initialize the Gson instance
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                @Override
                public void write(JsonWriter out, LocalDate value) throws IOException {
                    out.value(value != null ? value.toString() : null);
                }

                @Override
                public LocalDate read(JsonReader in) throws IOException {
                    String dateStr = in.nextString();
                    return LocalDate.parse(dateStr);
                }
            })
            .registerTypeAdapter(OffsetDateTime.class, new TypeAdapter<OffsetDateTime>() {
                @Override
                public void write(JsonWriter out, OffsetDateTime value) throws IOException {
                    out.value(value != null ? value.toInstant().toEpochMilli() : null);
                }

                @Override
                public OffsetDateTime read(JsonReader in) throws IOException {
                    long milliseconds = in.nextLong();
                    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);
                }
            })
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass() == File.class; // Exclude all fields of File class
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    // Method to serialize an object to JSON
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // Method to deserialize JSON to an object of specified type
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
