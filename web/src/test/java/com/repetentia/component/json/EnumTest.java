package com.repetentia.component.json;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.repetentia.component.code.UrlSe;
import com.repetentia.component.security.UrlSecurity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumTest {
    @Test
    public void serialize() {
        ObjectMapper om = new ObjectMapper();
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();

        try {
            String menu = ow.writeValueAsString(UrlSe.M);
            log.info("# Serialize Treat Enum as Object - {}", menu);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deserialize() {
        ObjectMapper om = new ObjectMapper();
        try {
            String json = "{"
                    + "  \"usergroup\" : \"USER\","
                    + "  \"url\" : \"/test\","
                    + "  \"method\" : \"GET\","
                    + "  \"usergroupNm\" : \"일부\","
                    + "  \"usergroupDesc\" : null,"
                    + "  \"enabled\" : true,"
                    + "  \"sqno\" : 2,"
                    + "  \"pqno\" : 2,"
                    + "  \"depth\" : 1,"
                    + "  \"site\" : \"rta\","
                    + "  \"menuSe\" : \"P\","
                    + "  \"menuNm\" : \"테스트\""
                    + "}";

            UrlSecurity se = om.readValue(json, UrlSecurity.class);
            log.info("# Deserialize from code to Enum - {}", se);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
