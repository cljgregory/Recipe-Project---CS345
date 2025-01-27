package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import recipeBookComponents.*;
import recipeBookUtilities.Alphabetizer;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class mealTest
{

  @Test
  void testMeals()
  {
    Ingredient alcohol = new Ingredient("Alcohol", "", 1.5, "Cups");
    Ingredient chicken = new Ingredient("Chicken", "", 2.0, "Pounds");
    Ingredient honey = new Ingredient("Honey", "", 1.0, "Teaspoon");
    Recipe sweetChicken = new Recipe("Sweet Chicken", 2);
    alcohol.setGramsPerMl(0.79);
    alcohol.setCalsPerGram(275);
    chicken.setGramsPerMl(1.04);
    chicken.setCalsPerGram(200);
    honey.setGramsPerMl(1.5);
    honey.setCalsPerGram(280);
    
    sweetChicken.addIngredient(alcohol);
    sweetChicken.addIngredient(chicken);
    sweetChicken.addIngredient(honey);
    
    Ingredient celery = new Ingredient("Celery", "chopped", 1.5, "Cups");
    Ingredient syrup = new Ingredient("Syrup", "", 0.5, "Cups");
    syrup.setCalsPerGram(260);
    syrup.setGramsPerMl(1.38);
    celery.setCalsPerGram(14);
    celery.setGramsPerMl(0.61);
    
    Recipe sweetCel = new Recipe("Sweet Celery", 2);
    sweetCel.addIngredient(celery);
    sweetCel.addIngredient(syrup);
    
    Meal newMeal = new Meal("Dinner");
    newMeal.addRecipe(sweetChicken);
    newMeal.addRecipe(sweetCel);
    
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    recipes.add(sweetChicken);
    recipes.add(sweetCel);
    
    assertEquals(recipes, newMeal.getRecipe());
    
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    for (Recipe recipe : recipes)
    {
      for (Ingredient in : recipe.getIngredients())
      {
        ingredients.add(in);
      }
    }
    
    Collections.sort(ingredients, new Alphabetizer());
    
    assertEquals(newMeal.getName(), "Dinner");
    assertEquals(ingredients, newMeal.getIngredients());
    assertEquals(sweetChicken.getIngredients(), newMeal.ingredientforMeal(sweetChicken));
    
    recipes.remove(sweetCel);
    newMeal.removeRecipe(sweetCel);
    assertEquals(recipes, newMeal.getRecipe());
    
    newMeal.setName("wack-a-mole!");
    assertEquals("wack-a-mole!", newMeal.getName());
  }

}
