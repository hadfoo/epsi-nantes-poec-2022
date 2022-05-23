package fr.epsi.rennes.poec.hadf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.rennes.poec.hadf.domain.Article;
import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;
import fr.epsi.rennes.poec.hadf.service.ArticleService;

@RestController
public class AdminController {
	
	@Autowired
	private ArticleService articleService;
	
	@PostMapping("/admin/article")
	public Response<Void> addArticle(@RequestBody Article article) throws BusinessException {
		articleService.addArticle(article);
		return new Response<>(true);
	}

}
