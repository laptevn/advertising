package com.laptevn.advertising.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class FloatSerializer extends JsonSerializer<Float> {
    @Override
    public void serialize(Float value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        if (value == null) {
            generator.writeNull();
        } else {
            generator.writeNumber(String.format("%.2f", value));
        }
    }
}