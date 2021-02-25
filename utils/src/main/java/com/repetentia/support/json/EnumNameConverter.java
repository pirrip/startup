package com.repetentia.support.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.repetentia.component.code.RtaEnumType;

public class EnumNameConverter extends StdConverter<String, RtaEnumType> {

    @Override
    public RtaEnumType convert(String value) {
//        return UrlSe.fromCode(value);
        return null;
    }

}
