package com.sanid.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanid.pojo.UserAccount;
import com.sanid.service.UserService;

@Controller
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void loadFormData(Model model) {
		UserAccount useraccobj = new UserAccount();
		model.addAttribute("userAcc", useraccobj);
		Map<Integer, String> countriesMap = userService.loadCountries();
		model.addAttribute("countries", countriesMap);
	}

	@GetMapping(value={"/register"})
	public String loadRegForm(Model model) {
		// TODO: We should write logic here
		
		return "registration";
	}

	@GetMapping("/uniqueMailCheck")
	public @ResponseBody String isEmailUnique(@RequestParam("Email") String Email) {
		String response = "";
		boolean isUnique = userService.isUniqueEmail(Email);
		return userService.isUniqueEmail(Email) ? "UNIQUE":"DUPLICATE" ;
		/*if(isUnique) {
			response="UNIQUE";
		}else {
			response="DUPLICATE";
		}
		
		return response;*/
	}

	@GetMapping("/countryChange")
	public @ResponseBody Map<Integer, String> handleCountryChangeEvnt(@RequestParam("CId") Integer countryId) {
		Map<Integer, String> statesMap = null;
		// TODO: We should write logic here
		return userService.loadStatesByCountryId(countryId);
			
	}

	@GetMapping("/stateChange")
	public @ResponseBody Map<Integer, String> handleStateChangeEvnt(@RequestParam("stateId") Integer stateId) {
		Map<Integer, String> citiesMap = null;
		// TODO: We should write logic here
		return userService.loadCitiesByStateId(stateId);
		
	}

	@PostMapping("/userRegistration")
	public String handleRegisterBtn(UserAccount userAcc, RedirectAttributes reat) {
		// TODO: We should write logic here
		boolean isSaved = userService.saveUserAccount(userAcc);
		
		if(isSaved) {
			reat.addFlashAttribute("succMsg", "Registration Successful");
		}else {
			reat.addFlashAttribute("failMsg", "Registration failed");
		}
		
		
		
		return "redirect:/register";
	}

}