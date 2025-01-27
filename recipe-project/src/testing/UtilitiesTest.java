package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import recipeBookComponents.Ingredient;
import recipeBookComponents.Recipe;
import recipeBookComponents.Meal;
import utilities.Serializer;
import utilities.MealSerializer;

class UtilitiesTest
{

  @Test
  void testSerializer() throws IOException
  {
    Ingredient alcohol = new Ingredient("Alcohol", "", 1.5, "Cups");
    Ingredient chicken = new Ingredient("Chicken", "", 2.0, "Pounds");
    Ingredient honey = new Ingredient("Honey", "", 1.0, "Teaspoon");
    Recipe sweetChicken = new Recipe("Sweat_Chicken", 2);
    alcohol.setGramsPerMl(0.79);
    alcohol.setCalsPerGram(275);
    chicken.setGramsPerMl(1.04);
    chicken.setCalsPerGram(200);
    honey.setGramsPerMl(1.5);
    honey.setCalsPerGram(280);

    sweetChicken.addIngredient(alcohol);
    sweetChicken.addIngredient(chicken);
    sweetChicken.addIngredient(honey);

    Serializer.serializeRecipe(sweetChicken);
    try
    {
      Recipe recipe = Serializer.deserializeRecipe();
    }
    catch (ClassNotFoundException e)
    {

    }
    Serializer.resetFilePath();
    Recipe sweetCel = new Recipe("Sweet Celery", 2);
    sweetCel.addIngredient(honey);
    sweetCel.addIngredient(alcohol);
    Serializer.reSerializeRecipe(sweetCel);
    String s = Serializer.getCurrentFilePath();
    assertEquals(s, Serializer.getCurrentFilePath());
    Serializer.showOpenDialog();

  }

  @Test
  void testMealSerializer() throws IOException
  {
    Ingredient alcohol = new Ingredient("Alcohol", "", 1.5, "Cups");
    Ingredient chicken = new Ingredient("Chicken", "", 2.0, "Pounds");
    Ingredient honey = new Ingredient("Honey", "", 1.0, "Teaspoon");
    Recipe sweetChicken = new Recipe("Sweat_Chicken", 2);
    alcohol.setGramsPerMl(0.79);
    alcohol.setCalsPerGram(275);
    chicken.setGramsPerMl(1.04);
    chicken.setCalsPerGram(200);
    honey.setGramsPerMl(1.5);
    honey.setCalsPerGram(280);
    sweetChicken.addIngredient(alcohol);
    sweetChicken.addIngredient(chicken);
    sweetChicken.addIngredient(honey);

    Meal meal = new Meal("Sweet_Honey_Chicken");

    MealSerializer.serializeMeal(meal);

    try
    {
      Meal meal2 = MealSerializer.deserializeMeal();

    }
    catch (ClassNotFoundException e)
    {

    }

    Serializer.resetFilePath();
    Recipe sweetCel = new Recipe("Sweet Celery", 2);
    sweetCel.addIngredient(honey);
    sweetCel.addIngredient(alcohol);
    MealSerializer.reSerializeMeal(meal);
    String s = MealSerializer.getCurrentFilePath();
    assertEquals(s, MealSerializer.getCurrentFilePath());
    MealSerializer.showOpenDialog();
  }

}
