package com.repetentia.support.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.repetentia.component.code.UrlSe;

public class EnumNameDeserializer extends StdDeserializer<UrlSe> {

    private static final long serialVersionUID = -8551268384047038145L;

    protected EnumNameDeserializer(Class<UrlSe> vc) {
        super(vc);
    }

    @Override
    public UrlSe deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }

}
