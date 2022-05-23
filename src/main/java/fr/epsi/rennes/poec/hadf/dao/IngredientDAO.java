package fr.epsi.rennes.poec.hadf.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IngredientDAO {
	
	@Autowired
	private DataSource ds;

}
