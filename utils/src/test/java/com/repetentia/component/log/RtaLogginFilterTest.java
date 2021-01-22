package com.repetentia.component.log;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.repetentia.web.startup.config.LoggingFilterConfig;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {LoggingFilterConfig.class})
@WebAppConfiguration
public class RtaLogginFilterTest {
	protected MockMvc mockMvc;
	
	protected Object controller() {
		return new FilterTestController();
	}
	
	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller())
			.alwaysDo(print())
			.build();
	}
	
	@Test
	public void test() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode content = mapper.createObjectNode();
		content.put("title", "제목신규");
		content.put("content", "내용신규");
			
		mockMvc.perform(
			post("/board")
			.content(mapper.writeValueAsString(content))
			.contentType(MediaType.APPLICATION_JSON)
			);
		
		
	}
}
