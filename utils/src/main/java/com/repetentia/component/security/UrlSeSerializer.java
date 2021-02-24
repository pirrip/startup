package com.repetentia.component.security;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UrlSeSerializer extends StdSerializer<UrlSe> {
    private static final long serialVersionUID = 1398297298244051255L;

    public UrlSeSerializer() {
        super(UrlSe.class);
    }

    public UrlSeSerializer(Class<UrlSe> t) {
        super(t);
    }

    @Override
    public void serialize(UrlSe urlSe, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(urlSe.value());
    }
}
