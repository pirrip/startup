package com.repetentia.component.security;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.repetentia.component.code.UrlSe;

public class UrlSeDeserializer extends StdDeserializer<UrlSe> {
    private static final long serialVersionUID = 442627383517316764L;

    protected UrlSeDeserializer(Class<UrlSe> vc) {
        super(vc);
    }

    @Override
    public UrlSe deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        return null;
    }
}
