package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import recipeBookComponents.Ingredient;
import recipeBookComponents.IngredientCalories;
import recipeBookComponents.Meal;
import recipeBookComponents.Recipe;
import recipeBookComponents.ShoppingList;

class ShoppingListTest
{

  @Test
  void testShoppingList()
  {
    // Setup ingredients, recipes, and meal examples
    Ingredient alcohol = new Ingredient("Alcohol", "", 1.5, "Cups");
    Ingredient chicken = new Ingredient("Chicken", "", 2.0, "Pounds");
    Ingredient honey = new Ingredient("Honey", "", 1.0, "Teaspoon");
    Recipe sweetChicken = new Recipe("Sweet Chicken", 2);
    sweetChicken.addIngredient(alcohol);
    sweetChicken.addIngredient(chicken);
    sweetChicken.addIngredient(honey);

    Ingredient celery = new Ingredient("Celery", "chopped", 1.5, "Cups");
    Ingredient syrup = new Ingredient("Syrup", "", 0.5, "Cups");
    Recipe sweetCel = new Recipe("Sweet Celery", 2);
    sweetCel.addIngredient(celery);
    sweetCel.addIngredient(syrup);

    celery = new Ingredient("Celery", "chopped", 2.0, "Cups");
    Ingredient peppers = new Ingredient("Pepper", "chopped", 1.0, "Cups");
    Ingredient spin = new Ingredient("Spinach", "washed", 1.5, "Cups");
    Recipe salad = new Recipe("Salad", 4);
    salad.addIngredient(spin);
    salad.addIngredient(peppers);
    salad.addIngredient(celery);

    Meal sweetMeal = new Meal("Sweet Meal");
    sweetMeal.addRecipe(sweetCel);
    sweetMeal.addRecipe(sweetChicken);
    sweetMeal.addRecipe(salad);

    // Create shopping list examples
    ShoppingList slMeal = new ShoppingList(sweetMeal);
    ShoppingList slRecipe = new ShoppingList(sweetChicken);
    ArrayList<Recipe> recipe = new ArrayList<Recipe>();
    recipe.add(sweetChicken);

    ArrayList<String> names = new ArrayList<String>();
    for (Ingredient ing : slMeal.getShoppingList())
    {
      String name = ing.toString();
      names.add(name);
    }

    // Test shopping list
    ArrayList<Ingredient> ingreds = new ArrayList<Ingredient>();
    celery.setAmount(3.5);
    ingreds.add(alcohol);
    ingreds.add(celery);
    ingreds.add(chicken);
    ingreds.add(honey);
    ingreds.add(peppers);
    ingreds.add(spin);
    ingreds.add(syrup);
    ArrayList<String> names2 = new ArrayList<String>();
    for (Ingredient ing : ingreds)
    {
      String name = ing.toString();
      names2.add(name);
    }
    assertEquals(slRecipe.getShoppingList(), sweetChicken.getIngredients());
    assertEquals(slMeal.getName(), "Sweet Meal");
    assertEquals(slRecipe.getRecipes(), recipe);
    assertEquals(names, names2);

  }
  
  @Test
  void testIngredient()
  {
    Ingredient alcohol = new Ingredient("Alcohol", "", 1.5, "Cups");
    alcohol.setCalsPerGram(275);
    alcohol.setGramsPerMl(0.79);

    assertEquals(alcohol.getGramsPerMl(), 0.79);
    assertEquals(alcohol.getCalsPerGram(), 2.75);
    assertEquals(alcohol.getHasCalInfo(), true);
    assertEquals(alcohol.toStringShopping(), " Cups of  Alcohol");
   
    alcohol.setHasCalInfoFalse();
    assertEquals(alcohol.getHasCalInfo(), false);
 
    alcohol.setHasCalInfoTrue();
    assertEquals(alcohol.getHasCalInfo(), true);

    alcohol.setHasCalInfo(false);
    assertEquals(alcohol.getHasCalInfo(), false);
    assertEquals(alcohol.toStringShopping(), " Cups of  Alcohol");


  }


 }
