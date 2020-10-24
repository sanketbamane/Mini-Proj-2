package com.sanid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanid.pojo.UnlockAccount;
import com.sanid.pojo.UserAccount;
import com.sanid.service.UserService;

@Controller
public class UnlockAccountController {
	
	@Autowired
	private UserService userService;
	/**
	 * This method is used load unlock-account form
	 * 
	 * @param email
	 * @param model
	 * @return String
	 */
	@GetMapping("/unlockAcc")
	public String loadUnlockAccForm(@RequestParam("email") String email, Model model) {
		// TODO: We should write logic here
		UnlockAccount account = new UnlockAccount();
		account.setEmail(email);
		
		model.addAttribute("userAcc", account);
		return "unlockAcc";
	}

	/**
	 * This method is used to handle unlock-account form submission
	 * 
	 * @param unlockAcc
	 * @param model
	 * @return String
	 */
	@PostMapping("/unlockAccount")
	public String handleSubmitBtn(@ModelAttribute("userAcc") UnlockAccount unlockAcc, Model model) {
		// TODO: We should write logic here
		boolean isValid = userService.isTempPwdValid(unlockAcc.getEmail(), unlockAcc.getTempPwd());
		
		if(isValid) {
			userService.unlockAccount(unlockAcc.getEmail(), unlockAcc.getNewPwd());
			model.addAttribute("succMsg", "Your account unlocked successfully. <a href=\"index\">Login Here</a>" );
		} else {
			model.addAttribute("failMsg", "temp password is incorrect enter correct pass.");
		}
		
		return "unlockAcc";
	}

}
