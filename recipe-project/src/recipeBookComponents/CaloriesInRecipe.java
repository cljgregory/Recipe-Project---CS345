package recipeBookComponents;

import java.util.ArrayList;

/**
 * 
 * Calories In Recipe returns the amount of calories in a recipe from the ingredients.
 * 
 * @author Gideon Agyemang Prempeh
 * @version 4.0
 */

public class CaloriesInRecipe
{
  private Recipe recipe;
  @SuppressWarnings("unused")
  private ArrayList<Ingredient> ingredients;

  /**
   * Constructor for Calories In Recipe.
   * 
   * @param name
   *          is the name of the recipe
   **/
  public CaloriesInRecipe(final Recipe name)
  {
    this.recipe = name;
    this.ingredients = new ArrayList<Ingredient>();
  }

  /**
   * loops through the ingrdients and returns the amount of calories.
   * 
   * @return int The calories per gram
   */
  public double totalCalories()
  {
    double count = 0;
    for (Ingredient ingredient : this.recipe.getIngredients())
    {
      count += ingredient.getCalsPerGram() * ingredient.getGramsPerMl();
    }
    return count;
  }

}
