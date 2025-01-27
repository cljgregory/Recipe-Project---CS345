package testing;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import recipeBookComponents.Ingredient;
import recipeBookComponents.IngredientCalories;
import recipeBookComponents.MasterIngredientList;
import recipeBookComponents.Meal;
import recipeBookComponents.Recipe;
import recipeBookComponents.ShoppingList;
import recipeBookComponents.Step;
import recipeBookComponents.Utensil;
import recipeBookUtilities.Alphabetizer;

class TestRecipeBookComponents
{

  @Test
  void testShoppingList()
  {
    // Setup ingredients, recipes, and meal examples
    Ingredient alcohol = new Ingredient("Alcohol", "", 1.5, "Cups");
    Ingredient chicken = new Ingredient("Chicken", "", 2.0, "Pounds");
    Ingredient honey = new Ingredient("Honey", "", 1.0, "Teaspoons");
    Recipe sweetChicken = new Recipe("Sweet Chicken", 2);
    sweetChicken.addIngredient(alcohol);
    sweetChicken.addIngredient(chicken);
    sweetChicken.addIngredient(honey);

    Ingredient celery = new Ingredient("Celery", "chopped", 1.5, "Cups");
    Ingredient syrup = new Ingredient("Syrup", "", 0.5, "Cups");
    Recipe sweetCel = new Recipe("Sweet Celery", 2);
    sweetCel.addIngredient(celery);
    sweetCel.addIngredient(syrup);

    honey = new Ingredient("Honey", "", 0.5, "Tablespoons");
    celery = new Ingredient("Celery", "chopped", 2.0, "Cups");
    Ingredient peppers = new Ingredient("Pepper", "chopped", 1.0, "Cups");
    Ingredient spin = new Ingredient("Spinach", "washed", 1.5, "Cups");
    Recipe salad = new Recipe("Salad", 4);
    salad.addIngredient(spin);
    salad.addIngredient(peppers);
    salad.addIngredient(celery);
    salad.addIngredient(honey);

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
    honey.setAmount(2.500000003043262);
    honey.setUnit("Teaspoons");
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
    Ingredient chicken = new Ingredient("Chicken", "", 2.0, "Pounds");
    alcohol.setCalsPerGram(275);
    alcohol.setGramsPerMl(0.79);

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
    double num = alcohol.getCalsPerGram();
    assertEquals(true, num == 2.75);

    ArrayList<Ingredient> ingredList = new ArrayList<Ingredient>();
    ingredList = IngredientCalories.getIngredientsToIngredients();

    ArrayList<Ingredient> list = new ArrayList<Ingredient>();
    list.add(new Ingredient("Alcohol", "", 1.5, "Cups"));
    MasterIngredientList i = new MasterIngredientList();
    Ingredient ing = new Ingredient("kill yourself;", "", 1.5, "Cups");
    Ingredient ingt = new Ingredient("dfghj", "", 1.5, "Cups");
    Ingredient empty = null;

    i.addIngredient(alcohol);
    i.addIngredient(ing);
    i.addIngredient(alcohol);
    i.addIngredient(ingt);

    assertEquals(list.get(0).getName(), i.getIngredients().get(0).getName());
    i.addIngredient(new Ingredient("Alcohol", "", 1.5, "Cups"));
    i.addIngredient(alcohol);
    MasterIngredientList c = new MasterIngredientList();
    c.addIngredient(ing);
    c.addIngredient(chicken);

    
    

  }

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

