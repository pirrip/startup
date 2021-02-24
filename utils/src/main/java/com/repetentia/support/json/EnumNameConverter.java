package com.repetentia.support.json;

import com.fasterxml.jackson.databind.util.StdConverter;

@SuppressWarnings("rawtypes")
public class EnumNameConverter extends StdConverter<Enum, String>{

    @Override
    public String convert(Enum value) {

        return null;
    }

}
