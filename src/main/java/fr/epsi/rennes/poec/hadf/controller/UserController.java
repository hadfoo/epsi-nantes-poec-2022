package fr.epsi.rennes.poec.hadf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.hadf.domain.Article;
import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;
import fr.epsi.rennes.poec.hadf.service.ArticleService;

@RestController
public class UserController {
	
	@Autowired
	private ArticleService articleService;
	
	// commentaire sur une seule ligne
	/*
	   commentaire
	   sur plusieurs
	   lignes
	 */
	/**
	 * Commentaire javadoc
	 * http://localhost:8080/user/article?code=123
	 * 
	 * @param code
	 * @return
	 * @throws BusinessException
	 */
	@GetMapping("/user/article")
	public Response<Article> getArticleByCode(@RequestParam String code)
	throws BusinessException {
		return new Response<>(articleService.getArticleByCode(code));
	}
	
	@PostMapping("/user/article/ingredient")
	public Response<Void> addIngredientToArticle(
			@RequestParam int article_id,
			@RequestParam int ingredient_id) {
		
		articleService.addIngredientToArticle(article_id, ingredient_id);
		
		return new Response<>(true);
	}

}
