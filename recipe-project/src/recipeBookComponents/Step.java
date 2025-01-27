package recipeBookComponents;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import recipeBookUtilities.ConvertUtil;

/**
 * Step contains actions, ingredients, source utensils, destination utensils, and time. Corresponds
 * #1917 Step Story in Information Representation Epic
 * 
 * @author Dylan Marti
 * @version 4.0
 *
 */
public class Step implements Serializable
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }
  
  private static final String FIVE_OUTPUT = "%s %s %s %s %s";
  private static final String THE = "THE";
  private static final String IN_THE = "IN_THE";
  private static final String CONTENTS = "CONTENTS";

  /**
   * Serializable and other attributes.
   */
  private static final long serialVersionUID = 1L;
  private String action;
  private Ingredient ingredient;
  private Utensil sourceUtensil;
  private Utensil destinationUtensil;
  private String details;
  private double timeMinutes;

  /**
   * Constructor that creates a step.
   * @param action The action of the step
   * @param ingredient The ingredient to use
   * @param sourceUtensil The utensil to take ingredients from
   * @param destinationUtensil The utensil used for ingredients or to put ingredients into
   * @param details Additional details
   * @param timeMinutes The time it will take to complete (INCOMPLETE)
   */
  public Step(final String action, final Ingredient ingredient, final Utensil sourceUtensil,
      final Utensil destinationUtensil, final String details, final double timeMinutes)
  {
    this.action = action;
    this.ingredient = ingredient;
    this.sourceUtensil = sourceUtensil;
    this.destinationUtensil = destinationUtensil;
    this.details = details;
    this.timeMinutes = timeMinutes;
  }

  /**
   * get the action in a step.
   * @return the Action in the step
   */
  public String getAction()
  {
    return action;
  }

  /**
   * get the ingredient in a step.
   * @return the Ingredient within steps
   */
  public Ingredient getIngredient()
  {
    return ingredient;
  }

  /**
   * get the sourceUtensil in a step.
   * @return the source utensil in the step
   */
  public Utensil getSourceUtensil()
  {
    return sourceUtensil;
  }

  /**
   * get the destinationUtensil in a step.
   * @return the destination utensil in the step
   */
  public Utensil getDestinationUtensil()
  {
    return destinationUtensil;
  }

  /**
   * get the time (minutes) in a step.
   * @return the time in a step (NEVER USED)
   */
  public double getTime()
  {
    return timeMinutes;
  }

  /**
   * set the time (minutes) in a step.
   * @param time the time to set the timeMinutes variable (NOT USED)
   */
  public void setTime(final String time)
  {
    this.timeMinutes = Double.parseDouble(time);
  }

  /**
   * get the details.
   * 
   * @return The details of the step
   */
  public String getDetail()
  {
    return this.details;
  }

  /**
   * set the details.
   * 
   * @param detail the new details to be set 
   */
  public void setDetail(final String detail)
  {
    this.details = detail;
  }

  /**
   * Converts step to string.
   * @return A string representation of the step
   */
  @Override
  public String toString()
  {
    String string = "";
    // step involving an ingredient
    // "action the ingredient in the utensil details"
    if (ingredient != null)
    {
      string = String.format(FIVE_OUTPUT, action, STRINGS.getString(THE),
          ingredient.getName(), STRINGS.getString(IN_THE), destinationUtensil.getName());

    }

    // step involving contents when the source and destination utensil are the same
    // "action the contents of utensil details"
    else if (sourceUtensil != null && destinationUtensil != null
        && sourceUtensil.getName() == destinationUtensil.getName())
    {
      string = String.format("%s %s %s", action, STRINGS.getString(CONTENTS),
          destinationUtensil.getName());
    }

    // steps involving contents when the source and destination the same
    // "action the contents of source utensil in the destination utensil details"
    else if (sourceUtensil != null && destinationUtensil != null
        && sourceUtensil.getName() != destinationUtensil.getName())
    {
      string = String.format(FIVE_OUTPUT, action, STRINGS.getString(CONTENTS),
          sourceUtensil.getName(), STRINGS.getString(IN_THE), destinationUtensil.getName());
    }

    // if step details are not blank then add them at the end
    if (details != ConvertUtil.BLANK)
      string += String.format(" %s", details);

    // convert first letter to uppercase!
    String firstLetter = string.substring(0, 1).toUpperCase();
    String lastBit = string.substring(1);
    string = "" + firstLetter + lastBit;
    return string;
  }

}
