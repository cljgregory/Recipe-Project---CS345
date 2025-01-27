package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import recipeBookComponents.Ingredient;
import recipeBookComponents.IngredientCalories;

class IngredientTest
{

 
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
    
    // Testing the IngredientsCalories
    ArrayList<String> ings = new ArrayList<String>();
    for (IngredientCalories in : IngredientCalories.getIngredients())
    {
      String name = in.getName();
      ings.add(name);
    }
    
    ArrayList<String> ings2 = new ArrayList<String>();
    for (String in : IngredientCalories.getIngredientsToString())
    {
      ings2.add(in);
    }
    
    assertEquals(ings, ings2);

  }

}
