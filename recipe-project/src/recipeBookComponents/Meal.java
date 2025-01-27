package recipeBookComponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;


import recipeBookUtilities.Alphabetizer;

/**
 * Class for internally manipulating the Meals seen in MealEditor and in other classes.
 * 
 * @author Gideon Agyemang Prempeh
 * @version 4.0
 */
public class Meal implements Serializable
{
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  private static final long serialVersionUID = 1L;
  private ArrayList<Recipe> recipes;
  private String name;
  // added for internationalization
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  /**
   * Constructor for Meal.
   * 
   * @param name is the name of the meal
   **/
  public Meal(final String name)
  {
    new HashMap<String, ArrayList<Recipe>>();
    this.name = name;
    this.recipes = new ArrayList<Recipe>();
  }

  /**
   * Add recipe to meal.
   * 
   * @param recipe is the input recipe
   */
  public void addRecipe(final Recipe recipe)
  {
    recipes.add(recipe);
    // meals.put(name, recipes);
  }

  /**
   * get recipe from meal.
   * 
   * @return name of meal
   */
  public ArrayList<Recipe> getRecipe()
  {
    return recipes;
  }

  /**
   * Gets and alphabetizes ingredients in recipies.
   * 
   * @return ArrayList<Ingredient>
   */
  public ArrayList<Ingredient> getIngredients()
  {
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    for (Recipe recipe : this.recipes)
    {
      for (Ingredient ingredient : recipe.getIngredients())
      {
        ingredients.add(ingredient);
      }
    }

    Collections.sort(ingredients, new Alphabetizer());
    return ingredients;
  }

  /**
   * Gets the list of ingredients for a recipe.
   * 
   * @param recipe The recipe of each ingredient
   * @return a Object array of ingredients in a recipe
   */
  public ArrayList<Ingredient> ingredientforMeal(final Recipe recipe)
  {
    ArrayList<Ingredient> ingredient = new ArrayList<Ingredient>();
    for (int i = 0; i < recipe.getIngredients().size(); i++)
    {
      ingredient.add(recipe.getIngredients().get(i));
    }
    Collections.sort(ingredient, new Alphabetizer());
    return ingredient;
  }

  /**
   * Gets the name of a meal.
   * 
   * @return a String of the meal name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Removes a recipe from the list.
   * 
   * @param recipeToRemove
   *          the recipe you want removed
   */
  public void removeRecipe(final Recipe recipeToRemove)
  {
    recipes.remove(recipeToRemove);
  }

  /**
   * Sets the name of the Meal.
   * 
   * @param newName
   *          a string meal name
   */
  public void setName(final String newName)
  {
    this.name = newName;
  }
}
