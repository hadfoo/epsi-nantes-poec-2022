package fr.epsi.rennes.poec.hadf.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import fr.epsi.rennes.poec.hadf.dao.ArticleDAO;
import fr.epsi.rennes.poec.hadf.domain.Article;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;

@SpringBootTest
public class ArticleServiceTest {
	
	@InjectMocks
	private ArticleService articleService;
	
	@Mock
	private ArticleDAO articleDAOMock;
	
	@Test
	public void getArticleByCode_AlphaNum() throws BusinessException {
		// given
		String code = "ABC123";
		Article article = new Article();
		article.setCode(code);
		
		// when
		Mockito
			.when(articleDAOMock.getArticleByCode(code))
			.thenReturn(article);
		
		// then
		Article result = articleService.getArticleByCode(code);
		Assertions.assertEquals(code, result.getCode());
	}
	
	@Test
	public void getArticleByCode_AlphaMinus() {
		// given
		String code = "abc123";
		Article article = new Article();
		article.setCode(code);
		
		// when
		Mockito
			.when(articleDAOMock.getArticleByCode(code))
			.thenReturn(article);
		
		// then
		try {
			articleService.getArticleByCode(code);
			Assertions.fail();
		} catch (BusinessException e) {
			Assertions.assertEquals("article.get.code.invalid", e.getCode());
		}
	}

}
