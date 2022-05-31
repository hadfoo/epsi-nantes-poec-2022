package fr.epsi.rennes.poec.hadf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.hadf.dao.IngredientDAO;
import fr.epsi.rennes.poec.hadf.domain.Ingredient;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientDAO ingredientDAO;
	
	public List<Ingredient> getIngredients() {
		return ingredientDAO.getIngredients();
	}
	
	public void addIngredient(Ingredient ingredient) throws BusinessException {
		if (ingredient.getCode() == null
				|| ingredient.getLabel() == null
				|| ingredient.getPrix() == 0) {
			throw new BusinessException("ingredient.add.mandatory.missing");
		}
		ingredientDAO.addIngredient(ingredient);
	}

}
