//package com.repetentia.component.log;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.repetentia.support.log.Marker;
//import com.repetentia.web.startup.WebApplication;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//@SpringBootTest(classes = WebApplication.class)
//public class LogTest {
//
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	public void setup() {
//		this.mockMvc = MockMvcBuilders.standaloneSetup(new FilterTestController())
//				.addFilter(new CharacterEncodingFilter("UTF8", true, true))
////				.addFilter(new RtaLoggingFilter())
//				.build();
//	}
//
//	@Test
//	public void test() throws JsonProcessingException, Exception {
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode content = mapper.createObjectNode();
//		content.put("name", "제목신규");
//		content.put("age", 3);
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.add("Accept", "application/json;charset=UTF8");
//		ResultActions ra = mockMvc.perform(
//				post("/test")
//				.headers(httpHeaders)
//				.content(mapper.writeValueAsString(content)).contentType(MediaType.APPLICATION_JSON));
//		MvcResult result = ra.andDo(print())
//							.andExpect(status().isOk())
//							.andReturn();
//		String str = result.getResponse().getContentAsString();
//		log.info(Marker.TEST, "# {}", str);
//	}
//}
