package recipeBookUtilities;

import java.util.Comparator;

import recipeBookComponents.*;

/**
 * Helper to alphabetize Utensil and Ingredient with their getName() methods. This is used
 * internally to Recipe when either a Utensil or Ingredient is added.
 * 
 * @author Dylan Marti
 * @version 3/30/23
 */
public class Alphabetizer implements Comparator<Object>
{

  @Override
  /**
   * Helper to alphabetize Utensil and Ingredient with their getName() methods. This is used
   * internally to Recipe when either a Utensil or Ingredient is added.
   * 
   * @return int that indicates comparison result
   */
  public int compare(final Object o1, final Object o2)
  {
    int returnValue = -1;
    if (o1 instanceof Utensil && o2 instanceof Utensil)
    {
      Utensil u1 = (Utensil) o1;
      Utensil u2 = (Utensil) o2;
      returnValue = u1.getName().compareToIgnoreCase(u2.getName());
    }
    if (o1 instanceof Ingredient && o2 instanceof Ingredient)
    {
      Ingredient i1 = (Ingredient) o1;
      Ingredient i2 = (Ingredient) o2;
      returnValue = i1.getName().compareToIgnoreCase(i2.getName()); // Ignores Cases ~Ethan
    }
    return returnValue;
  }
}
