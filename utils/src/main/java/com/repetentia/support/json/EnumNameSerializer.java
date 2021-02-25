package com.repetentia.support.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.repetentia.component.code.UrlSe;

public class EnumNameSerializer extends StdSerializer<UrlSe> {

    protected EnumNameSerializer(Class<UrlSe> t) {
        super(t);
    }

    @Override
    public void serialize(UrlSe value, JsonGenerator gen, SerializerProvider provider) throws IOException {

    }

}
