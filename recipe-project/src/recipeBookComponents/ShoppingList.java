package recipeBookComponents;

import java.util.ArrayList;

import recipeBookUtilities.ConvertUtil;

/**
 * Class representing the shopping list which will be displayed in the shopping list viewer.
 * 
 * @author Nick Walla
 * @version 03/31/2023
 *
 */
public class ShoppingList
{
  private ArrayList<Ingredient> shoppingList;
  private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
  private String name;

  /**
   * Meal constructor for the ShoppingList object.
   * 
   * @param meal
   *          is the meal passed in to the ShoppingList
   */
  public ShoppingList(final Meal meal)
  {
    this.shoppingList = new ArrayList<Ingredient>();

    this.name = meal.getName();
    Ingredient previous = new Ingredient("gleeporksdlnks", "", 0.0, "");
    int index = 0;

    for (Recipe recipe : meal.getRecipe())
    {
      recipes.add(recipe);
    }
    for (Ingredient ingredient : meal.getIngredients())
    {
      if (ingredient.getName().equals(previous.getName()))
      {
        if (!ingredient.getUnit().equals(previous.getUnit()))
        {
          double convertedAmount = ConvertUtil.convert(ingredient.getAmount(), ingredient.getUnit(),
              previous.getUnit(), previous.getGramsPerMl());

          double amount1 = convertedAmount;
          double amount2 = this.shoppingList.get(index - 1).getAmount();
          this.shoppingList.get(index - 1).setAmount(amount1 + amount2);
        }
        else
        {
          double amount1 = ingredient.getAmount();
          double amount2 = this.shoppingList.get(index - 1).getAmount();
          this.shoppingList.get(index - 1).setAmount(amount1 + amount2);
        }
      }
      else
      {
        shoppingList.add(ingredient);
        previous = ingredient;
        index += 1;
      }

    }

  }

  /**
   * Recipe constructor for shopping list.
   * 
   * @param recipe
   */
  public ShoppingList(final Recipe recipe)
  {
    this.recipes.add(recipe);
    this.name = recipe.getName();
    this.shoppingList = recipe.getIngredients();

  }

  /**
   * Returns the name of the meal or recipe.
   * 
   * @return String
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Getter method for shoppingList attribute.
   * 
   * @return ArrayList<Ingredient>
   */
  public ArrayList<Ingredient> getShoppingList()
  {
    return this.shoppingList;
  }

  /**
   * Returns the recipes used in the shopping list. Used to get the servings for calculating amounts
   * in ShoppingListViewer.
   * 
   * @return ArrayList<Recipe>
   */
  public ArrayList<Recipe> getRecipes()
  {
    return this.recipes;
  }

}
