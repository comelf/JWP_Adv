package org.jwp.support;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jwp.dao.UserDao;
import org.jwp.web.UserController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UserController userController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(userController)
				.alwaysExpect(status().isMovedTemporarily()).build();
		
	}
	
	
	@Test
	public void createWhenValid() throws Exception {
		this.mockMvc.perform(post("/users")
				.param("userId", "wooo")
				.param("password", "woooo")
				.param("name", "woo")
				.param("email", "woo@woo.com"))
				.andDo(print())
				.andExpect(status().isMovedTemporarily())
				.andExpect(redirectedUrl("/"));
	}
	
	public void createWhenInvalid() throws Exception {
		this.mockMvc.perform(post("/users")
				.param("userId", "wooo")
				.param("password", "woooo")
				.param("name", "woo")
				.param("email", "woo@"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/users/form"));
	}
}
