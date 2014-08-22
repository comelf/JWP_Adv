package org.jwp.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jwp.dao.UserDao;
import org.jwp.domain.Authenticate;
import org.jwp.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/form")
	public String createForm(Model model){
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
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String update(@Valid User user, BindingResult bindingResult, HttpSession session){
		log.debug("User : {}",user);
		
		if(bindingResult.hasErrors()){
			log.debug("Binding Result has Error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors){
				log.debug("error : {}", error.getDefaultMessage());
			}
			return "form";
		}
		
		Object temp = session.getAttribute("userId");
		if(temp ==null){
			throw new NullPointerException();
		}
		
		userDao.update(user);
		log.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
	@RequestMapping("/login/form")
	public String loginForm(Model model){
		model.addAttribute("authenticate", new Authenticate());
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult,HttpSession session, Model model){
		if (bindingResult.hasErrors()){
			return "login";
		}
		
		User user = userDao.findById(authenticate.getUserId());
		
		if(bindingResult.hasErrors()){
			log.debug("Binding Result has Error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors){
				log.info("error : {}", error.getDefaultMessage());
			}
			return "login";
		}
		
		if(user==null) {
			//user가 존재 하지 않을때 
			model.addAttribute("errorMessage", "사용자가 존재하지 않습니다.");
			return "login";
		}
		
		if(user.matchPassword(authenticate)){
			//비밀번호가 다름
			model.addAttribute("errorMessage", "비밀번호가 틀립니다.");
			return "login";
		}
		
		session.setAttribute("userId", user.getUserId());
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.removeAttribute("userId");
		return "redirect:/";
	}
	
	@RequestMapping("{uid}/form")
	public String updateForm(@PathVariable("uid") String userId, Model model){
		if(userId==null){
			throw new IllegalArgumentException("사용자 아이디가 필요합니다.");
		}
		
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		
		return "form";
	}
}
