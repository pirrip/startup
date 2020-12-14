package com.repetentia.component.property;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.repetentia.web.startup.WebApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebApplication.class)
public class DatabaseProperty {
    @Test
    public void testDatabasePropertySourcePlaceHolderConfigurer() {
    }
}
