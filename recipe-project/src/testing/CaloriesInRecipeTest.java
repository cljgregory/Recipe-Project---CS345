package testing;

import org.junit.Test;

import recipeBookComponents.CaloriesInRecipe;
import recipeBookComponents.Ingredient;
import recipeBookComponents.Recipe;

import static org.junit.Assert.*;

public class CaloriesInRecipeTest {

  @Test
  public void testTotalCalories() {
    // create ingredients
    Ingredient flour = new Ingredient("Flour", "All purpose", 2.5, "cups");
    Ingredient sugar = new Ingredient("Sugar", "White granulated", 1.5, "cups");
    Ingredient butter = new Ingredient("Butter", "Unsalted", 1, "cup");
    Ingredient eggs = new Ingredient("Eggs", "Large", 2, "units");
    Ingredient vanillaExtract = new Ingredient("Vanilla extract", "Pure", 2, "teaspoons");
    Ingredient bakingPowder = new Ingredient("Baking powder", "Double acting", 2, "teaspoons");
    Ingredient salt = new Ingredient("Salt", "Table salt", 0.5, "teaspoons");
    Ingredient milk = new Ingredient("Milk", "Whole milk", 1, "cup");
    
    // create recipe and add ingredients
    Recipe cake = new Recipe("Cake", 8);
    cake.addIngredient(flour);
    cake.addIngredient(sugar);
    cake.addIngredient(butter);
    cake.addIngredient(eggs);
    cake.addIngredient(vanillaExtract);
    cake.addIngredient(bakingPowder);
    cake.addIngredient(salt);
    cake.addIngredient(milk);
    
    CaloriesInRecipe caloriesInRecipe = new CaloriesInRecipe(cake);
    double actualTotalCalories = caloriesInRecipe.totalCalories();
    System.out.println(actualTotalCalories);
    
    // assert the calculated calories are correct
    assertEquals(12.991, actualTotalCalories, 0.1);

  }
}