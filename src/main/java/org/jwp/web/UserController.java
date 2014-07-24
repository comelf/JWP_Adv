package org.jwp.web;

import java.util.List;

import javax.validation.Valid;

import org.jwp.dao.UserDao;
import org.jwp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/form")
	public String form(Model model){
		model.addAttribute("user", new User());
		return "form";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create(@Valid User user, BindingResult bindingResult){
		log.debug("User : {}",user);
		
		if(bindingResult.hasErrors()){
			log.debug("Binding Result has Error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors){
				log.debug("error : {}", error.getDefaultMessage());
			}
			return "form";
		}
		
		userDao.create(user);
		log.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
}
