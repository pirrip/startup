package com.repetentia.component.log;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.repetentia.web.startup.WebApplication;
import com.repetentia.web.table.mapper.TableDmlProviderTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebApplication.class)
public class LogTest {

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new FilterTestController())
				.addFilter(new RtaLoggingFilter())
				.build();
	}

	@Test
	public void test() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode content = mapper.createObjectNode();
		content.put("name", "제목신규");
		content.put("age", 3);

		ResultActions ra = mockMvc.perform(
				post("/test").content(mapper.writeValueAsString(content)).contentType(MediaType.APPLICATION_JSON));
		log.info("# {}", ra);
		log.info("ending");
		
	}
}
