package com.saj.recipefinder.domain;
/**
 * @author Sajan
 *
 */
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Recipe {
	
	private Long id;
	private String name;
	private List<Ingredient> ingredients;
	
	public Recipe(){}
	
	public Recipe(Long id, String name, List<Ingredient> ingredients) {
		super();
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
	
	

}