    newMeal.setName("Lunch");
    assertEquals(newMeal.getName(), "Lunch");
  }

  @Test
  void testRecipes()
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
    sweetChicken.addIngredient(honey);
    sweetChicken.addIngredient(chicken);
    sweetChicken.addIngredient(alcohol);

    ArrayList<String> ingredientNames = new ArrayList<String>();
    for (Ingredient i : sweetChicken.getIngredients())
    {
      ingredientNames.add(i.getName());
    }

    Utensil spoon = new Utensil("spoon", "silver");
    ArrayList<Utensil> uts = new ArrayList<Utensil>();
    ArrayList<String> utsName = new ArrayList<String>();
    uts.add(spoon);
    for (Utensil name : uts)
    {
      utsName.add(name.getName());
    }

    Utensil nullUtensil = null;
    double time = 3.5;
    Step step = new Step("put", honey, nullUtensil, spoon, "", time);
    sweetChicken.addStep(step);
    ArrayList<Step> stp = new ArrayList<Step>();
    stp.add(step);

    sweetChicken.addUtensil(spoon);
    assertEquals(sweetChicken.getUtensils(), uts);
    assertEquals(sweetChicken.getUtensilNames(), utsName);
    assertEquals(spoon.toString(), "silver spoon");
    assertEquals(sweetChicken.getServings(), 2);
    assertEquals(ingredientNames, sweetChicken.getIngredientNames());
    assertEquals(stp, sweetChicken.getSteps());
    assertEquals(sweetChicken.addIngredient(alcohol), false);
    assertEquals(sweetChicken.addStep(step), false);
    assertEquals(sweetChicken.addUtensil(spoon), false);
    sweetChicken.removeIngredient(alcohol);
    assertEquals(sweetChicken.getIngredientsFromName("honey"), honey);
    assertEquals(sweetChicken.getIngredientsFromName("alcohol"), null);
    assertEquals(sweetChicken.removeStep(step), true);
    assertEquals(sweetChicken.removeUtensil(spoon), true);
    sweetChicken.setName("SweetChicken");
    sweetChicken.setServings(3);
    sweetChicken.setServings(2);
    sweetChicken.addPropertyChangeListener(null);
    sweetChicken.removePropertyChangeListener(null);
    
    // getIngredientsFromName(String)
    
    // add and remove PropertyChangeListener
  }

  // Step Tests done by Dylan Marti

  @Test
  void testConstructorAndGetters()
  {
    // set up
    String action = "cook";
    Ingredient ingredient = new Ingredient("sugar", "fried", 2.5, "pinch");
    Utensil source = new Utensil("name", "detail");
    Utensil destination = new Utensil("name", "detail");
    String detail = "this is some detail";
    double time = 3.5;
    Step step = new Step(action, ingredient, source, destination, detail, time);

    // test
    assertEquals(step.getAction(), action);
    assertEquals(step.getIngredient(), ingredient);
    assertEquals(step.getSourceUtensil(), source);
    assertEquals(step.getDestinationUtensil(), destination);
    assertEquals(step.getTime(), time, 0.00001);
    assertEquals(source.getDetails(), "detail");
  }

  @Test
  void testToStringIngredient()
  {
    Ingredient milk = new Ingredient("milk", "blah", 2.5, "bleh");
    Utensil utensil = new Utensil("milk", "blah");
    Utensil bowl = new Utensil("small bowl", "small");
    Utensil nullUtensil = null;
    Utensil saucepan = new Utensil("saucepan", "medium");
    double time = 3.5;

    // ingredient step
    Step step = new Step("put", milk, nullUtensil, bowl, "", time);
    assertEquals("Put the milk in the small bowl", step.toString());

  }

  @Test
  void testToStringSameUtensil()
  {
    Utensil saucepan = new Utensil("medium saucepan", "actually its huge");
    double time = 3.5;

    Step step = new Step("saute", null, saucepan, saucepan, "until tender but not brown", time);
    assertEquals("Saute the contents of the medium " + "saucepan until tender but not brown",
        step.toString());
  }

  @Test
  void testToStringDiffUtensil()
  {
    Ingredient nullIngredient = null;
    Utensil strainer = new Utensil("strainer", "");
    Utensil casserole = new Utensil("1-quart casserole", "giant");
    double time = 3.5;

    Step step = new Step("put", null, strainer, casserole, "", time);
    assertEquals("Put the contents of the strainer in the 1-quart casserole", step.toString());
  }

  @Test
  void testSetDetailsAndSetTime()
  {
    Ingredient nullIngredient = null;
    Utensil strainer = new Utensil("strainer", "");
    Utensil casserole = new Utensil("1-quart casserole", "giant");
    double time = 3.5;

    Step step = new Step("put", null, strainer, casserole, "", time);

    // test set detail
    step.setDetail("detail");
    assertEquals("detail", step.getDetail());

    step.setTime("20.2");
    assertEquals(20.2, step.getTime(), 0.001);

  }
}
