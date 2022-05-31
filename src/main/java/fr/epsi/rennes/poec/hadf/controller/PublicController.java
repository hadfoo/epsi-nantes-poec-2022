package fr.epsi.rennes.poec.hadf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.epsi.rennes.poec.hadf.domain.Article;
import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.domain.User;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;
import fr.epsi.rennes.poec.hadf.service.ArticleService;
import fr.epsi.rennes.poec.hadf.service.IngredientService;
import fr.epsi.rennes.poec.hadf.service.UserService;

@Controller
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@PostMapping("/public/register")
	public String register(User user) throws BusinessException {
		userService.addUser(user);
		return "redirect:/login";
	}
	
	@GetMapping("/public/articles")
	@ResponseBody
	public Response<List<Article>> getArticles() {
		return new Response<>(articleService.getArticles());
	}
	
	@GetMapping("/public/ingredients")
	@ResponseBody
	public Response<List<Ingredient>> getIngredients() {
		return new Response<>(ingredientService.getIngredients());
	}

}
