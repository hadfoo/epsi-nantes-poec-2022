package fr.epsi.rennes.poec.hadf.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.epsi.rennes.poec.hadf.domain.Article;
import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.exception.TechnicalException;

@Repository
public class ArticleDAO {
	
	@Autowired
	private DataSource ds;
	
	public List<Article> getArticles() {
		String sql = "select * from article";
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setCode(rs.getString("code"));
				article.setLabel(rs.getString("label"));
				article.setPrix(rs.getDouble("prix"));
				
				result.add(article);
			}
			return result;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void addArticle(Article article) {
		String sql = String.format(
				"insert into article (code, label, prix) values ('%s', '%s', '%s')",
				article.getCode(), article.getLabel(), article.getPrix());
		
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public Article getArticleByCode(String articleCode) {
		String sql = String.format(
				"select " +
					"article.id as articleId, " +
					"article.code as articleCode, " +
					"article.label as articleLabel,  " +
					"ingredient.id as ingredientId, " +
					"ingredient.code as ingredientCode, " +
					"ingredient.label as ingredientLabel, " +
					"ingredient.prix as ingredientPrix " +
				"from article " +
				"left join article_ingredient " +
					"on article_ingredient.article_id = article.id " +
				"left join ingredient " +
					"on ingredient.id = article_ingredient.ingredient_id " +
				"where article.code = '%s'", articleCode);
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			Article article = null;
			while (rs.next()) {
				if (article == null) {
					article = new Article();
					article.setIngredients(new ArrayList<>());
					article.setId(rs.getInt("articleId"));
					article.setCode(rs.getString("articleCode"));
					article.setLabel(rs.getString("articleLabel"));
				}
				String ingredientCode = rs.getString("ingredientCode");
				if (ingredientCode != null) {
					Ingredient ingredient = new Ingredient();
					ingredient.setId(rs.getInt("ingredientId"));
					ingredient.setCode(ingredientCode);
					ingredient.setLabel(rs.getString("ingredientLabel"));
					ingredient.setPrix(rs.getDouble("ingredientPrix"));
					article.getIngredients().add(ingredient);
				}
			}
			return article;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}
	
	public void addIngredientToArticle(int articleId, int ingredientId) {
		String sql = String.format(
				"insert into article_ingredient " +
				"(article_id, ingredient_id) values (%s, %s)",
				articleId, ingredientId);
		
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

}
