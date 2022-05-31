package fr.epsi.rennes.poec.hadf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.hadf.domain.Article;
import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;
import fr.epsi.rennes.poec.hadf.service.ArticleService;
import fr.epsi.rennes.poec.hadf.service.IngredientService;

@RestController
public class AdminController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@PostMapping("/admin/article")
	public Response<Void> addArticle(@RequestBody Article article) throws BusinessException {
		articleService.addArticle(article);
		return new Response<>(true);
	}
	
	@PostMapping("/admin/ingredient")
	public Response<Void> addIngredient(@RequestBody Ingredient ingredient)
			throws BusinessException {
		
		ingredientService.addIngredient(ingredient);
		
		return new Response<>(true);
	}

}
