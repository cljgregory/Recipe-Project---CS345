package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import recipeBookComponents.*;

/**
 * Tests recipe class.
 * 
 * @author Dylan Marti
 * @version 3/30/23
 *
 */
class RecipeTest
{

  @Test
  void testConstructor()
  {
    // set up
    String action = "cook";
    Ingredient ingredient = new Ingredient("sugar", "fried", 2.5, "pinch");
    Utensil source = new Utensil("name", "details");
    Utensil destination = new Utensil("name", "details");
    double time = 3.5;
    Step step = new Step(action, ingredient, source, destination, "detail", time);
    Recipe recipe = new Recipe("recipe", 10);

    ArrayList list = new ArrayList();

    // test
    assertEquals("recipe", recipe.getName());
    assertEquals(10, recipe.getServings());
    assertEquals(list, recipe.getIngredients());
    assertEquals(list, recipe.getUtensils());
    assertEquals(list, recipe.getSteps());
  }

  @Test
  void testAlphabeticalOrder()
  {
    Recipe recipe = new Recipe("recipe", 10);

    Ingredient i1 = new Ingredient("sugar", "fried", 2.5, "pinch");
    Ingredient i2 = new Ingredient("butter", "fried", 2.5, "pinch");
    Ingredient i3 = new Ingredient("bread", "fried", 2.5, "pinch");

    recipe.addIngredient(i1);
    recipe.addIngredient(i2);
    recipe.addIngredient(i3);

    assertEquals(i3.getName(), recipe.getIngredients().get(0).getName());
    assertEquals(i2.getName(), recipe.getIngredients().get(1).getName());
    assertEquals(i1.getName(), recipe.getIngredients().get(2).getName());

    Utensil u1 = new Utensil("zname", "details");
    Utensil u2 = new Utensil("dname", "details");
    Utensil u3 = new Utensil("bname", "details");

    recipe.addUtensil(u1);
    recipe.addUtensil(u2);
    recipe.addUtensil(u3);

    // TODO add after utensils gets a getname() method
    // assertEquals(u3.getName(), recipe.getUtensils().get(0).getName());
    // assertEquals(u2.getName(), recipe.getUtensils().get(1).getName());
    // assertEquals(u1.getName(), recipe.getUtensils().get(2).getName());
  }

  @Test
  void testAddersandRemovers()
  {
    // set up
    String action = "cook";
    Ingredient ingredient = new Ingredient("sugar", "fried", 2.5, "pinch");
    Utensil utensil = new Utensil("name", "details");
    Utensil destination = new Utensil("name", "details");
    double time = 3.5;
    Step step = new Step(action, ingredient, utensil, destination, "detail", time);
    Recipe recipe = new Recipe("recipe", 10);

    // add
    recipe.addIngredient(ingredient);
    recipe.addStep(step);
    recipe.addUtensil(utensil);

    // test
    assertEquals(recipe.getIngredients().contains(ingredient), true);
    assertEquals(recipe.getSteps().contains(step), true);
    assertEquals(recipe.getUtensils().contains(utensil), true);

    // try to add duplicate (should return false)
    assertEquals(false, recipe.addIngredient(ingredient));
    assertEquals(false, recipe.addStep(step));
    assertEquals(false, recipe.addUtensil(utensil));

    // remove
    recipe.removeIngredient(ingredient);
    recipe.removeStep(step);
    recipe.removeUtensil(utensil);

    // test
    assertEquals(recipe.getIngredients().contains(ingredient), false);
    assertEquals(recipe.getSteps().contains(step), false);
    assertEquals(recipe.getUtensils().contains(utensil), false);
  }

  





  @Test
  public void testGetIngredientNames()
  {
    Recipe recipe = new Recipe("Pasta", 4);
    Ingredient ingredient1 = new Ingredient("tomato", "sliced", 4, "cups");
    Ingredient ingredient2 = new Ingredient("onion", "diced", 2, "oz");

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);

    ArrayList<String> ingredientNames = new ArrayList<>();
    ingredientNames.add("onion");
    ingredientNames.add("tomato");

    assertEquals(ingredientNames, recipe.getIngredientNames());
  }

  @Test
  public void testGetUtensilNames()
  {
    Recipe recipe = new Recipe("Pasta", 4);
    Utensil utensil1 = new Utensil("pot", "on High");
    Utensil utensil2 = new Utensil("pan", "on Medium");

    recipe.addUtensil(utensil1);
    recipe.addUtensil(utensil2);

    ArrayList<String> utensilNames = new ArrayList<>();
    utensilNames.add("pan");
    utensilNames.add("pot");

    assertEquals(utensilNames, recipe.getUtensilNames());
  }

}
