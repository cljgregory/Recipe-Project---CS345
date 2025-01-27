package recipeBookComponents;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Enum representing an ingredient in a recipe.
 * 
 * @author Nick Walla
 * @version 4/14/23
 *
 */
public class Ingredient implements Serializable
{
  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }
  
  private static final String ONLY_FANS = "OF";

  private static final long serialVersionUID = 1L;
  private String name;
  private String details;
  private double amount;
  private String unit;
  private int calsPer100g;
  private double gPerMl;
  private boolean hasCalInfo;

  /**
   * Constructor for ingredient object. Requires all inputs to construct.
   * 
   * @param name
   *          is the ingredient name
   * @param details
   *          is the additional details
   * @param amount
   *          is the amount of the ingredient needed
   * @param unit
   *          is what unit the amount is in
   */
  public Ingredient(final String name, final String details, final double amount, final String unit)
  {
    MasterIngredientList list = new MasterIngredientList();
    this.name = name;
    this.details = details;
    this.amount = amount;
    this.unit = unit;
    this.hasCalInfo = IngredientCalories.hasCalorieInfo(this.name);
    if (hasCalInfo)
    {
      this.calsPer100g = IngredientCalories.getCalories(this.name);
      this.gPerMl = IngredientCalories.getGrams(this.name);
    }
    else
    {
      this.hasCalInfo = list.hasCalorieInfo(this.name);
      if (hasCalInfo)
      {
        this.calsPer100g = list.getCalories(this.name);
        this.gPerMl = list.getGrams(this.name);
      }
      this.calsPer100g = 0;
      this.gPerMl = 0;
    }
  }

  /**
   * Gets the number of calories per 100g.
   * 
   * @return double
   */
  public double getCalsPerGram()
  {
    double conversion = calsPer100g; // turns calsper100g to a double before being divided by 100
    return conversion / 100;
  }

  /**
   * Gets the density of the ingredient in grams per milliliter.
   * 
   * @return double
   */
  public double getGramsPerMl()
  {
    return this.gPerMl;
  }

  /**
   * Returns boolean value of weather or not the ingredient has cal info.
   * 
   * @return true if has info false if not
   */
  public boolean getHasCalInfo()
  {
    return this.hasCalInfo;
  }

  /**
   * Sets the calsPer100g to a given value.
   * 
   * @param value
   *          provided value that will set calsPer100g
   */
  public void setCalsPerGram(final int value)
  {
    this.calsPer100g = value;
    this.hasCalInfo = true;
  }

  /**
   * Setter method for gPerMl variable.
   * 
   * @param value
   *          to be assigned to gPerMl
   */
  public void setGramsPerMl(final double value)
  {
    this.gPerMl = value;
    this.hasCalInfo = true;
  }

  /**
   * Sets hasCalInfo as true.
   */
  public void setHasCalInfoTrue()
  {
    this.hasCalInfo = true;
  }

  /**
   * Sets hasCalInfo as false.
   */
  public void setHasCalInfoFalse()
  {
    this.hasCalInfo = false;
  }

  /**
   * Sets hasCalInfo as whatever is passed in.
   * 
   * @param condition
   *          if it has calorie info or not
   */
  public void setHasCalInfo(final boolean condition)
  {
    this.hasCalInfo = condition;
  }

  /**
   * Gets the name of the ingredient.
   * 
   * @return String of the name attribute
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Gets the additional details added by the user for the ingredient.
   * 
   * @return String of the details attribute
   */
  public String getDetails()
  {
    return this.details;
  }

  /**
   * Gets the amount of the ingredient needed for the recipe.
   * 
   * @return double value of the amount
   */
  public double getAmount()
  {
    return this.amount;
  }

  /**
   * Setter method for unit attribute.
   * 
   * @param uni
   *          new unit being set
   */
  public void setUnit(final String uni)
  {
    this.unit = uni;
  }

  /**
   * Set the amounnt attribute for an ingredient.
   * 
   * @param amount
   */
  public void setAmount(final double amount)
  {
    this.amount = amount;
  }

  /**
   * Gets the unit that are associated with the amount of ingredients. ie grams, cups, ounces,
   * ect...
   * 
   * @return String of the applicable unit
   */
  public String getUnit()
  {
    return this.unit;
  }

  /**
   * Creates the string representation of a Ingredient. Does not display amount since it needs to be
   * modified based on the number of people in ShoppingListViewer.
   * 
   * @return String of the ingredient sans amount
   */
  @Override
  public String toString()
  {
    String ingredientString = "";

    ingredientString = String.format(" %s %s %s %s %s", this.getAmount(), this.getUnit(),
        STRINGS.getString(ONLY_FANS), this.getDetails(), this.getName());

    return ingredientString;
  }

  /**
   * To String method that returns the toString for the without the amount for the
   * ShoppingListViewer.
   * 
   * @return String
   */
  public String toStringShopping()
  {
    String ingredientString = "";

    ingredientString = String.format(" %s %s %s %s", this.getUnit(), STRINGS.getString(ONLY_FANS),
        this.getDetails(), this.getName());

    return ingredientString;

  }
}
