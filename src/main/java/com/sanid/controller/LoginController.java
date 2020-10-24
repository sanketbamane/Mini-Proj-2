package com.sanid.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sanid.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService service;
	/**
	 * This method is used to load login page
	 * 
	 * @return String
	 */
	@GetMapping(value = { "/","/index" })
	public String index() {
		return "index";
	}

	/**
	 * This method is used handle sign-in btn request
	 * 
	 * @param req
	 * @param model
	 * @return String
	 */
	@PostMapping("/signin")
	public String handleSignInBtn(HttpServletRequest req, Model model) {
		String viewName = "";
		String email = req.getParameter("userEmail");
		String pwd = req.getParameter("pwd");
		String status = service.loginCheck(email, pwd);
		if(status.equals("VALID")) {
			viewName="dashboard";
		}else {
			viewName="index";
			model.addAttribute("failMsg", status);
		}
		
		return viewName;
		
	}

}