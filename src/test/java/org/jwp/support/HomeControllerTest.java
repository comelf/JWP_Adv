package org.jwp.support;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.jwp.web.HomeController;


public class HomeControllerTest {
	
	@Test
	public void test() throws Exception {
		standaloneSetup(new HomeController()).build()
		.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("home"));
	}

}
