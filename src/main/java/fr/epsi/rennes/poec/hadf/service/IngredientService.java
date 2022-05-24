package fr.epsi.rennes.poec.hadf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.epsi.rennes.poec.hadf.dao.IngredientDAO;
import fr.epsi.rennes.poec.hadf.domain.Ingredient;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientDAO ingredientDAO;
	
	public List<Ingredient> getIngredients() {
		return ingredientDAO.getIngredients();
	}

}
