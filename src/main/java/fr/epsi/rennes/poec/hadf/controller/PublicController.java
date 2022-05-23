package fr.epsi.rennes.poec.hadf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import fr.epsi.rennes.poec.hadf.domain.User;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;
import fr.epsi.rennes.poec.hadf.service.UserService;

@Controller
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/public/register")
	public String register(User user) throws BusinessException {
		userService.addUser(user);
		return "redirect:/login";
	}

}
